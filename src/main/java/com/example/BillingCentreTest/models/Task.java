package com.example.BillingCentreTest.models;

import com.example.BillingCentreTest.utills.TaskTypes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "task")
public class Task {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /*
     * Тип задачи, показывающий приоритет задачи вынес в Enum пакете utills
     * */
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TaskTypes type;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "date")
    private Date date;
    @ManyToOne
    @JoinColumn(name = "tag_id", referencedColumnName = "id")
    private Tag tag;
}
