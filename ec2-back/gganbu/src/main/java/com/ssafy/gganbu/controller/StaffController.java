package com.ssafy.gganbu.controller;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.ssafy.gganbu.db.entity.Staffs;
import com.ssafy.gganbu.exception.NoChangeExeption;
import com.ssafy.gganbu.request.StaffLoginReq;
import com.ssafy.gganbu.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import response.BaseResponseBody;

import java.nio.file.Path;
import java.util.NoSuchElementException;

import static java.lang.System.out;

@Slf4j
@RestController
@RequestMapping("/staff")
public class StaffController {


    @Autowired
    private StaffService staffService;

    @PostMapping("/login")
    public ResponseEntity<? extends BaseResponseBody> login(@RequestBody StaffLoginReq staffLoginReq) {
        out.println("login");;
        out.println("id : " + staffLoginReq.getId()+"password : " + staffLoginReq.getPassword());
        try{
            Staffs staff = staffService.login(staffLoginReq.getId(), staffLoginReq.getPassword());

            return ResponseEntity.status(200).body(BaseResponseBody.of("SUCCESS",staff));
        }
        catch (NoSuchElementException e){
            out.println(e.getMessage());
            return ResponseEntity.status(500).body(BaseResponseBody.of("FAIL"));
        }
    }

    @PutMapping("/receipt")
    public Object receipt(@RequestBody String residentNo) {
        out.println("StaffController.receipt");
        out.println("residentNo : " + residentNo);
        try{
            BitMatrix qr = staffService.receipt(residentNo);
            // convert bitmatrix to png
            MatrixToImageWriter.writeToPath(qr, "png", Path.of("/qr.png"));

        }
        catch (Exception e) {
            out.println(e.getMessage());
            return ResponseEntity.status(500).body(BaseResponseBody.of("FAIL"));
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of("SUCCESS"));
    }

    @GetMapping("/progress/{userId}")
    public ResponseEntity<? extends BaseResponseBody> progress(@PathVariable Long userId) {
        out.println("StaffController.progress");
        out.println("userId : " + userId);
        try{
            Object success = staffService.progress(userId);
            return ResponseEntity.status(200).body(BaseResponseBody.of("SUCCESS", success));
        }
        catch (Exception e) {
            out.println(e.getMessage());
            return ResponseEntity.status(500).body(BaseResponseBody.of("FAIL"));
        }
    }

}
