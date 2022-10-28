package com.ssafy.gganbu.service;

import com.ssafy.gganbu.db.entity.TaskChecktitle;

import java.util.List;

public interface TaskService {

    public TaskChecktitle getTask(Long tcId);

    public List<TaskChecktitle> getAllTask();
}
