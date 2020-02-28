package ru.fds.tavrzcms_tl.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.fds.tavrzcms_tl.dictionary.TypeOfTBO;

import javax.validation.constraints.Positive;

@Getter
@Setter
@ToString
public class PledgeSubjectTboDto{

    @Positive(message = "Значение должно быть больше нуля")
    private Integer countOfTBO;

    @Positive(message = "Значение должно быть больше нуля")
    private Double carryingAmount;

    private TypeOfTBO typeOfTBO;
}
