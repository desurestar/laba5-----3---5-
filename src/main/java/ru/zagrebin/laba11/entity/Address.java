package ru.zagrebin.laba11.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private Long id;

    private String city;
    private String street;
    private String house;
    private String apartment;
    private List<Student> students;

}
