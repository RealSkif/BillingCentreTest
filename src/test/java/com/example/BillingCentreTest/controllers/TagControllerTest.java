package com.example.BillingCentreTest.controllers;

import com.example.BillingCentreTest.dto.TagDTO;
import com.example.BillingCentreTest.dto.TaskDTO;
import com.example.BillingCentreTest.models.Tag;
import com.example.BillingCentreTest.models.Task;
import com.example.BillingCentreTest.services.TagService;
import com.example.BillingCentreTest.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class TagControllerTest {

    @Mock
    private TaskService taskService;

    @Mock
    private TagService tagService;

    private TagController tagController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        tagController = new TagController(tagService, taskService, new ModelMapper());
    }




    @Test
    void findAllTagsWithTasks() {
        List<Tag> expectedTags = new ArrayList<>();
        when(tagService.findAllTagsWithTasks()).thenReturn(expectedTags);
        List<Tag> result = tagController.findAllTagsWithTasks();
        assertEquals(expectedTags, result);
    }

    @Test
    void findOne() {
        String header = "header";
        Tag tag = new Tag();
        when(tagService.findByHeader(header)).thenReturn(tag);
        Tag result = tagController.findOne(header);
        assertEquals(tag, result);
    }

    @Test
    void create() {
        TagDTO tagDTO = new TagDTO();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        ResponseEntity<String> response = tagController.create(tagDTO, bindingResult);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Тег успешно сохранен", response.getBody());
    }

    @Test
    void update() {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(1);
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(tagService.findById(tagDTO.getId())).thenReturn(true);
        ResponseEntity<String> response = tagController.update(tagDTO, bindingResult);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Тег успешно изменен", response.getBody());
    }

    @Test
    void delete() {
        String header = "header";
        when(tagService.findByHeader(header)).thenReturn(new Tag());
        ResponseEntity<String> response = tagController.delete(header);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Тег успешно удален", response.getBody());
    }

}