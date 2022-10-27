package com.ssafy.gganbu.service;

import com.ssafy.gganbu.db.entity.TaskChecktitle;
import com.ssafy.gganbu.db.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("TaskService")
public class TaskServiceImpl implements TaskService{

    @Autowired
    TaskRepository taskRepository;
    @Override
    public TaskChecktitle getTask(Long tcId) {
        try {
            TaskChecktitle tc = taskRepository.findByTcId(tcId);
            return tc;
        }catch (Exception e){
            return null;
        }

    }
}
