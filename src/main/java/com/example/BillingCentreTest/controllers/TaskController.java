package com.example.BillingCentreTest.controllers;

import com.example.BillingCentreTest.models.Tag;
import com.example.BillingCentreTest.models.Task;
import com.example.BillingCentreTest.services.TagService;
import com.example.BillingCentreTest.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;
    private final TagService tagService;


    @Autowired
    public TaskController(TaskService taskService, TagService tagService) {
        this.taskService = taskService;
        this.tagService = tagService;
    }


    @GetMapping()
    public List<Task> findAll() {
        return taskService.findAll();
    }

    @GetMapping("/date")
    public List<Task> findByDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return taskService.findByDate(date);
    }

    @GetMapping("/one")
    public Task findOne(@RequestParam("id") long id) {
        return taskService.findOne(id);
    }
    /*
     * Возвращает несортированный список типов задач
     * */
    @GetMapping("/type")
    public List<String> getTypes() {
        return taskService.getTypes();
    }

    /*
     * Так как задание не предполагает создание задач внутри сервиса, то смысла вставлять проверку на валидность даты
     * в конструктор или сеттер я посчитал ненужным. Это проверка происходит в методах Create и Update этого контроллера
     * Так же если задача подается с тегом, то он обрабатывается и заносится в бд
     * */
    @PostMapping()
    public ResponseEntity<String> create(@RequestBody Task task) {
        if (task.getDate().before(new Date())) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Дата задачи не может быть меньше текущей даты");
        }
        checkAndSaveTag(task);
        taskService.save(task);
        return ResponseEntity.ok().build();
    }


    @PatchMapping()
    public ResponseEntity<String> update(@RequestBody Task task) {
        if (taskService.findOne(task.getId()) == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Задачи с ID " + task.getId() + " для изменения не найдено");
        }
        if (task.getDate().before(new Date())) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Дата задачи не может быть меньше текущей даты");
        }
        checkAndSaveTag(task);
        taskService.save(task);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    public ResponseEntity<String> delete(@RequestParam("id") long id) {
        if (taskService.findOne(id) == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Задачи с ID " + id + " для удаления не найдено");
        }
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }

    /*
     * Проверка наличия тега в задаче и сохранение его в бд, если такой не существовал
     * */
    private void checkAndSaveTag(Task task) {
        if (task.getTag() == null)
            return;
        Tag tag = tagService.findByHeader(task.getTag().getHeader());
        if (tag == null)
            tagService.save(task.getTag());
        task.setTag(tag);
    }
}
