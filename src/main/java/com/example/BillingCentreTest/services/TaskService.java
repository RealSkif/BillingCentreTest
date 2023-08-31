package com.example.BillingCentreTest.services;

import com.example.BillingCentreTest.models.Task;
import com.example.BillingCentreTest.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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
        return taskRepository.findByDate(date).stream()
                .sorted(Comparator.comparing(Task::getType))
                .collect(Collectors.toList());
    }

    public Task findOne(long id) {
        Optional<Task> foundTask = taskRepository.findById(id);
        return foundTask.orElse(null);
    }

//    public List<String> findType() {
//        return taskRepository.findType();
//    }

    @Transactional
    public void save(Task task) {
        taskRepository.save(task);
    }

//    @Transactional
//    public void update(long id, Task updatedTask) {
//        updatedTask.setId(id);
//        taskRepository.save(updatedTask);
//    }

    @Transactional
    public void delete(long id) {
        taskRepository.deleteById(id);
    }
}
