package ru.fds.tavrzcms_tl.service;

import org.springframework.stereotype.Service;
import ru.fds.tavrzcms_tl.dto.ClientDto;
import ru.fds.tavrzcms_tl.service.feign.TavrzcmsAPIFeignService;

@Service
public class ClientService {

    private final TavrzcmsAPIFeignService tavrzcmsAPIFeignService;

    public ClientService(TavrzcmsAPIFeignService tavrzcmsAPIFeignService) {
        this.tavrzcmsAPIFeignService = tavrzcmsAPIFeignService;
    }

    public ClientDto getClientById(Long clientId){
        return tavrzcmsAPIFeignService.getClient(clientId);
    }
}
