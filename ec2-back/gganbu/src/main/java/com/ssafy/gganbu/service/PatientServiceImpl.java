package com.ssafy.gganbu.service;

import com.ssafy.gganbu.db.entity.Patients;
import com.ssafy.gganbu.db.repository.HistoryRepository;
import com.ssafy.gganbu.db.repository.PatientReqository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service("patientService")
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PatientServiceImpl implements PatientService{

    @Autowired
    PatientReqository patientReqository;


    @Autowired
    HistoryRepository historyRepository;

    public boolean checkResidentNo(String residentNo){
        log.info("service" + residentNo);
        return patientReqository.existsByResidentNo(residentNo).orElseThrow(()-> new NoSuchElementException("patient not found"));
    }

    @Override
    public List<Patients> searchPatient(String name) {
        try {
            return patientReqository.findAllByName(name).orElseThrow(()-> new NoSuchElementException("patient not found"));
        }catch (Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public Patients getPatient(Long patientId) {
        try {
            return patientReqository.findByPatientId(patientId).orElseThrow(()-> new NoSuchElementException("patient not found"));
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public Page<Patients> searchPatientWithPage(String name, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return patientReqository.findAllByName(name, pageable).orElseThrow(()-> new NoSuchElementException("patient not found"));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<Patients> searchAllPatientWithPage(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
            return patientReqository.findAll(pageable);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Patients receipt(Patients patients){

        try{
            return patientReqository.save(patients);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
