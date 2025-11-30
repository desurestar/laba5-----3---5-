package ru.zagrebin.laba11.form;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentForm {
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
    private String city;
    private String street;
    private String house;
    private String apartment;
    private Long studentGroupId;
}
