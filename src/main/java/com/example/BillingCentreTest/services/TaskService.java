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

    /*
     * Возвращает список задач на переданную дату с отсортированными типами приоритетов
     * */
    public List<Task> findByDate(Date date) {
        return taskRepository.findByDate(date).stream()
                .sorted(Comparator.comparingInt(task -> task.getType().getValue()))
                .collect(Collectors.toList());
    }

    public Task findOne(long id) {
        Optional<Task> foundTask = taskRepository.findById(id);
        return foundTask.orElse(null);
    }

    /*
     * Возвращает список приоритетов заголовков. Т.к. возвращается список строк, а не объекты Task,
     * то в названии метода не использовал слово find.
     * */
    public List<String> getTypes() {
        return taskRepository.getTypes();
    }

    @Transactional
    public void save(Task task) {
        taskRepository.save(task);
    }


    @Transactional
    public void delete(long id) {
        taskRepository.deleteById(id);
    }
}
