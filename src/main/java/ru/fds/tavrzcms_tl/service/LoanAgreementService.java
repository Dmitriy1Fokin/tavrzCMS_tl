package ru.fds.tavrzcms_tl.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fds.tavrzcms_tl.dto.LoanAgreementDto;
import ru.fds.tavrzcms_tl.dto.PledgeAgreementDto;
import ru.fds.tavrzcms_tl.service.feign.TavrzcmsAPIFeignService;
import ru.fds.tavrzcms_tl.utils.Utils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LoanAgreementService {

    private final TavrzcmsAPIFeignService tavrzcmsAPIFeignService;
    private final Utils utils;

    public LoanAgreementService(TavrzcmsAPIFeignService tavrzcmsAPIFeignService,
                                Utils utils) {
        this.tavrzcmsAPIFeignService = tavrzcmsAPIFeignService;
        this.utils = utils;
    }

    public Integer countOfCurrentLoanAgreementsByEmployee(Long employeeId){
        return tavrzcmsAPIFeignService.getCountOfCurrentLoanAgreementByEmployee(employeeId);
    }

    public List<LoanAgreementDto> getCurrentLoanAgreementByEmployee(Long employeeId, Pageable pageable){
        return tavrzcmsAPIFeignService.getCurrentLoanAgreementByEmployee(pageable, employeeId);
    }

    public Integer countOfAllCurrentLoanAgreements(){
        return tavrzcmsAPIFeignService.getCountOfLoanAgreements();
    }

    public List<LoanAgreementDto> getLoanAgreements(Pageable pageable){
        return tavrzcmsAPIFeignService.getLoanAgreements(pageable);
    }

    public List<LoanAgreementDto> getCurrentLoanAgreementsByPledgeAgreement(Long pledgeAgreementId){
        return tavrzcmsAPIFeignService.getCurrentLoanAgreementByPledgeAgreement(pledgeAgreementId);
    }

    public List<LoanAgreementDto> getClosedLoanAgreementsByPledgeAgreement(Long pledgeAgreementId){
        return tavrzcmsAPIFeignService.getClosedLoanAgreementByPledgeAgreement(pledgeAgreementId);
    }

    public List<LoanAgreementDto> getCurrentLoanAgreementsByClient(Long clientId){
        return tavrzcmsAPIFeignService.getCurrentLoanAgreementsByClient(clientId);
    }

    public List<LoanAgreementDto> getClosedLoanAgreementsByClient(Long clientId){
        return tavrzcmsAPIFeignService.getClosedLoanAgreementsByClient(clientId);
    }

    public LoanAgreementDto getLoanAgreementById(Long loanAgreementId){
        return tavrzcmsAPIFeignService.getLoanAgreement(loanAgreementId);
    }

    public List<LoanAgreementDto> getLoanAgreementByPledgeAgreements(List<PledgeAgreementDto> pledgeAgreements){
        return tavrzcmsAPIFeignService.getAllLoanAgreementByPledgeAgreements(pledgeAgreements
                .stream().map(PledgeAgreementDto::getPledgeAgreementId).collect(Collectors.toList()));
    }

    public List<LoanAgreementDto> getLoanAgreementBySearchCriteria(Map<String, String> searchParam, Pageable pageable){
        if(Objects.nonNull(searchParam.get("loanerName"))){
            searchParam.putAll(utils.ExtractClientName(Map.of("typeOfClient", searchParam.get("typeOfClient"), "clientName", searchParam.get("loanerName"))));
        }
        return tavrzcmsAPIFeignService.getLoanAgreementBySearchCriteria(searchParam, pageable);
    }

    @Transactional
    public LoanAgreementDto updateLoanAgreement(LoanAgreementDto loanAgreementDto){
        return tavrzcmsAPIFeignService.updateLoanAgreement(loanAgreementDto);
    }

    @Transactional
    public LoanAgreementDto inseertLoanAgreement(LoanAgreementDto loanAgreementDto){
        return tavrzcmsAPIFeignService.insertLoanAgreement(loanAgreementDto);
    }
}
