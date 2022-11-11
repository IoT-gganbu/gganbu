package com.ssafy.gganbu.service;

import com.ssafy.gganbu.db.entity.PatientProgressHistory;
import com.ssafy.gganbu.db.entity.Patients;
import com.ssafy.gganbu.db.entity.TaskChecktitle;
import com.ssafy.gganbu.db.repository.HistoryRepository;
import com.ssafy.gganbu.db.repository.PatientReqository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        System.out.println("service" + residentNo);
        Boolean check = patientReqository.existsByResidentNo(residentNo).orElseThrow(()-> new NoSuchElementException("patient not found"));
        return check;
    }

    @Override
    public List<Patients> searchPatient(String name) {
        try {
            List<Patients> res = patientReqository.findAllByName(name).orElseThrow(()-> new NoSuchElementException("patient not found"));
            return res;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Patients getPatient(Long patientId) {
        try {
            Patients patients = patientReqository.findByPatientId(patientId).orElseThrow(()-> new NoSuchElementException("patient not found"));
            return patients;
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public Page<Patients> searchPatientWithPage(String name, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Patients> res = patientReqository.findAllByName(name, pageable).orElseThrow(()-> new NoSuchElementException("patient not found"));
            return res;
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
            Patients res = patientReqository.save(patients);
            return res;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
