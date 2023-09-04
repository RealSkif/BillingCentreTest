package com.example.BillingCentreTest.services;

import com.example.BillingCentreTest.models.Task;
import com.example.BillingCentreTest.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    private TaskService taskService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        taskService = new TaskService(taskRepository);
    }

    @Test
    public void testFindByDate() {
        Date date = new Date();
        List<Task> tasks = new ArrayList<>();
        when(taskRepository.findByDate(date)).thenReturn(tasks);
        List<Task> result = taskService.findByDate(date);
        assertEquals(tasks, result);
    }

    @Test
    public void testFindOne() {
        long taskId = 1;
        Task task = new Task();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        Task result = taskService.findOne(taskId);
        assertEquals(task, result);
    }

    @Test
    public void testGetTypes() {
        List<String> types = new ArrayList<>();
        when(taskRepository.getTypes()).thenReturn(types);
        List<String> result = taskService.getTypes();
        assertEquals(types, result);
    }

    @Test
    public void testSave() {
        Task task = new Task();
        taskService.save(task);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testDelete() {
        long taskId = 1;
        taskService.delete(taskId);
        verify(taskRepository, times(1)).deleteById(taskId);
    }
}
