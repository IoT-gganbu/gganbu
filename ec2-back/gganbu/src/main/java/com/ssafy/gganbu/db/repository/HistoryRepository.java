package com.ssafy.gganbu.db.repository;


import com.ssafy.gganbu.db.entity.PatientProgressHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<PatientProgressHistory, Long> {
}
