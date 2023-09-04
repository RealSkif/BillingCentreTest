package com.example.BillingCentreTest.repositories;

import com.example.BillingCentreTest.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("SELECT DISTINCT t FROM Tag t JOIN FETCH t.tasks")
    List<Tag> findAllTagsWithTasks();
    Tag findByHeader(String header);


}