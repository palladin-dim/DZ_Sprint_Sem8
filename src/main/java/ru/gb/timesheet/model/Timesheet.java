package ru.gb.timesheet.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Setter
@Getter
@Data
@Entity
public class Timesheet {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    private String projectName;

    @Column(nullable = false)
    private int minutes;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}