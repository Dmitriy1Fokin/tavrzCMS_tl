package ru.fds.tavrzcms_tl.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.fds.tavrzcms_tl.dto.LoanAgreementDto;
import ru.fds.tavrzcms_tl.service.feign.TavrzcmsAPIFeignService;

import java.util.List;

@Service
public class LoanAgreementService {

    private final TavrzcmsAPIFeignService tavrzcmsAPIFeignService;

    public LoanAgreementService(TavrzcmsAPIFeignService tavrzcmsAPIFeignService) {
        this.tavrzcmsAPIFeignService = tavrzcmsAPIFeignService;
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
}
