package ru.fds.tavrzcms_tl.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.fds.tavrzcms_tl.dictionary.TypeOfSecurities;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@ToString
public class PledgeSubjectSecuritiesDto{

    @NotNull(message = "Обязательно для заполнения")
    @PositiveOrZero(message = "Значение должно быть больше или ровно нулю")
    private Double nominalValue;

    @NotNull(message = "Обязательно для заполнения")
    @PositiveOrZero(message = "Значение должно быть больше или ровно нулю")
    private Double actualValue;

    private TypeOfSecurities typeOfSecurities;
}
