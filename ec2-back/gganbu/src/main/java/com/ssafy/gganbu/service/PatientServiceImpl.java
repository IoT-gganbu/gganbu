package com.ssafy.gganbu.service;

import com.ssafy.gganbu.db.entity.Patients;
import com.ssafy.gganbu.db.repository.PatientReqository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("patientService")
@RequiredArgsConstructor
@Transactional
public class PatientServiceImpl implements PatientService{

    PatientReqository patientReqository;

    public boolean checkResidentNo(String residentNo){
        if(patientReqository.findByResidentNo(residentNo)!=null){
            return false;
        }
        return true;
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
