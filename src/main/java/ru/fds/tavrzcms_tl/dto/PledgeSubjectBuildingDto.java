package ru.fds.tavrzcms_tl.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.fds.tavrzcms_tl.dictionary.MarketSegment;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@ToString
public class PledgeSubjectBuildingDto{

    @NotNull(message = "Обязательно для заполнения")
    @PositiveOrZero(message = "Значение должно быть больше или ровно нулю")
    private Double area;

    @NotNull(message = "Обязательно для заполнения")
    @Pattern(regexp = "[0-9]{2}:[0-9]{2}:[0-9]{3,7}:[0-9]+",
            message = "Неверное значение")
    private String cadastralNum;

    private String conditionalNum;

    @NotNull(message = "Обязательно для заполнения")
    @Min(value = 1, message = "Неверное значение")
    @Max(value = 100, message = "Неверное значение")
    private Integer readinessDegree;

    @NotNull(message = "Обязательно для заполнения")
    @Min(value = 1800, message = "Неверное значение")
    @Max(value = 2100, message = "Неверное значение")
    private Integer yearOfConstruction;

    private MarketSegment marketSegment;
}
