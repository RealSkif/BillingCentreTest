package com.example.BillingCentreTest.repositories;

import com.example.BillingCentreTest.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByDate(Date date);
    @Query("SELECT type FROM Task" )
    List<String> getTypes();

}