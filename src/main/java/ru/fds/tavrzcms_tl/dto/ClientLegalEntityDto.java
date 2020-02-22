package ru.fds.tavrzcms_tl.dto;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@ToString
public class ClientLegalEntityDto{

    @NotBlank(message = "Обязательно для заполнения")
    private String organizationalForm;

    @NotBlank(message = "Обязательно для заполнения")
    private String name;

    @Pattern(regexp = "^$|[0-9]{10}",
            message = "Неверное значение")
    private String inn;
}
