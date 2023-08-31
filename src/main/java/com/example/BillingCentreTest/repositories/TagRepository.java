package com.example.BillingCentreTest.repositories;

import com.example.BillingCentreTest.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

//    @Query("SELECT f FROM Tag f JOIN FETCH f.task s WHERE SIZE(s) > 0")
//    List<Tag> findAllTagsWithTasks();

    Tag findByHeader(String header);

//    void delete(String header);

}

