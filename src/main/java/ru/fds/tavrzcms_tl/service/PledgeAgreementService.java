package ru.fds.tavrzcms_tl.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fds.tavrzcms_tl.dictionary.TypeOfPledgeAgreement;
import ru.fds.tavrzcms_tl.dto.PledgeAgreementDto;
import ru.fds.tavrzcms_tl.service.feign.TavrzcmsAPIFeignService;
import ru.fds.tavrzcms_tl.utils.Utils;
import ru.fds.tavrzcms_tl.wrapper.PledgeAgreementDtoWrapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class PledgeAgreementService {

    private final TavrzcmsAPIFeignService tavrzcmsAPIFeignService;
    private final Utils utils;

    public PledgeAgreementService(TavrzcmsAPIFeignService tavrzcmsAPIFeignService,
                                  Utils utils) {
        this.tavrzcmsAPIFeignService = tavrzcmsAPIFeignService;
        this.utils = utils;
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

    public PledgeAgreementDto getPledgeAgreementById(Long pledgeAgreementId){
        return tavrzcmsAPIFeignService.getPledgeAgreement(pledgeAgreementId);
    }

    @Transactional
    public PledgeAgreementDto updatePledgeAgreement(PledgeAgreementDtoWrapper pledgeAgreementDtoWrapper){
        return tavrzcmsAPIFeignService.updatePledgeAgreement(pledgeAgreementDtoWrapper);
    }

    @Transactional
    public PledgeAgreementDto insertPledgeAgreement(PledgeAgreementDtoWrapper pledgeAgreementDtoWrapper){
        return tavrzcmsAPIFeignService.insertPledgeAgreement(pledgeAgreementDtoWrapper);
    }

    public List<PledgeAgreementDto> getCurrentPledgeAgreementsByClient(Long clientId){
        return tavrzcmsAPIFeignService.getCurrentPledgeAgreementsByClient(clientId);
    }

    public List<PledgeAgreementDto> getClosedPledgeAgreementsByClient(Long clientId){
        return tavrzcmsAPIFeignService.getClosedPledgeAgreementsByClient(clientId);
    }

    public List<PledgeAgreementDto> getCurrentPledgeAgreementsByLoanAgreement(Long loanAgreementId){
        return tavrzcmsAPIFeignService.getCurrentPledgeAgreementsByLoanAgreement(loanAgreementId);
    }

    public List<PledgeAgreementDto> getClosedPledgeAgreementsByLoanAgreement(Long loanAgreementId){
        return tavrzcmsAPIFeignService.getClosedPledgeAgreementsByLoanAgreement(loanAgreementId);
    }

    public List<PledgeAgreementDto> getPledgeAgreementByPledgeSubject(Long pledgeSubjectId){
        return tavrzcmsAPIFeignService.getPledgeAgreementsByPledgeSubjects(pledgeSubjectId);
    }

    public List<PledgeAgreementDto> getPledgeAgreementBySearchCriteria(Map<String, String> searchParam, Pageable pageable){
        if(Objects.nonNull(searchParam.get("pledgorName"))){
            searchParam.putAll(utils.ExtractClientName(Map.of("typeOfClient", searchParam.get("typeOfClient"), "clientName", searchParam.get("pledgorName"))));
        }
        return tavrzcmsAPIFeignService.getPledgeAgreementBySearchCriteria(searchParam, pageable);
    }

    @Transactional
    public PledgeAgreementDto insertCurrentPledgeSubjectInPledgeAgreement(List<Long> pledgeSubjectsIdArray, Long pledgeAgreementId){
        return tavrzcmsAPIFeignService.insertCurrentPledgeSubjectInPledgeAgreement(pledgeSubjectsIdArray, pledgeAgreementId);
    }

}
