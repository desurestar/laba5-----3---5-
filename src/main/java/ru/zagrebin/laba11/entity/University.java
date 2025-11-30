package ru.zagrebin.laba11.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class University {
    private Long id;

    private String name;

    private List<StudentGroup> studentGroups;

}
