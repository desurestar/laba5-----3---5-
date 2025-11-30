package ru.zagrebin.laba11.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentGroup {
    private Long id;
    private String name;
    private String speciality;
    private University university;
    private List<Student> students;
}
