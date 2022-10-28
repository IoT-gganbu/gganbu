package com.ssafy.gganbu.db.repository;


import com.ssafy.gganbu.db.entity.PatientProgressHistory;
import com.ssafy.gganbu.db.entity.Patients;
import com.ssafy.gganbu.db.entity.TaskChecktitle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<PatientProgressHistory, Long> {

    public Boolean existsByTaskChecktitleAndPatient(TaskChecktitle taskChecktitle, Patients patients);

    List<PatientProgressHistory> findByPatientOrderByTaskChecktitleAsc(Patients patient);

}
