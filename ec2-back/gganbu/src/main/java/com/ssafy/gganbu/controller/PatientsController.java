package com.ssafy.gganbu.controller;


import com.ssafy.gganbu.db.entity.Patients;
import com.ssafy.gganbu.db.repository.PatientReqository;
import com.ssafy.gganbu.request.PatientReq;
import com.ssafy.gganbu.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<Map<String, Object>> searchPatient(@PathVariable String name){
        Map<String, Object> result = new HashMap<>();
//        List<Patients> res
        return null;
    }
}
