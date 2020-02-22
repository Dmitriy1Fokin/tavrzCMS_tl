package ru.fds.tavrzcms_tl.dto;

import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import ru.fds.tavrzcms_tl.dictionary.StatusOfAgreement;
import ru.fds.tavrzcms_tl.dictionary.TypeOfPledgeAgreement;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@ToString
public class PledgeAgreementDto{

    private Long pledgeAgreementId;

    @NotBlank(message = "Обязательно для заполнения")
    private String numPA;

    @NotNull(message = "Обязательно для заполнения")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateBeginPA;

    @NotNull(message = "Обязательно для заполнения")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateEndPA;

    @NotNull
    private TypeOfPledgeAgreement pervPosl;

    @NotNull
    private StatusOfAgreement statusPA;

    private String noticePA;

    @NotNull(message = "Обязательно для заполнения")
    @PositiveOrZero(message = "Значение должно быть больше или ровно нулю")
    private BigDecimal zsDz;

    @NotNull(message = "Обязательно для заполнения")
    @PositiveOrZero(message = "Значение должно быть больше или ровно нулю")
    private BigDecimal zsZz;

    @NotNull(message = "Обязательно для заполнения")
    @PositiveOrZero(message = "Значение должно быть больше или ровно нулю")
    private BigDecimal rsDz;

    @NotNull(message = "Обязательно для заполнения")
    @PositiveOrZero(message = "Значение должно быть больше или ровно нулю")
    private BigDecimal rsZz;

    @NotNull(message = "Обязательно для заполнения")
    @PositiveOrZero(message = "Значение должно быть больше или ровно нулю")
    private BigDecimal ss;

    @NotNull
    private Long clientId;

    private List<String> briefInfoAboutCollateral;

    private List<String> typesOfCollateral;

    private List<LocalDate> datesOfConclusions;

    private List<LocalDate> datesOfMonitoring;

    private List<String> resultsOfMonitoring;

    private String clientName;
}
