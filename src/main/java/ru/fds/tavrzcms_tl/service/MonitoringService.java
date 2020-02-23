package ru.fds.tavrzcms_tl.service;

import org.springframework.stereotype.Service;
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

}
