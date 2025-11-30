package ru.zagrebin.laba11.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Long id;

    private String lastName;

    private String firstName;

    private String patronymic;

    private String gender;

    private String nationality;

    private Double height;

    private Double weight;

    private LocalDate birthDate;

    private String phoneNumber;

    private Double gpa;

    private String speciality;

    private Address address;

    private StudentGroup studentGroup;

    private List<Course> courses;
}
