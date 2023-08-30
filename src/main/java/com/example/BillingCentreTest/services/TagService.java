package com.example.BillingCentreTest.services;

import com.example.BillingCentreTest.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    private final TagRepository tagRepository;
@Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }
}
