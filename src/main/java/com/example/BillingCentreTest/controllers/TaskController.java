package com.example.BillingCentreTest.controllers;

import com.example.BillingCentreTest.models.Task;
import com.example.BillingCentreTest.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    public List<Task> findbyDate(@RequestParam("date") Date date) {
        return taskService.findbyDate(date);
    }

    @GetMapping("/one")
    public Task findOne(@RequestParam("id") long id) {
        return taskService.findOne(id);
    }

//    @GetMapping()
//    public List<String> findType() {
//        return taskService.findType();
//    }

    @PostMapping("/add")
    public ResponseEntity<Task> create(@RequestBody Task task) {
        taskService.save(task);
        return ResponseEntity.ok().build();
    }


    @PatchMapping()
    public ResponseEntity<Task> update(@RequestBody Task task) {
        if (taskService.findOne(task.getId()) == null) {
            return ResponseEntity.notFound().build();
        }
        taskService.save(task);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    public ResponseEntity<Task> delete(@RequestParam("id") long id) {
        if (taskService.findOne(id) == null) {
            return ResponseEntity.notFound().build();
        }
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }
}
