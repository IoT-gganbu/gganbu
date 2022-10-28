package com.ssafy.gganbu.db.repository;


import com.ssafy.gganbu.db.entity.PatientProgressHistory;
import com.ssafy.gganbu.db.entity.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<PatientProgressHistory, Long> {
    List<PatientProgressHistory> findByPatientOrderByTaskChecktitleAsc(Patients patient);
}
