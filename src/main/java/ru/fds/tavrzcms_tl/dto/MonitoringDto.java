package ru.fds.tavrzcms_tl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import ru.fds.tavrzcms_tl.dictionary.StatusOfMonitoring;
import ru.fds.tavrzcms_tl.dictionary.TypeOfMonitoring;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MonitoringDto{

    private Long monitoringId;

    @NotNull(message = "Обязательно для заполнения")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateMonitoring;

    @NotNull(message = "Обязательно для заполнения")
    private StatusOfMonitoring statusMonitoring;

    @NotBlank(message = "Обязательно для заполнения")
    private String employee;

    @NotNull(message = "Обязательно для заполнения")
    private TypeOfMonitoring typeOfMonitoring;

    private String notice;

    private BigDecimal collateralValue;

    private Long pledgeSubjectId;
}
