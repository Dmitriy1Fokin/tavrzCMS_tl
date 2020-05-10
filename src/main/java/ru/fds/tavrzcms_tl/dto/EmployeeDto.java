package ru.fds.tavrzcms_tl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
