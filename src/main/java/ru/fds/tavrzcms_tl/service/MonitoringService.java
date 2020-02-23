package ru.fds.tavrzcms_tl.service;

import org.springframework.stereotype.Service;
import ru.fds.tavrzcms_tl.service.feign.TavrzcmsAPIFeignService;

@Service
public class MonitoringService {

    private final TavrzcmsAPIFeignService tavrzcmsAPIFeignService;

    public MonitoringService(TavrzcmsAPIFeignService tavrzcmsAPIFeignService) {
        this.tavrzcmsAPIFeignService = tavrzcmsAPIFeignService;
    }


}
