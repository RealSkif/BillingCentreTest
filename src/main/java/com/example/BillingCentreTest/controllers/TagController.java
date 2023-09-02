package com.example.BillingCentreTest.controllers;

import com.example.BillingCentreTest.models.Tag;
import com.example.BillingCentreTest.models.Task;
import com.example.BillingCentreTest.services.TagService;
import com.example.BillingCentreTest.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;

    private final TaskService taskService;


    @Autowired
    public TagController(TagService tagService, TaskService taskService) {
        this.tagService = tagService;
        this.taskService = taskService;
    }
/*
*
* */
    @GetMapping()
    public List<Tag> findAllTagsWithTasks() {
        return tagService.findAllTagsWithTasks();
    }

    /*
     * Как и в контроллере для задач не уверен, какой способ лучше:
     * Вместо @RequestParam и маппинга на постоянный URL можно отправлять
     * запрос на единый маппинг контроллера tag/{header} и использовать аннотацию @PathVariable
     * */
    @GetMapping("/one")
    public Tag findOne(@RequestParam("header") String header) {
        return tagService.findByHeader(header);
    }

    /*
     * Я решил, что тег может сразу прийти со списком его задач, поэтому сначала проверяю его заголовок на уникальность,
     * а потом отдельно перезаписываю его задачи. Таким образом задачи заносятся в бд уже со ссылкой на этот тег
     * */
    @PostMapping()
    public ResponseEntity<String> create(@RequestBody Tag tag) {
        if (tagService.findByHeader(tag.getHeader()) == null) {
            tagService.save(tag);
            checkAndSaveTask(tag);
            return ResponseEntity.ok().build();
        } else return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("Тег с заголовком " + tag.getHeader() + " уже существует");
    }

    @PatchMapping()
    public ResponseEntity<String> update(@RequestBody Tag tag) {
        if (tagService.findByHeader(tag.getHeader()) == null) {
            tagService.save(tag);
            checkAndSaveTask(tag);
            return ResponseEntity.ok().build();
        } else return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("Заголовок " + tag.getHeader() + " уже используется другим тегом");
    }

    @DeleteMapping()
    public ResponseEntity<String> delete(@RequestParam("header") String header) {
        if (tagService.findByHeader(header) == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Тег с заголовком " + header + " для изменения не найден");
        }
        tagService.delete(header);
        return ResponseEntity.ok().build();
    }

    /*
     * Проверку на наличие задач тега и их сохранение вынес в отдельный метод
     * */
    private void checkAndSaveTask(Tag tag) {
        if (tag.getTasks() != null) {
            for (Task task : tag.getTasks()) {
                task.setTag(tagService.findByHeader(tag.getHeader()));
                taskService.save(task);
            }
        }
    }
}
