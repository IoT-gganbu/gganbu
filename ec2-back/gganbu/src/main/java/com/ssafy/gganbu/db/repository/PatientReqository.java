package com.ssafy.gganbu.db.repository;

import com.ssafy.gganbu.db.entity.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientReqository extends JpaRepository<Patients, Long> {

    public Patients findByResidentNo(String residentNo);

    public List<Patients> findAllByName(String name);
}
