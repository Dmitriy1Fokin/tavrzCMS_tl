package ru.fds.tavrzcms_tl.dto;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
public class EmployeeDto{

    private Long employeeId;

    @NotBlank(message = "Обязательно для заполнения")
    private String surname;

    @NotBlank(message = "Обязательно для заполнения")
    private String name;

    private String patronymic;

    private Long appUserId;

    private String fullName;

}
