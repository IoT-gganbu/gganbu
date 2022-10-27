package com.ssafy.gganbu.service;

import com.ssafy.gganbu.db.entity.Patients;
import com.ssafy.gganbu.request.PatientReq;

public interface PatientService {

    public Patients receipt(Patients patients);

    public boolean checkResidentNo(String residentNo);
}
