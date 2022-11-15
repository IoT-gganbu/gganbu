package com.ssafy.gganbu.service;

import com.ssafy.gganbu.db.entity.PatientProgressHistory;
import com.ssafy.gganbu.db.entity.Patients;
import com.ssafy.gganbu.db.entity.TaskChecktitle;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PatientService {

    public Patients receipt(Patients patients);
    public boolean checkResidentNo(String residentNo);
    public List<Patients> searchPatient(String name);
    public Patients getPatient(Long patientId);
    public Page<Patients> searchPatientWithPage(String name, int page, int size);
    public Page<Patients> searchAllPatientWithPage(int page, int size);


    boolean checkPatientHistory(Patients patient, TaskChecktitle taskChecktitle);

    String updateStatus(Long patientId, Long tcId, int status);

    PatientProgressHistory getHistory(Patients patient, TaskChecktitle taskChecktitle);

    boolean existedHistory(Patients patient, TaskChecktitle taskChecktitle);

    void addHistory(Patients patient, TaskChecktitle taskChecktitle, int status);

    boolean addPatient(String name, Integer gender, String tel, String residentNo);
}
