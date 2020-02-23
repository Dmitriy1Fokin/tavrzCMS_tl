package ru.fds.tavrzcms_tl.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.fds.tavrzcms_tl.dictionary.TypeOfPledgeAgreement;
import ru.fds.tavrzcms_tl.dto.PledgeAgreementDto;
import ru.fds.tavrzcms_tl.service.feign.TavrzcmsAPIFeignService;

import java.util.List;

@Service
public class PledgeAgreementService {

    private final TavrzcmsAPIFeignService tavrzcmsAPIFeignService;

    public PledgeAgreementService(TavrzcmsAPIFeignService tavrzcmsAPIFeignService) {
        this.tavrzcmsAPIFeignService = tavrzcmsAPIFeignService;
    }

    public Integer countOfCurrentPledgeAgreementForEmployee(Long employeeId){
        return tavrzcmsAPIFeignService.getCountOfCurrentPledgeAgreementByEmployee(employeeId);
    }

    public List<PledgeAgreementDto> getCurrentPledgeAgreementByEmployee(Long employeeId, Pageable pageable){
        return tavrzcmsAPIFeignService.getCurrentPledgeAgreementByEmployee(employeeId, pageable);
    }

    public Integer countOfCurrentPledgeAgreementForEmployee(Long employeeId, TypeOfPledgeAgreement typeOfPledgeAgreement){
        if(typeOfPledgeAgreement == TypeOfPledgeAgreement.PERV){
            return tavrzcmsAPIFeignService.getCountOfCurrentPervPledgeAgreementByEmployee(employeeId);
        }else{
            return tavrzcmsAPIFeignService.getCountOfCurrentPoslPledgeAgreementByEmployee(employeeId);
        }
    }

    public List<PledgeAgreementDto> getCurrentPledgeAgreementByEmployee(Long employeeId, TypeOfPledgeAgreement typeOfPledgeAgreement, Pageable pageable){
        if(typeOfPledgeAgreement == TypeOfPledgeAgreement.PERV){
            return tavrzcmsAPIFeignService.getCurrentPervPledgeAgreementByEmployee(employeeId, pageable);
        }else{
            return tavrzcmsAPIFeignService.getCurrentPoslPledgeAgreementByEmployee(employeeId, pageable);
        }
    }

    public Integer countOfMonitoringNotDone(Long employeeId){
        return tavrzcmsAPIFeignService.getCountOfPledgeAgreementsWithMonitoringNotDone(employeeId);
    }

    public List<PledgeAgreementDto> getPledgeAgreementsWithMonitoringNotDone(Long employeeId){
        return tavrzcmsAPIFeignService.getPledgeAgreementsWithMonitoringNotDone(employeeId);
    }

    public Integer countOfMonitoringIsDone(Long employeeId){
        return tavrzcmsAPIFeignService.getCountOfPledgeAgreementsWithMonitoringIsDone(employeeId);
    }

    public List<PledgeAgreementDto> getPledgeAgreementsWithMonitoringIsDone(Long employeeId){
        return tavrzcmsAPIFeignService.getPledgeAgreementsWithMonitoringIsDone(employeeId);
    }

    public Integer countOfMonitoringOverdue(Long employeeId){
        return tavrzcmsAPIFeignService.getCountOfPledgeAgreementsWithMonitoringOverdue(employeeId);
    }

    public List<PledgeAgreementDto> getPledgeAgreementsWithMonitoringOverdue(Long employeeId){
        return tavrzcmsAPIFeignService.getPledgeAgreementsWithMonitoringOverdue(employeeId);
    }

    public Integer countOfConclusionNotDone(Long employeeId){
        return tavrzcmsAPIFeignService.getCountOfPledgeAgreementsWithConclusionNotDone(employeeId);
    }

    public List<PledgeAgreementDto> getPledgeAgreementsWithConclusionNotDone(Long employeeId){
        return tavrzcmsAPIFeignService.getPledgeAgreementsWithConclusionNotDone(employeeId);
    }

    public Integer countOfConclusionIsDone(Long employeeId){
        return tavrzcmsAPIFeignService.getCountOfPledgeAgreementsWithConclusionIsDone(employeeId);
    }

    public List<PledgeAgreementDto> getPledgeAgreementsWithConclusionIsDone(Long employeeId){
        return tavrzcmsAPIFeignService.getPledgeAgreementsWithConclusionIsDone(employeeId);
    }

    public Integer countOfConclusionOverdue(Long employeeId){
        return tavrzcmsAPIFeignService.getCountOfPledgeAgreementsWithConclusionOverdue(employeeId);
    }

    public List<PledgeAgreementDto> getPledgeAgreementsWithConclusionOverdue(Long employeeId){
        return tavrzcmsAPIFeignService.getPledgeAgreementsWithConclusionOverdue(employeeId);
    }

    public Integer countOfAllCurrentPledgeAgreements(){
        return tavrzcmsAPIFeignService.getCountOfCurrentPledgeAgreements();
    }

    public List<PledgeAgreementDto> getCurrentPledgeAgreements(Pageable pageable){
        return tavrzcmsAPIFeignService.getCurrentPledgeAgreements(pageable);
    }

    public Integer countOfAllCurrentPledgeAgreements(TypeOfPledgeAgreement typeOfPledgeAgreement){
        if(typeOfPledgeAgreement == TypeOfPledgeAgreement.PERV){
            return tavrzcmsAPIFeignService.getCountOfCurrentPervPledgeAgreements();
        }else {
            return tavrzcmsAPIFeignService.getCountOfCurrentPoslPledgeAgreements();
        }
    }

    public List<PledgeAgreementDto> getCurrentPledgeAgreements(TypeOfPledgeAgreement typeOfPledgeAgreement, Pageable pageable){
        if(typeOfPledgeAgreement == TypeOfPledgeAgreement.PERV){
            return tavrzcmsAPIFeignService.getCurrentPervPledgeAgreements(pageable);
        }else {
            return tavrzcmsAPIFeignService.getCurrentPoslPledgeAgreements(pageable);
        }
    }




}
