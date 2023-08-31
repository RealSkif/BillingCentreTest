package com.example.BillingCentreTest.services;

import com.example.BillingCentreTest.models.Task;
import com.example.BillingCentreTest.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public List<Task> findbyDate(Date date) {
        List<Task> listOfTasks = taskRepository.findByDate(date);
        listOfTasks.stream().
        return
    }

    public Task findOne(UUID id) {
        Optional<Task> foundTask = taskRepository.findById(id);
        return foundTask.orElse(null);
    }

    @Transactional
    public void save(Task task) {
        taskRepository.save(task);
    }

    @Transactional
    public void update(UUID id, Task updatedTask) {
        updatedTask.setId(id);
        taskRepository.save(updatedTask);
    }

    @Transactional
    public void delete(UUID id) {
        taskRepository.deleteById(id);
    }
}
