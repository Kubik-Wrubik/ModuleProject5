package com.kubik.service;

import com.kubik.dao.TaskDAO;
import com.kubik.entity.Status;
import com.kubik.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskDAO taskDAO;

    public List<Task> getAll(int offset, int limit) {
        return taskDAO.getAll(offset, limit);
    }

    public long getAllCount() {
        return taskDAO.getAllCount();
    }

    public Task create(String description, Status status) {
        Task task = new Task();
        task.setDescription(description);
        task.setStatus(status);
        taskDAO.saveOrUpdate(task);
        return task;
    }

    @Transactional
    public Task saveOrUpdate(int id, String description, Status status) {
        Task task = taskDAO.getById(id);
        if (task == null){
            throw new RuntimeException("Not found");
        }
        task.setDescription(description);
        task.setStatus(status);
        taskDAO.saveOrUpdate(task);
        return task;
    }

    @Transactional
    public void delete(int id) {
        Task task = taskDAO.getById(id);
        if (task == null){
            throw new RuntimeException("Not found");
        }
        taskDAO.delete(task);
    }
}
