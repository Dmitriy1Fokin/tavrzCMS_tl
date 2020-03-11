package ru.fds.tavrzcms_tl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.fds.tavrzcms_tl.dictionary.TypeOfClient;
import ru.fds.tavrzcms_tl.dto.ClientDto;
import ru.fds.tavrzcms_tl.dto.ClientIndividualDto;
import ru.fds.tavrzcms_tl.dto.ClientLegalEntityDto;
import ru.fds.tavrzcms_tl.dto.ClientManagerDto;
import ru.fds.tavrzcms_tl.dto.EmployeeDto;
import ru.fds.tavrzcms_tl.dto.LoanAgreementDto;
import ru.fds.tavrzcms_tl.dto.MonitoringDto;
import ru.fds.tavrzcms_tl.dto.PledgeAgreementDto;
import ru.fds.tavrzcms_tl.service.ClientManagerService;
import ru.fds.tavrzcms_tl.service.ClientService;
import ru.fds.tavrzcms_tl.service.EmployeeService;
import ru.fds.tavrzcms_tl.service.LoanAgreementService;
import ru.fds.tavrzcms_tl.service.MonitoringService;
import ru.fds.tavrzcms_tl.service.PledgeAgreementService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;
    private final ClientManagerService clientManagerService;
    private final EmployeeService employeeService;
    private final PledgeAgreementService pledgeAgreementService;
    private final LoanAgreementService loanAgreementService;
    private final MonitoringService monitoringService;

    private static final String ATTR_CLIENT = "clientDto";
    private static final String ATTR_CLIENT_MANAGER = "clientManagerDto";
    private static final String ATTR_EMPLOYEE = "employeeDto";
    private static final String ATTR_PLEDGE_AGREEMENT_CURRENT_LIST = "pledgeAgreementCurrentList";
    private static final String ATTR_PLEDGE_AGREEMENT_CLOSED_LIST = "pledgeAgreementClosedList";
    private static final String ATTR_LOAN_AGREEMENT_CURRENT_LIST = "loanAgreementCurrentList";
    private static final String ATTR_LOAN_AGREEMENT_CLOSED_LIST = "loanAgreementClosedList";
    private static final String ATTR_CLIENT_MANAGER_LIST = "clientManagerDtoList";
    private static final String ATTR_EMPLOYEE_LIST = "employeeDtoList";
    private static final String ATTR_WHAT_DO = "whatDo";
    private static final String ATTR_MONITORING = "monitoringDto";
    private static final String PAGE_CARD_UPDATE = "client/card_update";
    private static final String PAGE_CARD_INSERT = "client/card_insert";
    private static final String PAGE_DETAIL = "client/detail";
    private static final String PAGE_MONITORING_CARD = "monitoring/card_insert_client";


    public ClientController(ClientService clientService,
                            ClientManagerService clientManagerService,
                            EmployeeService employeeService,
                            PledgeAgreementService pledgeAgreementService,
                            LoanAgreementService loanAgreementService,
                            MonitoringService monitoringService) {
        this.clientService = clientService;
        this.clientManagerService = clientManagerService;
        this.employeeService = employeeService;
        this.pledgeAgreementService = pledgeAgreementService;
        this.loanAgreementService = loanAgreementService;
        this.monitoringService = monitoringService;
    }

    @GetMapping("/detail")
    public String clientDetailPage(@RequestParam("clientId") long clientId,
                                   Model model){

        ClientDto clientDto = clientService.getClientById(clientId);
        ClientManagerDto clientManagerDto = clientManagerService.getClientManagerById(clientDto.getClientManagerId());
        EmployeeDto employeeDto = employeeService.getEmployeeById(clientDto.getEmployeeId());
        List<PledgeAgreementDto> pledgeAgreementCurrentDtoList = pledgeAgreementService.getCurrentPledgeAgreementsByClient(clientId);
        List<PledgeAgreementDto> pledgeAgreementClosedDtoList = pledgeAgreementService.getClosedPledgeAgreementsByClient(clientId);
        List<LoanAgreementDto> loanAgreementCurrentDtoList = loanAgreementService.getCurrentLoanAgreementsByClient(clientId);
        List<LoanAgreementDto> loanAgreementClosedDtoList = loanAgreementService.getClosedLoanAgreementsByClient(clientId);

        model.addAttribute(ATTR_CLIENT, clientDto);
        model.addAttribute(ATTR_CLIENT_MANAGER, clientManagerDto);
        model.addAttribute(ATTR_EMPLOYEE, employeeDto);
        model.addAttribute(ATTR_PLEDGE_AGREEMENT_CURRENT_LIST, pledgeAgreementCurrentDtoList);
        model.addAttribute(ATTR_PLEDGE_AGREEMENT_CLOSED_LIST, pledgeAgreementClosedDtoList);
        model.addAttribute(ATTR_LOAN_AGREEMENT_CURRENT_LIST, loanAgreementCurrentDtoList);
        model.addAttribute(ATTR_LOAN_AGREEMENT_CLOSED_LIST, loanAgreementClosedDtoList);

        return PAGE_DETAIL;
    }

    @GetMapping("/update/card")
    public String clientCardUpdatePage(@RequestParam("clientId") Long clientId,
                                       Model model){
        ClientDto clientDto = clientService.getClientById(clientId);
        List<ClientManagerDto> clientManagerDtoList = clientManagerService.getAllClientManagers();
        List<EmployeeDto> employeeDtoList = employeeService.getAllEmployees();

        model.addAttribute(ATTR_CLIENT, clientDto);
        model.addAttribute(ATTR_CLIENT_MANAGER_LIST, clientManagerDtoList);
        model.addAttribute(ATTR_EMPLOYEE_LIST, employeeDtoList);

        return PAGE_CARD_UPDATE;
    }

    @GetMapping("/insert/card")
    public String clientCardInsertPage(@RequestParam("typeOfClient") String typeOfClient,
                                       @RequestParam("whatDo") String whatDo,
                                       Model model){

        List<ClientManagerDto> clientManagerDtoList = clientManagerService.getAllClientManagers();
        List<EmployeeDto> employeeDtoList = employeeService.getAllEmployees();

        ClientDto clientDto = new ClientDto();
        if(typeOfClient.equals(TypeOfClient.LEGAL_ENTITY.name())){
            clientDto.setTypeOfClient(TypeOfClient.LEGAL_ENTITY);
            clientDto.setClientLegalEntityDto(new ClientLegalEntityDto());

        }else {
            clientDto.setTypeOfClient(TypeOfClient.INDIVIDUAL);
            clientDto.setClientIndividualDto(new ClientIndividualDto());

        }

        model.addAttribute(ATTR_CLIENT, clientDto);
        model.addAttribute(ATTR_CLIENT_MANAGER_LIST, clientManagerDtoList);
        model.addAttribute(ATTR_EMPLOYEE_LIST, employeeDtoList);
        model.addAttribute(ATTR_WHAT_DO, whatDo);

        return PAGE_CARD_INSERT;
    }

    @PostMapping("/update")
    public String updateInsertClient(@Valid ClientDto clientDto,
                                     BindingResult bindingResult,
                                     Model model){
        if(bindingResult.hasErrors()){
            List<ClientManagerDto> clientManagerDtoList = clientManagerService.getAllClientManagers();
            List<EmployeeDto> employeeDtoList = employeeService.getAllEmployees();
            model.addAttribute(ATTR_CLIENT_MANAGER_LIST, clientManagerDtoList);
            model.addAttribute(ATTR_EMPLOYEE_LIST, employeeDtoList);
            return PAGE_CARD_UPDATE;
        }

        clientDto = clientService.updateInsertClient(clientDto);

        return clientDetailPage(clientDto.getClientId(), model);
    }

    @GetMapping("/monitoring/insert/card")
    public String monitoringByClientCardPage(@RequestParam("clientId") Long clientId,
                                             Model model){

        ClientDto clientDto = clientService.getClientById(clientId);
        MonitoringDto monitoringDto = new MonitoringDto();

        model.addAttribute(ATTR_CLIENT, clientDto);
        model.addAttribute(ATTR_MONITORING, monitoringDto);


        return PAGE_MONITORING_CARD;
    }

    @PostMapping("/monitoring/insert")
    public String insertMonitoringByClient(@Valid MonitoringDto monitoringDto,
                                           BindingResult bindingResult,
                                           @RequestParam("clientId") Long clientId,
                                           Model model){
        if(bindingResult.hasErrors()){
            return PAGE_MONITORING_CARD;
        }

        monitoringService.insertMonitoringInClient(monitoringDto, clientId);

        return clientDetailPage(clientId, model);
    }
}
