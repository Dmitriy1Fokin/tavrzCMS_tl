package ru.fds.tavrzcms_tl.dto;

import lombok.Getter;
import lombok.ToString;
import ru.fds.tavrzcms_tl.dictionary.TypeOfEquipment;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@ToString
public class PledgeSubjectEquipmentDto{

    @NotBlank(message = "Обязательно для заполнения")
    private String brand;

    @NotBlank(message = "Обязательно для заполнения")
    private String model;

    private String serialNum;

    @Min(value = 1900, message = "Неверное значение")
    @Max(value = 2100, message = "Неверное значение")
    private Integer yearOfManufacture;

    private String inventoryNum;

    private TypeOfEquipment typeOfEquipment;

    @Positive(message = "Значение должно быть больше нуля")
    private Double productivity;

    private String typeOfProductivity;
}
