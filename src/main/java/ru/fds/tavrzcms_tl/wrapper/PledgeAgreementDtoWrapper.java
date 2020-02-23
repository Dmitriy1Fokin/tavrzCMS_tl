package ru.fds.tavrzcms_tl.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.fds.tavrzcms_tl.dto.PledgeAgreementDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@ToString
@Getter
public class PledgeAgreementDtoWrapper {

    @Valid
    private PledgeAgreementDto pledgeAgreementDto;
    @NotNull
    private List<Long> loanAgreementsIds;
}
