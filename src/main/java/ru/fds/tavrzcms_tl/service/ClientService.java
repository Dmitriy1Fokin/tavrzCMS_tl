package ru.fds.tavrzcms_tl.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.fds.tavrzcms_tl.dto.ClientDto;
import ru.fds.tavrzcms_tl.service.feign.TavrzcmsAPIFeignService;

import java.util.List;
import java.util.Map;

@Service
public class ClientService {

    private final TavrzcmsAPIFeignService tavrzcmsAPIFeignService;

    public ClientService(TavrzcmsAPIFeignService tavrzcmsAPIFeignService) {
        this.tavrzcmsAPIFeignService = tavrzcmsAPIFeignService;
    }

    public ClientDto getClientById(Long clientId){
        return tavrzcmsAPIFeignService.getClient(clientId);
    }

    public List<ClientDto> getClientBySearchCriteria(Map<String, String> searchParam){
        return tavrzcmsAPIFeignService.getClientBySearchCriteria(searchParam);
    }

    public List<ClientDto> getClientByClientManager(Long clientManagerId){
        return tavrzcmsAPIFeignService.getClientsByClientManager(clientManagerId);
    }

    @Transactional
    public ClientDto updateClient(ClientDto clientDto){
        return tavrzcmsAPIFeignService.updateClient(clientDto);
    }

    @Transactional
    public  ClientDto insertClient(ClientDto clientDto){
        return tavrzcmsAPIFeignService.insertClient(clientDto);
    }

    @Transactional
    public List<ClientDto> insertClientLegalEntityFromFile(MultipartFile file){
        return tavrzcmsAPIFeignService.insertClientLegalEntityFromFile(file);
    }

    @Transactional
    public List<ClientDto> insertClientIndividualFromFile(MultipartFile file){
        return tavrzcmsAPIFeignService.insertClientIndividualFromFile(file);
    }

}
