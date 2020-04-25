package ru.fds.tavrzcms_tl.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.fds.tavrzcms_tl.dto.ClientManagerDto;
import ru.fds.tavrzcms_tl.service.feign.TavrzcmsAPIFeignService;

import java.util.List;

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

    public List<ClientManagerDto> getAllClientManagers(){
        return tavrzcmsAPIFeignService.getAllClientManagers();
    }

    @Transactional
    public List<ClientManagerDto> insertClientManagerFromFile(MultipartFile file){
        return tavrzcmsAPIFeignService.insertClientManagerFromFile(file);
    }

    @Transactional
    public ClientManagerDto updateClientManager(ClientManagerDto clientManagerDto){
        return tavrzcmsAPIFeignService.updateClientManager(clientManagerDto);
    }

    @Transactional
    public ClientManagerDto insertClientManager(ClientManagerDto clientManagerDto){
        return tavrzcmsAPIFeignService.insertClientManager(clientManagerDto);
    }
}
