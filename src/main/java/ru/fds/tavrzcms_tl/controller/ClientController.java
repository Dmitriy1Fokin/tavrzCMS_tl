package ru.fds.tavrzcms_tl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.fds.tavrzcms_tl.dto.ClientDto;
import ru.fds.tavrzcms_tl.dto.ClientManagerDto;
import ru.fds.tavrzcms_tl.dto.EmployeeDto;
import ru.fds.tavrzcms_tl.dto.LoanAgreementDto;
import ru.fds.tavrzcms_tl.dto.PledgeAgreementDto;
import ru.fds.tavrzcms_tl.service.ClientManagerService;
import ru.fds.tavrzcms_tl.service.ClientService;
import ru.fds.tavrzcms_tl.service.EmployeeService;
import ru.fds.tavrzcms_tl.service.LoanAgreementService;
import ru.fds.tavrzcms_tl.service.PledgeAgreementService;

import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;
    private final ClientManagerService clientManagerService;
    private final EmployeeService employeeService;
    private final PledgeAgreementService pledgeAgreementService;
    private final LoanAgreementService loanAgreementService;

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
    private static final String PAGE_CARD = "client/card";
    private static final String PAGE_DETAIL = "client/detail";
    private static final String MSG_WRONG_LINK = "Неверная ссылка";

    public ClientController(ClientService clientService,
                            ClientManagerService clientManagerService,
                            EmployeeService employeeService,
                            PledgeAgreementService pledgeAgreementService,
                            LoanAgreementService loanAgreementService) {
        this.clientService = clientService;
        this.clientManagerService = clientManagerService;
        this.employeeService = employeeService;
        this.pledgeAgreementService = pledgeAgreementService;
        this.loanAgreementService = loanAgreementService;
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
}
