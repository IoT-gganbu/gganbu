package com.ssafy.gganbu.service;

import com.ssafy.gganbu.db.entity.PatientProgressHistory;
import com.ssafy.gganbu.db.entity.Patients;
import com.ssafy.gganbu.db.entity.TaskChecktitle;
import com.ssafy.gganbu.db.repository.HistoryRepository;
import com.ssafy.gganbu.db.repository.PatientRepository;
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
    PatientRepository patientRepository;

    @Autowired
    HistoryRepository historyRepository;


    public boolean checkResidentNo(String residentNo){
        log.info("service" + residentNo);
        return patientRepository.existsByResidentNo(residentNo).orElseThrow(()-> new NoSuchElementException("patient not found"));
    }

    @Override
    public List<Patients> searchPatient(String name) {
        try {
            return patientRepository.findAllByName(name).orElseThrow(()-> new NoSuchElementException("patient not found"));
        }catch (Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public Patients getPatient(Long patientId) {
        try {
            return patientRepository.findByPatientId(patientId).orElseThrow(()-> new NoSuchElementException("patient not found"));
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public Page<Patients> searchPatientWithPage(String name, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return patientRepository.findAllByName(name, pageable).orElseThrow(()-> new NoSuchElementException("patient not found"));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<Patients> searchAllPatientWithPage(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
            return patientRepository.findAll(pageable);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean checkPatientHistory(Patients patient, TaskChecktitle taskChecktitle) {
        PatientProgressHistory patientProgressHistory = historyRepository.findByPatientAndTaskChecktitle(patient, taskChecktitle).orElseThrow(() -> new NoSuchElementException("patientProgressHistory not found"));
        // 이동전(0), 이동중(1), 대기중(2), 검사중(3), 검사완료(4)
        if(patientProgressHistory.getPatientStatus() == 4)
            return false;
        else if(patientProgressHistory.getPatientStatus() == 3)
            return true;
        else{
            log.error("this patient is not staus 3 or 4");
            return false;
        }
    }

    public Patients receipt(Patients patients){

        try{
            return patientRepository.save(patients);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
