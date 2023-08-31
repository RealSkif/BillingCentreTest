package com.example.BillingCentreTest.controllers;

import com.example.BillingCentreTest.models.Tag;
import com.example.BillingCentreTest.models.Task;
import com.example.BillingCentreTest.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

//    @GetMapping()
//    public List<Tag> findAllTagsWithTasks() {
//        return tagService.findAllTagsWithTasks();
//    }


    @GetMapping()
    public Tag findOne(@RequestParam("header") String header) {
        return tagService.findByHeader(header);
    }


    @PostMapping("/add")
    public void create(@RequestBody Tag tag) {
        tagService.save(tag);
    }


    @PatchMapping()
    public void update(@RequestBody Tag tag) {
        tagService.save(tag);
    }

//    @DeleteMapping()
//    public void delete(@RequestParam("header") String header) {
//        tagService.delete(header);
//    }
}
