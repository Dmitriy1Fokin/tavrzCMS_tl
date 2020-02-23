package ru.fds.tavrzcms_tl.service;

import org.springframework.stereotype.Service;
import ru.fds.tavrzcms_tl.dto.PledgeSubjectDto;
import ru.fds.tavrzcms_tl.service.feign.TavrzcmsAPIFeignService;

import java.util.List;

@Service
public class PledgeSubjectService {

    private final TavrzcmsAPIFeignService tavrzcmsAPIFeignService;

    public PledgeSubjectService(TavrzcmsAPIFeignService tavrzcmsAPIFeignService) {
        this.tavrzcmsAPIFeignService = tavrzcmsAPIFeignService;
    }

    public List<PledgeSubjectDto> getPledgeSubjectsByPledgeAgreement(Long pledgeAgreementId){
        return tavrzcmsAPIFeignService.getPledgeSubjectByPledgeAgreement(pledgeAgreementId);
    }

    public PledgeSubjectDto getPledgeSubjectById(Long pledgeSubjectId){
        return tavrzcmsAPIFeignService.getPledgeSubject(pledgeSubjectId);
    }
}
