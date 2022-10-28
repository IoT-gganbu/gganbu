package com.ssafy.gganbu.controller;


import com.ssafy.gganbu.db.entity.PatientProgressHistory;
import com.ssafy.gganbu.db.entity.Patients;
import com.ssafy.gganbu.db.entity.TaskChecktitle;
import com.ssafy.gganbu.db.repository.HistoryRepository;
import com.ssafy.gganbu.db.repository.PatientReqository;
import com.ssafy.gganbu.db.repository.TaskRepository;
import com.ssafy.gganbu.request.CheckUpReq;
import com.ssafy.gganbu.request.PatientReq;
import com.ssafy.gganbu.service.PatientService;
import com.ssafy.gganbu.service.QrService;
import com.ssafy.gganbu.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
import java.time.LocalDate;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/patient")
public class PatientsController {

    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";

    @Autowired
    PatientService patientService;
    @Autowired
    PatientReqository patientReqository;

    @Autowired
    TaskService taskService;

    @Autowired
    QrService qrService;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    HistoryRepository historyRepository;

    @PostMapping("/receipt")
    public ResponseEntity<Map<String, Object>> receipt(@RequestBody PatientReq reqData){
        Map<String, Object> result = new HashMap<>();
        System.out.println("aaaaaa");
        System.out.println(reqData.getResidentNo());
        if(!patientService.checkResidentNo(reqData.getResidentNo())){
            result.put("message", FAIL);
        }else{
            Patients res = new Patients();
            res.setName(reqData.getName());
            res.setGender(reqData.getGender());
            res.setTel(reqData.getTel());
            String residentNo = reqData.getResidentNo();
            res.setResidentNo(residentNo);
            int newage = 0;
            if(Integer.parseInt(residentNo.substring(7,8))==3 || Integer.parseInt(residentNo.substring(7,8))==4){
                newage = 2000 + Integer.parseInt(residentNo.substring(0,2));
            }else{
                newage = 1900 + Integer.parseInt(residentNo.substring(0,2));
            }
            int age = getAge(newage,
                    Integer.parseInt(residentNo.substring(2,4)), Integer.parseInt(residentNo.substring(4,6)));
            System.out.println(age);
            res.setAge(age);
            patientReqository.save(res);
            result.put("message", SUCCESS);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public static int getAge(int birthYear, int birthMonth, int birthDay) {
        Calendar current = Calendar.getInstance();

        int currentYear  = current.get(Calendar.YEAR);
        int currentMonth = current.get(Calendar.MONTH) + 1;
        int currentDay   = current.get(Calendar.DAY_OF_MONTH);

        // 만 나이 구하기 2022-1995=27 (현재년-태어난년)
        int age = currentYear - birthYear;
        // 만약 생일이 지나지 않았으면 -1
        if (birthMonth * 100 + birthDay > currentMonth * 100 + currentDay)
            age--;
        // 5월 26일 생은 526
        // 현재날짜 5월 25일은 525
        // 두 수를 비교 했을 때 생일이 더 클 경우 생일이 지나지 않은 것이다.
        return age;
    }

    @GetMapping("/{name}")
    public ResponseEntity<? extends BaseResponseBody> searchPatient(@PathVariable String name){

        List<Patients> res = new ArrayList<>();
        try {
            res = patientService.searchPatient(name);
        }catch (Exception e){
            return ResponseEntity.status(500).body(BaseResponseBody.of(FAIL));
        }
        if(res.size() == 0){
            return ResponseEntity.status(200).body(BaseResponseBody.of("해당회원없음"));
        }else{
            return ResponseEntity.status(200).body(BaseResponseBody.of(SUCCESS,res));
        }

    }

    @PostMapping("/checkup")
    public ResponseEntity<? extends BaseResponseBody> checkUp(@RequestBody CheckUpReq checkUpReq) {
        PatientProgressHistory history = new PatientProgressHistory();
        try {
            Patients patients = patientService.getPatient(checkUpReq.getPatientId());
            history.setPatient(patients);

            TaskChecktitle taskChecktitle = taskService.getTask(checkUpReq.getTcId());
            history.setTaskChecktitle(taskChecktitle);

            historyRepository.save(history);
        }catch (Exception e){
            return ResponseEntity.status(500).body(BaseResponseBody.of(FAIL));
        }

        return ResponseEntity.status(200).body(BaseResponseBody.of(SUCCESS));
    }

    @GetMapping("/downloadfile/{patientId:.+}")
    public ResponseEntity<Resource> downloadQr(@PathVariable String patientId, HttpServletRequest request){
        Resource resource = qrService.loadFileAsResource(patientId);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Could not determine file type.");
        }
        if(contentType == null){
//            contentType = "application/octet-stream";
            contentType = "image/png";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    @GetMapping(value="/image/view/{patientId}", produces= MediaType.IMAGE_PNG_VALUE)
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
            System.out.println("파일을 찾을 수 없습니다.");
        }
        return fileArray;

    }

}
