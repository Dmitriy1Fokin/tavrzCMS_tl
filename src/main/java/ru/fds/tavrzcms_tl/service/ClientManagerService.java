package ru.fds.tavrzcms_tl.service;

import org.springframework.stereotype.Service;
import ru.fds.tavrzcms_tl.dto.ClientManagerDto;
import ru.fds.tavrzcms_tl.service.feign.TavrzcmsAPIFeignService;

@Service
public class ClientManagerService {

    private final TavrzcmsAPIFeignService tavrzcmsAPIFeignService;

    public ClientManagerService(TavrzcmsAPIFeignService tavrzcmsAPIFeignService) {
        this.tavrzcmsAPIFeignService = tavrzcmsAPIFeignService;
    }

    public ClientManagerDto getClientManagerByClient(Long clientId){
        return tavrzcmsAPIFeignService.getClientManagerByClient(clientId);
    }

    public ClientManagerDto getClientManagerById(Long clientManagerId){
        return tavrzcmsAPIFeignService.getClientManager(clientManagerId);
    }
}
