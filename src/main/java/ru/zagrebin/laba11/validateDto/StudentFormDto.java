package ru.zagrebin.laba11.validateDto;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentFormDto {
	private Long id;

    @NotBlank
    @Size(min=3, max=50)
    private String lastName;

    @NotBlank
    @Size(min=3, max=50)
    private String firstName;

    @Size(min=3, max=50)
    private String patronymic;

    @NotBlank
    @Pattern(regexp ="Мужской|Женский")
    private String gender;

    @NotBlank
    private String nationality;

    @NotNull
    @Min(value = 100)
    @Max(value = 250)
    private Double height;

    @NotNull
    @Min(value = 25)
    @Max(value = 300)
    private Double weight;

    @NotNull
    @Past
    private LocalDate birthDate;

    @NotNull
    @Pattern(regexp = "^\\+7\\d{10}$")
    private String phoneNumber;

    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "5.0")
    private Double gpa;

    @NotBlank
    private String city;

    @NotBlank
    private String street;

    @NotBlank
    private String house;

    private String apartment;

    @NotNull
    private Long studentGroupId;
}
