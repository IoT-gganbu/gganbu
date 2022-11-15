package com.ssafy.gganbu.controller;


import com.ssafy.gganbu.db.entity.PatientProgressHistory;
import com.ssafy.gganbu.db.entity.Patients;
import com.ssafy.gganbu.db.entity.TaskChecktitle;
import com.ssafy.gganbu.db.repository.HistoryRepository;
import com.ssafy.gganbu.db.repository.PatientRepository;
import com.ssafy.gganbu.db.repository.TaskRepository;
import com.ssafy.gganbu.event.CheckupEvent;
import com.ssafy.gganbu.model.SocketVO;
import com.ssafy.gganbu.request.CheckUpReq;
import com.ssafy.gganbu.request.PatientReq;
import com.ssafy.gganbu.request.StatusReq;
import com.ssafy.gganbu.service.PatientService;
import com.ssafy.gganbu.service.QrService;
import com.ssafy.gganbu.service.TaskService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import response.BaseResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Api("환자 Controller")
@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/api/patient")
public class PatientsController {

    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL = "FAIL";

    @Autowired
    PatientService patientService;
    @Autowired
    PatientRepository patientRepository;

    @Autowired
    TaskService taskService;

    @Autowired
    QrService qrService;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @PostMapping("/receipt")
    @ApiOperation(value = "환자 접수")
    public ResponseEntity<BaseResponseBody> receipt(@RequestBody @ApiParam(value = "수정할회원정보") PatientReq reqData) {
        if(patientService.addPatient(reqData.getName(), reqData.getGender(), reqData.getTel(), reqData.getResidentNo())){
            return ResponseEntity.status(200).body(BaseResponseBody.of(SUCCESS));
        }else{
            return ResponseEntity.status(500).body(BaseResponseBody.of(FAIL));
        }
    }


    @GetMapping("/search/{name}")
    @ApiOperation(value = "이름으로 환자 검색")
    @ApiImplicitParam(name = "name", value = "환자 이름", required = true, dataType = "String", paramType = "path", example = "김철수")
    public ResponseEntity<BaseResponseBody> searchPatient(@PathVariable String name) {

        List<Patients> res = new ArrayList<>();
        try {
            res = patientService.searchPatient(name);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(BaseResponseBody.of(FAIL));
        }
        // 해당 회원이 없는 경우
        if (res.isEmpty()) {
            return ResponseEntity.status(200).body(BaseResponseBody.of("해당회원없음"));
        } else {
            return ResponseEntity.status(200).body(BaseResponseBody.of(SUCCESS, res));
        }
    }

