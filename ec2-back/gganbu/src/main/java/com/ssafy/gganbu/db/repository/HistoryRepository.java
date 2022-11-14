package com.ssafy.gganbu.db.repository;


import com.ssafy.gganbu.db.entity.PatientProgressHistory;
import com.ssafy.gganbu.db.entity.Patients;
import com.ssafy.gganbu.db.entity.TaskChecktitle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistoryRepository extends JpaRepository<PatientProgressHistory, Long> {

    Optional<PatientProgressHistory> findByPatientAndTaskChecktitle(Patients patient, TaskChecktitle taskChecktitle);

    public Optional<List<PatientProgressHistory>> findByPatientOrderByTaskChecktitleAsc(Patients patient);

    Boolean existsByPatientAndTaskChecktitle(Patients patient, TaskChecktitle taskChecktitle);
}
