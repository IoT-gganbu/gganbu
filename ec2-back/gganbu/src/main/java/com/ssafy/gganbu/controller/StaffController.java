package com.ssafy.gganbu.controller;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.ssafy.gganbu.db.entity.Staffs;
import com.ssafy.gganbu.exception.NoChangeExeption;
import com.ssafy.gganbu.request.ResidentNoReq;
import com.ssafy.gganbu.request.StaffLoginReq;
import com.ssafy.gganbu.service.StaffService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import response.BaseResponseBody;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.NoSuchElementException;

import static com.ssafy.gganbu.controller.PatientsController.FAIL;
import static com.ssafy.gganbu.controller.PatientsController.SUCCESS;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @PostMapping("/login")
    public ResponseEntity<BaseResponseBody> login(@RequestBody StaffLoginReq staffLoginReq) {
        log.info("login");
        log.debug("id : " + staffLoginReq.getId()+"password : " + staffLoginReq.getPassword());
        try{
            Staffs staff = staffService.login(staffLoginReq.getId(), staffLoginReq.getPassword());

            return ResponseEntity.status(200).body(BaseResponseBody.of(SUCCESS,staff));
        }
        catch (NoSuchElementException e){
            log.debug(e.getMessage());
            return ResponseEntity.status(500).body(BaseResponseBody.of(FAIL));
        }
    }

    @PutMapping("/receipt")
    public Object receipt(@RequestBody ResidentNoReq residentNoReq) {
        log.info("StaffController.receipt");
        log.debug("residentNo : " + residentNoReq.getResidentNo());
        try{
            staffService.receipt(residentNoReq.getResidentNo());
            return ResponseEntity.status(200).body(BaseResponseBody.of(SUCCESS));
        }
        catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.status(500).body(BaseResponseBody.of(FAIL));
        }
    }

    @GetMapping("/progress/{userId}")
    @ApiImplicitParam(name = "userId", value = "사용자 ID", required = true, dataType = "String", paramType = "path", example = "1")
    public ResponseEntity<BaseResponseBody> progress(@PathVariable Long userId) {
        log.info("StaffController.progress");
        log.debug("userId : " + userId);
        try{
            staffService.progress(userId);
            return ResponseEntity.status(200).body(BaseResponseBody.of(SUCCESS));
        }
        catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.status(500).body(BaseResponseBody.of(FAIL));
        }
    }

}
