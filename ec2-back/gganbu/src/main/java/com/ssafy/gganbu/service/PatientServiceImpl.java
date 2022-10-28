package com.ssafy.gganbu.service;

import com.ssafy.gganbu.db.entity.Patients;
import com.ssafy.gganbu.db.repository.PatientReqository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service("patientService")
@RequiredArgsConstructor
@Transactional
public class PatientServiceImpl implements PatientService{

    @Autowired
    PatientReqository patientReqository;

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