    @GetMapping("/search")
    @ApiOperation(value = "이름으로 환자 검색 + 페이징 처리")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "name", value = "환자 이름", required = true, dataType = "String", paramType = "query", example = "김철수"),
                    @ApiImplicitParam(name = "page", value = "페이지 번호", required = true, dataType = "int", paramType = "query", example = "1"),
                    @ApiImplicitParam(name = "size", value = "페이지 사이즈", required = true, dataType = "int", paramType = "query", example = "10")
            }
    )
    public ResponseEntity<BaseResponseBody> searchPatientWithPage(@RequestParam(value = "name") String name, @RequestParam(value = "page") int page, @RequestParam(value = "size") int size) {
        try{
            log.info("name : " + name);
            log.info("page : " + page);
            log.info("size : " + size);
            Page<Patients> res = patientService.searchPatientWithPage(name, page, size);
            return ResponseEntity.status(200).body(BaseResponseBody.of(SUCCESS, res));
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(BaseResponseBody.of(FAIL));
        }
    }

    @PutMapping("/checkup")
    @ApiOperation(value = "상태 변경")
    public ResponseEntity<BaseResponseBody> changeStatus(@RequestBody StatusReq statusReq) {
            try {
                String res = patientService.updateStatus(statusReq.getPatientId(), statusReq.getTcId(), statusReq.getStatus());
                if(res.equals("duplicated")){
                    return ResponseEntity.status(200).body(BaseResponseBody.of("Duplicate"));
                }else{
                    eventPublisher.publishEvent(new CheckupEvent(new SocketVO(statusReq.getPatientId()+"", statusReq.getTcId()+"", statusReq.getStatus())));
                }
            } catch(Exception e){
                log.info("error");
                log.info(e.getMessage());
                e.printStackTrace();
                return ResponseEntity.status(500).body(BaseResponseBody.of(FAIL));
            }
        return ResponseEntity.status(200).body(BaseResponseBody.of(SUCCESS));
    }


    @GetMapping("/checkup/{patientId}/{tcId}")
    @ApiOperation(value = "상태 조회")
    public ResponseEntity<BaseResponseBody> getStatus(@PathVariable Long patientId, @PathVariable Long tcId) {
        HashMap<String, Object> res = new HashMap<>();
        Patients patients = patientService.getPatient(patientId);
        TaskChecktitle taskChecktitle = taskService.getTask(tcId);
        try {
            PatientProgressHistory patientProgressHistory = patientService.getHistory(patients, taskChecktitle);
            res.put("status",patientProgressHistory.getPatientStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(BaseResponseBody.of(FAIL));
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(SUCCESS, res));
    }

    @GetMapping("/downloadfile/{patientId:.+}")
    @ApiOperation(value = "QR 다운로드")
    @ApiImplicitParam(name = "patientId", value = "환자 ID", required = true, dataType = "String", paramType = "path", example = "1")
    public ResponseEntity<Resource> downloadQr(@PathVariable String patientId, HttpServletRequest request) {
        Resource resource = qrService.loadFileAsResource(patientId);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            log.info("Could not determine file type.");
        }
        if (contentType == null) {
            contentType = "image/png";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping(value = "/image/view/{patientId}", produces = MediaType.IMAGE_PNG_VALUE)
    @ApiImplicitParam(name = "patientId", value = "환자 ID", required = true, dataType = "String", paramType = "path", example = "1")
    public @ResponseBody byte[] getImage(@RequestParam("patientId") String id) throws IOException {
        FileInputStream fis = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Path path = Paths.get("/tmp/gganbu/patient/" + id + "/qr.png");
        byte[] fileArray = new byte[0];
        try {
            fis = new FileInputStream(path.toString());
            int readCount = 0;
            byte[] buffer = new byte[1024];
            fileArray = null;
            while ((readCount = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, readCount);
            }
            fileArray = baos.toByteArray();
            fis.close();
            baos.close();
        } catch (IOException e) {
            log.info("파일을 찾을 수 없습니다.");
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (baos != null) {
                baos.close();
            }
        }
        return fileArray;
    }

    @GetMapping("/{patientId}")
    @ApiOperation(value = "QR의 id로 유저 검색")
    @ApiImplicitParam(name = "patientId", value = "환자 ID", required = true, dataType = "String", paramType = "path", example = "1")
    public ResponseEntity<BaseResponseBody> getPatientInfo(@PathVariable Long patientId) {
        HashMap<String, Object> res = new HashMap<>();
        log.info("PatientsController.getPatientInfo");
        try {
            Patients patients = patientService.getPatient(patientId);
            res.put("patientId", patients.getPatientId());
            res.put("gender", patients.getGender());
            res.put("name", patients.getName());
            res.put("age", patients.getAge());
            List<TaskChecktitle> tasks = taskService.getAllTask();
            // 남자의 경우
            if (patients.getGender() == 0) {
                // 자궁경부암, 유방암 검사 제외
                tasks.remove(5);
                tasks.remove(5);
                res.put("task", tasks);
            } else {
                res.put("task", tasks);
            }

            return ResponseEntity.status(200).body(BaseResponseBody.of(SUCCESS, res));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(BaseResponseBody.of(FAIL));
        }

    }


}