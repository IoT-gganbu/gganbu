package com.ssafy.gganbu.service;

import com.ssafy.gganbu.db.entity.Patients;
import com.ssafy.gganbu.db.entity.TaskChecktitle;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PatientService {

    public Patients receipt(Patients patients);
    public boolean checkResidentNo(String residentNo);
    public List<Patients> searchPatient(String name);
    public Patients getPatient(Long patientId);
    Page<Patients> searchPatientWithPage(String name, int page, int size);
    Page<Patients> searchAllPatientWithPage(int page, int size);


    boolean checkPatientHistory(Patients patient, TaskChecktitle taskChecktitle);

}
