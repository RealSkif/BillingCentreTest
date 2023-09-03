package com.example.BillingCentreTest.controllers;

import com.example.BillingCentreTest.dto.TaskDTO;
import com.example.BillingCentreTest.models.Tag;
import com.example.BillingCentreTest.models.Task;
import com.example.BillingCentreTest.services.TagService;
import com.example.BillingCentreTest.services.TaskService;
import com.example.BillingCentreTest.utills.TagException;
import com.example.BillingCentreTest.utills.TaskException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;
    private final TagService tagService;
    private final ModelMapper modelMapper;


    @Autowired
    public TaskController(TaskService taskService, TagService tagService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.tagService = tagService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/date")
    public List<Task> findByDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return taskService.findByDate(date);
    }

    @GetMapping("/one")
    public Task findOne(@RequestParam("id") long id) {
        if (taskService.findOne(id) == null)
            throw new TaskException("Задача с ID " + id + " не найдена");
        return taskService.findOne(id);
    }

    /*
     * Возвращает несортированный список типов задач, использует кэширование
     * */
    @Cacheable("priorityTypesCache")
    @GetMapping("/type")
    public List<String> getTypes() {
        return taskService.getTypes();
    }

    /*
     * Так как задание не предполагает создание задач внутри сервиса, то смысла вставлять проверку на валидность даты
     * в конструктор или сеттер я посчитал ненужным. Эта проверка происходит в методах Create и Update этого контроллера
     * Так же если задача подается с тегом, то он обрабатывается и заносится в бд
     * */
    @PostMapping()
    public ResponseEntity<String> create(@RequestBody @Valid TaskDTO taskDTO,
                                         BindingResult bindingResult) {
        if (!createErrorMessage(bindingResult).isEmpty())
            throw new TaskException(createErrorMessage(bindingResult).toString());
        Task task = convertToTask(taskDTO);
        checkAndSaveTag(task);
        taskService.save(task);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Задача успешно сохранена");
    }


    @PatchMapping()
    public ResponseEntity<String> update(@RequestBody @Valid TaskDTO taskDTO,
                                         BindingResult bindingResult) {
        if (!createErrorMessage(bindingResult).isEmpty()) {
            throw new TaskException(createErrorMessage(bindingResult).toString());
        } else if (taskService.findOne(taskDTO.getId()) == null)
            throw new TaskException("Задача с таким ID не найдена");
        else {
            Task task = convertToTask(taskDTO);
            checkAndSaveTag(task);
            taskService.save(task);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Задача успешно изменена");
        }
    }

    @DeleteMapping()
    public ResponseEntity<String> delete(@RequestParam("id") long id) {
        if (taskService.findOne(id) == null)
            throw new TaskException("Задача с ID " + id + " не найдена");
        taskService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Задача успешно удалена");
    }

    private Task convertToTask(TaskDTO task) {
        return modelMapper.map(task, Task.class);
    }


    /*
     * Проверка наличия тега в задаче и сохранение его в бд, если такой не существовал
     * */
    private void checkAndSaveTag(Task task) {
        if (task.getTag() == null)
            return;
        Tag tag = tagService.findByHeader(task.getTag().getHeader());
        if (tag == null) {
            tagService.save(task.getTag());
            tag = tagService.findByHeader(task.getTag().getHeader());
        }
        task.setTag(tag);
    }

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

    @ExceptionHandler
    public ResponseEntity<String> handleValidationExceptions(TaskException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }


//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
//
//        try {
//            Path directoryPath = Paths.get("/src");
//            if (!Files.exists(directoryPath)) {
//                Files.createDirectories(directoryPath);
//            }
//
//            Path filePath = Paths.get("/src", file.getOriginalFilename());
//            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//            return ResponseEntity.ok("File uploaded successfully");
//        } catch (IOException e) {
//            return ResponseEntity.badRequest().body("Failed to upload file");
//        }
//    }
}