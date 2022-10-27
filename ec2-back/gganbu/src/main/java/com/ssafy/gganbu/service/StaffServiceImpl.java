package com.ssafy.gganbu.service;

import com.ssafy.gganbu.db.entity.Staffs;
import com.ssafy.gganbu.db.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service("StaffService")
public class StaffServiceImpl implements StaffService {

    @Autowired
    StaffRepository staffRepository;
    @Override
    public boolean login(String id, String password) {
        System.out.println("StaffServiceImpl.login");
        Staffs staff = staffRepository.findByIdAndPassword(id, password).orElseThrow(()-> new NoSuchElementException("해당하는 직원이 없습니다."));
        System.out.println("오류 발생 안함");
        return staff == null? false : true;
    }
}

