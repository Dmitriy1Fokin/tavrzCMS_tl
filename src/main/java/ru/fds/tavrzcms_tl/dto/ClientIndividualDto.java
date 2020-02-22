package ru.fds.tavrzcms_tl.dto;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@ToString
public class ClientIndividualDto{

    @NotBlank(message = "Обязательно для заполнения")
    private String surname;

    @NotBlank(message = "Обязательно для заполнения")
    private String name;

    private String patronymic;

    @Pattern(regexp = "^$|[0-9]{4} [0-9]{6}",
            message = "Неверное значение")
    private String pasportNum;

}
