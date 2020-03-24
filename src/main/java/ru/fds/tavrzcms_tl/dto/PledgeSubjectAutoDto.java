package ru.fds.tavrzcms_tl.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.fds.tavrzcms_tl.dictionary.TypeOfAuto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;


@Getter
@Setter
@ToString
public class PledgeSubjectAutoDto{

    @NotBlank(message = "Обязательно для заполнения")
    private String brand;

    @NotBlank(message = "Обязательно для заполнения")
    private String model;

    @Pattern(regexp = "^$|[0-9A-Z]{17}",
            message = "Неверное значение")
    private String vin;

    private String numOfEngine;

    private String numOfPTS;

    @Min(value = 1900, message = "Неверное значение")
    @Max(value = 2100, message = "Неверное значение")
    private Integer yearOfManufacture;

    private String inventoryNum;

    private TypeOfAuto typeOfAuto;

    @Positive(message = "Значение должно быть больше нуля")
    private Double horsepower;

}
