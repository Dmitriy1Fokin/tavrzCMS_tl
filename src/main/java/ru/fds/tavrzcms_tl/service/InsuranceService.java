package ru.fds.tavrzcms_tl.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fds.tavrzcms_tl.dto.InsuranceDto;
import ru.fds.tavrzcms_tl.service.feign.TavrzcmsAPIFeignService;

import java.util.List;

@Service
public class InsuranceService {

    private final TavrzcmsAPIFeignService tavrzcmsAPIFeignService;

    public InsuranceService(TavrzcmsAPIFeignService tavrzcmsAPIFeignService) {
        this.tavrzcmsAPIFeignService = tavrzcmsAPIFeignService;
    }

    public InsuranceDto getInsuranceById(Long insuranceId){
        return tavrzcmsAPIFeignService.getInsurance(insuranceId);
    }

    public List<InsuranceDto> getInsurancesByPledgeSubject(Long pledgeSubjectId){
        return tavrzcmsAPIFeignService.getInsurancesByPledgeSubject(pledgeSubjectId);
    }

    @Transactional
    public void insertInsurance(InsuranceDto insuranceDto){
        tavrzcmsAPIFeignService.insertInsurance(insuranceDto);
    }

    @Transactional
    public InsuranceDto updateInsurance(InsuranceDto insuranceDto){
        return tavrzcmsAPIFeignService.updateInsurance(insuranceDto);
    }
}
