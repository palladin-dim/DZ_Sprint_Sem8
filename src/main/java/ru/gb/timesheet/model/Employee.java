package ru.gb.timesheet.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private int age;
    private String departmentName;

    @ManyToMany(mappedBy = "employees")
    private Set<Project> projects = new HashSet<>();

    public Employee() {
    }

    public Employee(String firstName, String lastName, int age, String departmentName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.departmentName = departmentName;
    }
}

