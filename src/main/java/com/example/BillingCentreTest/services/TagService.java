package com.example.BillingCentreTest.services;

import com.example.BillingCentreTest.models.Tag;
import com.example.BillingCentreTest.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

//    public List<Tag> findAllTagsWithTasks() {
//        return tagRepository.findAllTagsWithTasks();
//    }

    public Tag findByHeader(String header) {
        return tagRepository.findByHeader(header);
    }

    @Transactional
    public void save(Tag tag) {
        tagRepository.save(tag);
    }


//    @Transactional

//    public void delete(String header) {
//        tagRepository.delete(header);
//    }
}
