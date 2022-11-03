package com.ssafy.gganbu.db.repository;

import com.ssafy.gganbu.db.entity.Patients;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientReqository extends JpaRepository<Patients, Long> {
    // 나중에 Optional로 변경해야함.
    public Optional<Patients> findByResidentNo(String residentNo);

    public Optional<Boolean> existsByResidentNo(String residentNo);

    public Optional<List<Patients>> findAllByName(String name);

    public Optional<Patients> findByPatientId(Long patientId);

    public Optional<Page<Patients>> findAllByName(String name, Pageable pageable);
}
