package com.example.BillingCentreTest.services;

import com.example.BillingCentreTest.models.Tag;
import com.example.BillingCentreTest.models.Task;
import com.example.BillingCentreTest.repositories.TagRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    private TagService tagService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        tagService = new TagService(tagRepository);
    }
    @Test
    void findAllTagsWithTasks() {
        List<Tag> tags = new ArrayList<>();
        when(tagRepository.findAllTagsWithTasks()).thenReturn(tags);
        List<Tag> result = tagService.findAllTagsWithTasks();
        assertEquals(tags, result);
    }

    @Test
    void findByHeader() {
        String header = "header";
        Tag tag = new Tag();
        when(tagRepository.findByHeader(header)).thenReturn(tag);
        Tag result = tagService.findByHeader(header);
        assertEquals(tag, result);
    }

    @Test
    void save() {
        Tag tag = new Tag();
        tagService.save(tag);
        verify(tagRepository, times(1)).save(tag);
    }

    @Test
    public void testDelete() {
        String header = "header";
        Tag tag = new Tag();
        when(tagRepository.findByHeader(header)).thenReturn(tag);
        tagService.delete(header);
        verify(tagRepository, times(1)).deleteById(tag.getId());
    }

    @Test
    public void testFindById() {
        long tagId = 1;
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(new Tag()));
        boolean result = tagService.findById(tagId);
        assertTrue(result);
    }
}