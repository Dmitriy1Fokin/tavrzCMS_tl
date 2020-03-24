package ru.fds.tavrzcms_tl.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fds.tavrzcms_tl.dto.MonitoringDto;
import ru.fds.tavrzcms_tl.service.feign.TavrzcmsAPIFeignService;

import java.util.List;

@Service
public class MonitoringService {

    private final TavrzcmsAPIFeignService tavrzcmsAPIFeignService;

    public MonitoringService(TavrzcmsAPIFeignService tavrzcmsAPIFeignService) {
        this.tavrzcmsAPIFeignService = tavrzcmsAPIFeignService;
    }

    public List<MonitoringDto> getMonitoringByPledgeSubject(Long pledgeSubjectId){
        return tavrzcmsAPIFeignService.getMonitoringsByPledgeSubject(pledgeSubjectId);
    }

    @Transactional
    public void insertMonitoringInClient(MonitoringDto monitoringDto, Long clientId){
        tavrzcmsAPIFeignService.insertMonitoringByClient(monitoringDto, clientId);
    }

    @Transactional
    public void insertMonitoringInPledgeAgreement(MonitoringDto monitoringDto, Long pledgeAgreementId){
        tavrzcmsAPIFeignService.insertMonitoringByPledgeAgreement(monitoringDto, pledgeAgreementId);
    }

}
