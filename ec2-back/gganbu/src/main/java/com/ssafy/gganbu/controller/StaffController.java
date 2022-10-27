package com.ssafy.gganbu.controller;

import com.ssafy.gganbu.db.entity.Staffs;
import com.ssafy.gganbu.exception.NoChangeExeption;
import com.ssafy.gganbu.request.StaffLoginReq;
import com.ssafy.gganbu.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import response.BaseResponseBody;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/staff")
public class StaffController {


    @Autowired
    private StaffService staffService;

    @PostMapping("/login")
    public ResponseEntity<? extends BaseResponseBody> login(@RequestBody StaffLoginReq staffLoginReq) {
        System.out.println("login");;
        System.out.println("id : " + staffLoginReq.getId()+"password : " + staffLoginReq.getPassword());
        try{
            Staffs staff = staffService.login(staffLoginReq.getId(), staffLoginReq.getPassword());

            return ResponseEntity.status(200).body(BaseResponseBody.of("SUCCESS",staff));
        }
        catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body(BaseResponseBody.of("FAIL"));
        }
    }

    @PutMapping("/receipt")
    public ResponseEntity<? extends BaseResponseBody> receipt(@RequestBody String residentNo) {
        System.out.println("StaffController.receipt");
        System.out.println("residentNo : " + residentNo);
        try{
            Boolean success = staffService.receipt(residentNo);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body(BaseResponseBody.of("FAIL"));
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of("SUCCESS"));
    }

}
