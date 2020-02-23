package ru.fds.tavrzcms_tl.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.fds.tavrzcms_tl.dto.CostHistoryDto;
import ru.fds.tavrzcms_tl.dto.MonitoringDto;
import ru.fds.tavrzcms_tl.dto.PledgeSubjectDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@ToString
@Getter
public class PledgeSubjectDtoNewWrapper {

    @Valid
    private PledgeSubjectDto pledgeSubjectDto;
    @Valid
    private CostHistoryDto costHistoryDto;
    @Valid
    private MonitoringDto monitoringDto;
    @NotNull
    private List<Long> pledgeAgreementsIds;

}
