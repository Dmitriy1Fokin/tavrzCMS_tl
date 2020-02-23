package ru.fds.tavrzcms_tl.service;

import org.springframework.stereotype.Service;
import ru.fds.tavrzcms_tl.dto.InsuranceDto;
import ru.fds.tavrzcms_tl.service.feign.TavrzcmsAPIFeignService;

import java.util.List;

@Service
public class InsuranceService {

    private final TavrzcmsAPIFeignService tavrzcmsAPIFeignService;

    public InsuranceService(TavrzcmsAPIFeignService tavrzcmsAPIFeignService) {
        this.tavrzcmsAPIFeignService = tavrzcmsAPIFeignService;
    }

    public List<InsuranceDto> getInsurancesByPledgeSubject(Long pledgeSubjectId){
        return tavrzcmsAPIFeignService.getInsurancesByPledgeSubject(pledgeSubjectId);
    }
}
