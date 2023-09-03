package com.example.BillingCentreTest.services;

import com.example.BillingCentreTest.models.Task;
import com.example.BillingCentreTest.repositories.TaskRepository;
import com.example.BillingCentreTest.services.TaskService;
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
        MockitoAnnotations.openMocks(this); // Initialize mocks
        taskService = new TaskService(taskRepository);
    }

    @Test
    public void testFindByDate() {
        Date date = new Date(); // Replace with a specific date
        List<Task> mockTasks = new ArrayList<>(); // Replace with mock tasks
        when(taskRepository.findByDate(date)).thenReturn(mockTasks);

        List<Task> result = taskService.findByDate(date);

        // Assertions
        assertEquals(mockTasks, result);
    }

    @Test
    public void testFindOne() {
        long taskId = 1; // Replace with a specific task ID
        Task mockTask = new Task(); // Replace with a mock task
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(mockTask));

        Task result = taskService.findOne(taskId);

        // Assertions
        assertEquals(mockTask, result);
    }

    @Test
    public void testGetTypes() {
        List<String> mockTypes = new ArrayList<>(); // Replace with mock types
        when(taskRepository.getTypes()).thenReturn(mockTypes);

        List<String> result = taskService.getTypes();

        // Assertions
        assertEquals(mockTypes, result);
    }

    @Test
    public void testSave() {
        Task mockTask = new Task(); // Replace with a mock task

        taskService.save(mockTask);

        // Verify that the save method was called once with the mock task
        verify(taskRepository, times(1)).save(mockTask);
    }

    @Test
    public void testDelete() {
        long taskId = 1; // Replace with a specific task ID

        taskService.delete(taskId);

        // Verify that the deleteById method was called once with the specified task ID
        verify(taskRepository, times(1)).deleteById(taskId);
    }
}
