package com.example.BillingCentreTest.controllers;

import com.example.BillingCentreTest.models.Task;
import com.example.BillingCentreTest.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping()
    public List<Task> findAll() {
        return taskService.findAll();
    }
    @GetMapping("/byDate")
    public List<Task> findbyDate(@PathVariable("date") Date date) {
        return taskService.findbyDate(date);
    }

    @GetMapping("/{id}")
    public Task findOne(@PathVariable("id") UUID id) {
        return taskService.findOne(id);
    }


    @PostMapping("/add")
    public void create(@RequestBody Task task) {
        taskService.save(task);

    }


    @PatchMapping("/{id}")
    public void update(@PathVariable("id") UUID id) {
        Task task = taskService.findOne(id);
        taskService.update(id, task);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") UUID id) {
        taskService.delete(id);
    }
}
