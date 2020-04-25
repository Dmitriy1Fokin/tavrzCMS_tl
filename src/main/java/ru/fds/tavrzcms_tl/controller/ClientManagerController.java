package ru.fds.tavrzcms_tl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.fds.tavrzcms_tl.dto.ClientDto;
import ru.fds.tavrzcms_tl.dto.ClientManagerDto;
import ru.fds.tavrzcms_tl.service.ClientManagerService;
import ru.fds.tavrzcms_tl.service.ClientService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/client_manager")
public class ClientManagerController {

    private final ClientManagerService clientManagerService;
    private final ClientService clientService;

    private static final String ATTR_CLIENT_MANAGER = "clientManagerDto";
    private static final String ATTR_CLIENT_LIST = "clientDtoList";
    private static final String PAGE_DETAIL = "client_manager/detail";
    private static final String PAGE_CARD_UPDATE = "client_manager/card_update";
    private static final String PAGE_CARD_INSERT = "client_manager/card_insert";

    public ClientManagerController(ClientManagerService clientManagerService,
                                   ClientService clientService) {
        this.clientManagerService = clientManagerService;
        this.clientService = clientService;
    }

    @GetMapping("/detail")
    public String clientManagerDetailPage(@RequestParam("clientManagerId") Long clientManagerId,
                                          Model model){
        ClientManagerDto clientManagerDto = clientManagerService.getClientManagerById(clientManagerId);
        List<ClientDto> clientDtoList = clientService.getClientByClientManager(clientManagerId);

        model.addAttribute(ATTR_CLIENT_MANAGER, clientManagerDto);
        model.addAttribute(ATTR_CLIENT_LIST, clientDtoList);

        return PAGE_DETAIL;
    }

    @GetMapping("/update/card")
    public String clientManagerUpdatePage(@RequestParam("clientManagerId") Long clientManagerId,
                                          Model model){

        ClientManagerDto clientManagerDto = clientManagerService.getClientManagerById(clientManagerId);

        model.addAttribute(clientManagerDto);

        return PAGE_CARD_UPDATE;
    }

    @PostMapping("/update")
    public String updateClientManager(@Valid ClientManagerDto clientManagerDto,
                                      BindingResult bindingResult,
                                      Model model){
        if(bindingResult.hasErrors()){
            return PAGE_CARD_UPDATE;
        }

        clientManagerDto = clientManagerService.updateClientManager(clientManagerDto);

        return clientManagerDetailPage(clientManagerDto.getClientManagerId(), model);
    }

    @GetMapping("/insert/card")
    public String clientManagerInsertPage(Model model){
        ClientManagerDto clientManagerDto = new ClientManagerDto();

        model.addAttribute(clientManagerDto);

        return PAGE_CARD_INSERT;
    }

    @PostMapping("/insert")
    public String insertClientManager(@Valid ClientManagerDto clientManagerDto,
                                      BindingResult bindingResult,
                                      Model model){
        if(bindingResult.hasErrors()){
            return PAGE_CARD_INSERT;
        }

        clientManagerDto = clientManagerService.insertClientManager(clientManagerDto);

        return clientManagerDetailPage(clientManagerDto.getClientManagerId(), model);
    }
}
