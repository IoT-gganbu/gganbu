package com.ssafy.gganbu.controller;

import com.ssafy.gganbu.db.entity.Staffs;
import com.ssafy.gganbu.request.PatientReq;
import com.ssafy.gganbu.request.StaffLoginReq;
import com.ssafy.gganbu.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import response.BaseResponseBody;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/staff")
public class StaffsController {


    @Autowired
    private StaffService staffService;

    @PostMapping("/login")
    public ResponseEntity<? extends BaseResponseBody> login(@RequestBody StaffLoginReq staffLoginReq) {
        System.out.println("login");;
        System.out.println("id : " + staffLoginReq.getId()+"password : " + staffLoginReq.getPassword());
        try{
            Staffs staff = staffService.login(staffLoginReq.getId(), staffLoginReq.getPassword());

            return ResponseEntity.status(200).body(BaseResponseBody.of("success",staff));
        }
        catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body(BaseResponseBody.of("FAIL"));
        }
    }
}
