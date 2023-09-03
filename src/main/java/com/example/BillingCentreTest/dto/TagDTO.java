package com.example.BillingCentreTest.dto;

import com.example.BillingCentreTest.models.Task;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class TagDTO {
    private long id;
    @NotBlank
    private String header;
    private List<Task> tasks;
}
