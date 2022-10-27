package com.ssafy.gganbu.db.repository;

import com.ssafy.gganbu.db.entity.TaskChecktitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskChecktitle, Long> {
    public TaskChecktitle findByTcId(Long tcId);
}
