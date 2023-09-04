package com.example.BillingCentreTest.controllers;

import com.example.BillingCentreTest.dto.TagDTO;
import com.example.BillingCentreTest.models.Tag;
import com.example.BillingCentreTest.models.Task;
import com.example.BillingCentreTest.services.TagService;
import com.example.BillingCentreTest.services.TaskService;
import com.example.BillingCentreTest.utills.TagException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;
    private final TaskService taskService;
    private final ModelMapper modelMapper;

    @Autowired
    public TagController(TagService tagService, TaskService taskService, ModelMapper modelMapper) {
        this.tagService = tagService;
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }

    /**
     * @return возвращает список тегов, у которых имеются задачи
     */
    @GetMapping()
    public List<Tag> findAllTagsWithTasks() {
        return tagService.findAllTagsWithTasks();
    }

    /* Как и в контроллере для задач не уверен, какой способ лучше:
     Вместо @RequestParam и маппинга на постоянный URL можно отправлять
     запрос на единый маппинг контроллера tag/{header} и использовать аннотацию @PathVariable */

    /**
     * @param header - заголовок искомого тега
     * @return возвращает найденный по заголовку тег
     */
    @GetMapping("/one")
    public Tag findOne(@RequestParam("header") String header) {
        if (tagService.findByHeader(header) == null)
            throw new TagException("Тег с заголовком " + header + " не найден");
        return tagService.findByHeader(header);
    }

    /*
      Я решил, что тег может сразу прийти со списком его задач, поэтому сначала проверяю его заголовок на уникальность,
      а потом отдельно перезаписываю его задачи. Таким образом задачи заносятся в бд уже со ссылкой на этот тег
     */

    /**
     * @param tagDTO        - объект класса TegDTO
     * @param bindingResult - объект класса bindingResult для хранения ошибок валидации
     * @return возвращает сообщение и код о результате сохранения
     */
    @PostMapping()
    public ResponseEntity<String> create(@RequestBody @Valid TagDTO tagDTO,
                                         BindingResult bindingResult) {
        if (!createErrorMessage(bindingResult).isEmpty())
            throw new TagException(createErrorMessage(bindingResult).toString());
        Tag tag = convertToTag(tagDTO);
        if (tagService.findByHeader(tag.getHeader()) != null)
            throw new TagException("Тег с заголовком " + tag.getHeader() + " уже существует");
        tagService.save(tag);
        checkAndSaveTask(tag);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Тег успешно сохранен");
    }

    /**
     * @param tagDTO        - объект класса TegDTO
     * @param bindingResult - объект класса bindingResult для хранения ошибок валидации
     * @return возвращает сообщение и код о результате изменения
     */
    @PatchMapping()
    public ResponseEntity<String> update(@RequestBody @Valid TagDTO tagDTO,
                                         BindingResult bindingResult) {
        if (!createErrorMessage(bindingResult).isEmpty())
            throw new TagException(createErrorMessage(bindingResult).toString());
        Tag tag = convertToTag(tagDTO);
        if (!tagService.findById(tag.getId()))
            throw new TagException("Тег с ID " + tag.getId() + "  не существует");
        if (tagService.findByHeader(tag.getHeader()) != null)
            throw new TagException("Тег с заголовком " + tag.getHeader() + " уже существует");
        else {
            tagService.save(tag);
            checkAndSaveTask(tag);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Тег успешно изменен");
        }
    }

    /**
     * @param header - заголок удаляемого тега
     * @return возвращает сообщение и код о результате удаления
     */
    @DeleteMapping()
    public ResponseEntity<String> delete(@RequestParam("header") String header) {
        if (tagService.findByHeader(header) == null)
            throw new TagException("Тег с заголовком " + header + " не найден");
        tagService.delete(header);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Тег успешно удален");
    }

    private Tag convertToTag(TagDTO tag) {
        return modelMapper.map(tag, Tag.class);
    }

    /*
      Проверку на наличие задач тега и их сохранение вынес в отдельный метод
    */

    /**
     * @param tag - объект класса Tag
     */
    private void checkAndSaveTask(Tag tag) {
        if (tag.getTasks() != null) {
            for (Task task : tag.getTasks()) {
                task.setTag(tagService.findByHeader(tag.getHeader()));
                taskService.save(task);
            }
        }
    }

    /**
     * Обработка ошибок валидации и составление сообщения с ошибками
     *
     * @param bindingResult - объект класса bindingResult
     * @return StringBuilder c сообщением об ошибке
     */
    private StringBuilder createErrorMessage(BindingResult bindingResult) {
        StringBuilder errorsMessages = new StringBuilder();
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorsMessages.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage()).append(";\n");
            }
        }
        return errorsMessages;
    }

    /**
     * Перехват ошибок валидации и отправление сообщения с ними отправителю
     *
     * @param ex - объект класса TagException
     */
    @ExceptionHandler
    public ResponseEntity<String> handleValidationExceptions(TagException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}