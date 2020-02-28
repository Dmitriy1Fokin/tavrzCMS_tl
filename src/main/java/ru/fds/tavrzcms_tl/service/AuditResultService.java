package ru.fds.tavrzcms_tl.service;

import org.springframework.stereotype.Service;
import ru.fds.tavrzcms_tl.dto.AuditResultDto;
import ru.fds.tavrzcms_tl.service.feign.TavrzcmsAPIFeignService;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuditResultService {

    private final TavrzcmsAPIFeignService tavrzcmsAPIFeignService;

    public AuditResultService(TavrzcmsAPIFeignService tavrzcmsAPIFeignService) {
        this.tavrzcmsAPIFeignService = tavrzcmsAPIFeignService;
    }

    public List<AuditResultDto> getActualAuditResultAboutPledgeSubject(Long pledgeSubjectId){
        return new ArrayList<>(tavrzcmsAPIFeignService.getActualAuditResultAboutPledgeSubject(pledgeSubjectId));
    }

    public void setIgnore(String auditResultId){
        tavrzcmsAPIFeignService.setIgnore(auditResultId);
    }
}
