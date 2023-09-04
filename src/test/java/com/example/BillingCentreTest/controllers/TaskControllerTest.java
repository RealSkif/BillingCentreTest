package com.example.BillingCentreTest.controllers;

import com.example.BillingCentreTest.dto.TaskDTO;
import com.example.BillingCentreTest.models.Task;
import com.example.BillingCentreTest.services.TaskService;
import com.example.BillingCentreTest.services.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @Mock
    private TagService tagService;

    private TaskController taskController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        taskController = new TaskController(taskService, tagService, new ModelMapper());
    }

    @Test
    public void testFindByDate() {
        Date date = new Date();
        when(taskService.findByDate(date)).thenReturn(List.of(new Task(), new Task()));
        List<Task> result = taskController.findByDate(date);
        assertEquals(2, result.size());
    }
    @Test
    public void testFindOne() {
        long id = 1;
        Task task = new Task();
        when(taskService.findOne(id)).thenReturn(task);
        Task result = taskController.findOne(id);
        assertEquals(task, result);
    }
    @Test
    public void testGetTypes() {
        List<String> expectedTypes = List.of("Type1", "Type2");
        when(taskService.getTypes()).thenReturn(expectedTypes);
        List<String> result = taskController.getTypes();
        assertEquals(expectedTypes, result);
    }

    @Test
    public void testCreate() {
        TaskDTO taskDTO = new TaskDTO();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        ResponseEntity<String> response = taskController.create(taskDTO, bindingResult);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Задача успешно сохранена", response.getBody());
    }

    @Test
    public void testUpdate() {
        TaskDTO taskDTO = new TaskDTO();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(taskService.findOne(taskDTO.getId())).thenReturn(new Task());
        ResponseEntity<String> response = taskController.update(taskDTO, bindingResult);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Задача успешно изменена", response.getBody());
    }

    @Test
    public void testDelete() {
        long id = 1;
        when(taskService.findOne(id)).thenReturn(new Task());
        ResponseEntity<String> response = taskController.delete(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Задача успешно удалена", response.getBody());
    }
}
