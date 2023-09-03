package com.example.BillingCentreTest.dto;

import com.example.BillingCentreTest.models.Tag;
import com.example.BillingCentreTest.utills.TaskTypes;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
public class TaskDTO {
    private long id;
    /*
    * Как прикрутить валидацию к Enum так и не придумал
    * */
    @Enumerated(EnumType.STRING)
    private TaskTypes type;
    @NotBlank(message = "Наименование не должно быть пустым")
    private String title;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Дата не должна быть меньше текущей")
    private Date date;
    private Tag tag;

}
