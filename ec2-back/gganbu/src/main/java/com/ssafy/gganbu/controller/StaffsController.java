package com.ssafy.gganbu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/staff")
public class StaffsController {

    @PostMapping()
    public ResponseEntity<?> loginStaff(@RequestBody String id, @RequestBody String password) {


        return null;
    }

}
