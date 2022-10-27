package com.ssafy.gganbu.db.repository;

import com.ssafy.gganbu.db.entity.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientReqository extends JpaRepository<Patients, Long> {

    public Patients findByResidentNo(String residentNo);
}
