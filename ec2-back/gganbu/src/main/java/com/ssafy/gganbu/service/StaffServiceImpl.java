package com.ssafy.gganbu.service;

import com.ssafy.gganbu.db.entity.Patients;
import com.ssafy.gganbu.db.entity.Staffs;
import com.ssafy.gganbu.db.repository.PatientReqository;
import com.ssafy.gganbu.db.repository.StaffRepository;
import com.ssafy.gganbu.exception.NoChangeExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.prefs.NodeChangeEvent;

@Service("StaffService")
public class StaffServiceImpl implements StaffService {

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    PatientReqository patientRepository;
    @Override
    public Staffs login(String id, String password) {
        System.out.println("StaffServiceImpl.login");
        Staffs staff = staffRepository.findByIdAndPassword(id, password).orElseThrow(()-> new NoSuchElementException("staff not found"));
        return staff;
    }

    @Override
    public Boolean receipt(String residentNo) throws NoChangeExeption {
        System.out.println("StaffServiceImpl.receipt");
        // 나중에 Optional로 변경해야함.
        Patients patient = patientRepository.findByResidentNo(residentNo);
        // null 체크
        if(patient == null) {
            throw new NoSuchElementException("no patient found");
        }
        // 이미 isCheckup이 true라면?
        if(patient.getIsCheckup()){
            throw new NoChangeExeption("aleady checked");
        }
        // isCheckup을 true로 변환
        patient.setIsCheckup(true);
        patientRepository.save(patient);
        // 추 후에 QR코드 전송방식으로 변경해줘야함.

        return true;
    }
}

