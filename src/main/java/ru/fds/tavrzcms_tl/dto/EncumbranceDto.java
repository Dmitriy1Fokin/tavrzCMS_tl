package ru.fds.tavrzcms_tl.dto;

import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import ru.fds.tavrzcms_tl.dictionary.TypeOfEncumbrance;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@ToString
public class EncumbranceDto{

    private Long encumbranceId;

    @NotBlank(message = "Обязательно для заполнения")
    private String nameEncumbrance;

    @NotNull
    private TypeOfEncumbrance typeOfEncumbrance;

    @NotBlank(message = "Обязательно для заполнения")
    private String inFavorOfWhom;

    @NotNull(message = "Обязательно для заполнения")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateBegin;

    @NotNull(message = "Обязательно для заполнения")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateEnd;

    @NotBlank(message = "Обязательно для заполнения")
    private String numOfEncumbrance;

    @NotNull
    private Long pledgeSubjectId;
}
