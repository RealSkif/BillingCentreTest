package com.example.BillingCentreTest.services;

import com.example.BillingCentreTest.models.Tag;
import com.example.BillingCentreTest.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {
    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> findAllTagsWithTasks() {
        return tagRepository.findAllTagsWithTasks();
    }

    /*
     * Возвращает тег, найденный по уникальному заголовку, если в нем присутствуют задачи,
     * то сортирует по приоритету
     * */
    public Tag findByHeader(String header) {
        Tag tag = tagRepository.findByHeader(header);
        if (tag != null && tag.getTasks() != null) {
            tag.getTasks().sort(Comparator.comparingInt(task -> task.getType().getValue()));
        }
        return tag;
    }

    @Transactional
    public void save(Tag tag) {
        tagRepository.save(tag);
    }

    @Transactional
    public void delete(String header) {
        tagRepository.deleteById(findByHeader(header).getId());
    }

    public boolean findById(long id) {
        return tagRepository.findById(id).isPresent();
    }
}
