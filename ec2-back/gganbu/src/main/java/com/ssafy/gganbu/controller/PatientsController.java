package com.ssafy.gganbu.controller;


import com.ssafy.gganbu.db.entity.Patients;
import com.ssafy.gganbu.request.PatientReq;
import com.ssafy.gganbu.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class PatientsController {

    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";

    PatientService patientService;

    @PostMapping("/receipt")
    public ResponseEntity<Map<String, Object>> receipt(@RequestBody PatientReq reqData){
        Map<String, Object> result = new HashMap<>();
        if(!patientService.checkResidentNo(reqData.getResidentNo())){
            result.put("message", FAIL);
        }else{
            Patients res = new Patients();

        }
        return null;
    }
}
