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
import java.io.IOException;
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

    // 환자 정보 입력(접수)
    @PostMapping("/receipt")
    public ResponseEntity<Map<String, Object>> receipt(@RequestBody PatientReq reqData){
        Map<String, Object> result = new HashMap<>();
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
            // 만나이 저장 변수
            int newage = 0;
            // 2000년생 이상인 경우
            if(Integer.parseInt(residentNo.substring(7,8))==3 || Integer.parseInt(residentNo.substring(7,8))==4){
                newage = 2000 + Integer.parseInt(residentNo.substring(0,2));
            // 1900년생인 경우
            }else{
                newage = 1900 + Integer.parseInt(residentNo.substring(0,2));
            }
            // 만나이 구하는 함수
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

    // 이름으로 환자정보 가져오는 함수
    @GetMapping("/search/{name}")
    public ResponseEntity<? extends BaseResponseBody> searchPatient(@PathVariable String name){

        List<Patients> res = new ArrayList<>();
        try {
            res = patientService.searchPatient(name);
        }catch (Exception e){
            return ResponseEntity.status(500).body(BaseResponseBody.of(FAIL));
        }
        // 해당 회원이 없는 경우
        if(res.size() == 0){
            return ResponseEntity.status(200).body(BaseResponseBody.of("해당회원없음"));
        }else{
            return ResponseEntity.status(200).body(BaseResponseBody.of(SUCCESS,res));
        }

    }

    // 해당 검진 절차 완료 여부 입력 함수
    @PostMapping("/checkup")
    public ResponseEntity<? extends BaseResponseBody> checkUp(@RequestBody CheckUpReq checkUpReq) {
        PatientProgressHistory history = new PatientProgressHistory();
        Patients patients = patientService.getPatient(checkUpReq.getPatientId());
        TaskChecktitle taskChecktitle = taskService.getTask(checkUpReq.getTcId());
        // 중복 입력시
        if(historyRepository.existsByTaskChecktitleAndPatient(taskChecktitle, patients)){
            return ResponseEntity.status(200).body(BaseResponseBody.of("중복 입력"));
        }
        try {
            history.setPatient(patients);
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
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<? extends BaseResponseBody> getPatientInfo(@PathVariable Long patientId){
        HashMap<String,Object> res = new HashMap<>();
        try {
            Patients patients = patientService.getPatient(patientId);
            res.put("patientId",patients.getPatientId());
            res.put("gender",patients.getGender());
            List<TaskChecktitle> tasks = taskService.getAllTask();
            // 남자의 경우
            if(patients.getGender()==0){
                // 자궁경부암, 유방암 검사 제외
                tasks.remove(5);
                tasks.remove(5);
                res.put("task",tasks);
            }else{
                res.put("task",tasks);
            }

            return ResponseEntity.status(200).body(BaseResponseBody.of(SUCCESS,res));
        }catch (Exception e){
            return ResponseEntity.status(500).body(BaseResponseBody.of(FAIL));
        }

    }


}
