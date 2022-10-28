package com.ssafy.gganbu.service;

import com.ssafy.gganbu.db.entity.TaskChecktitle;
import com.ssafy.gganbu.db.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<TaskChecktitle> getAllTask() {
        List<TaskChecktitle> res = taskRepository.findAll();
        return res;
    }
}
