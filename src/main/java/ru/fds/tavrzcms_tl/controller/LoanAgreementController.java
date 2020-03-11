package ru.fds.tavrzcms_tl.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.fds.tavrzcms_tl.dto.EmployeeDto;
import ru.fds.tavrzcms_tl.dto.LoanAgreementDto;
import ru.fds.tavrzcms_tl.dto.PledgeAgreementDto;
import ru.fds.tavrzcms_tl.service.EmployeeService;
import ru.fds.tavrzcms_tl.service.LoanAgreementService;
import ru.fds.tavrzcms_tl.service.PledgeAgreementService;
import ru.fds.tavrzcms_tl.wrapper.PledgeAgreementDtoWrapper;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/loan_agreement")
public class LoanAgreementController {

    private final LoanAgreementService loanAgreementService;
    private final EmployeeService employeeService;
    private final PledgeAgreementService pledgeAgreementService;

    @Value("${page_size}")
    private Integer pageSize;
    private static final String ATTR_LOAN_AGREEMENT_LIST = "loanAgreementList";
    private static final String ATTR_PAGE = "page";
    private static final String ATTR_EMPLOYEE_ID = "employeeId";
    private static final String ATTR_LOAN_AGREEMENT = "loanAgreementDto";
    private static final String ATTR_EMPLOYEE = "employeeDto";
    private static final String ATTR_CURRENT_PLEDGE_AGREEMENT_LIST = "currentPledgeAgreementDtoList";
    private static final String ATTR_CLOSED_PLEDGE_AGREEMENT_LIST = "closedPledgeAgreementDtoList";
    private static final String PAGE_LOAN_AGREEMENTS = "loan_agreement/loan_agreements";
    private static final String PAGE_DETAIL = "loan_agreement/detail";
    private static final String PAGE_CARD_UPDATE = "loan_agreement/card_update";
    private static final String PAGE_CARD_INSERT = "loan_agreement/card_insert";

    public LoanAgreementController(LoanAgreementService loanAgreementService,
                                   EmployeeService employeeService,
                                   PledgeAgreementService pledgeAgreementService) {
        this.loanAgreementService = loanAgreementService;
        this.employeeService = employeeService;
        this.pledgeAgreementService = pledgeAgreementService;
    }

    @GetMapping("/loan_agreements/emp")
    public String loanAgreementsPageForEmployee(@RequestParam("employeeId")Long employeeId,
                                                @RequestParam("page") Integer page,
                                                Model model) {
        Pageable pageable = PageRequest.of(page, pageSize);

        List<LoanAgreementDto> loanAgreementDtoList = loanAgreementService.getCurrentLoanAgreementByEmployee(employeeId, pageable);

        model.addAttribute(ATTR_LOAN_AGREEMENT_LIST, loanAgreementDtoList);
        model.addAttribute(ATTR_EMPLOYEE_ID, employeeId);
        model.addAttribute(ATTR_PAGE, page);

        return PAGE_LOAN_AGREEMENTS;
    }

    @GetMapping("/loan_agreements/guest")
    public String loanAgreementsPageFoeGuest(@RequestParam("page") Integer page,
                                             Model model) {
        Pageable pageable = PageRequest.of(page, pageSize);

        List<LoanAgreementDto> loanAgreementDtoList = loanAgreementService.getLoanAgreements(pageable);

        model.addAttribute(ATTR_LOAN_AGREEMENT_LIST, loanAgreementDtoList);
        model.addAttribute(ATTR_PAGE, page);

        return PAGE_LOAN_AGREEMENTS;
    }

    @GetMapping("/detail")
    public String loanAgreementDetailPage(@RequestParam("loanAgreementId") long loanAgreementId,
                                          Model model){

        LoanAgreementDto loanAgreementDto = loanAgreementService.getLoanAgreementById(loanAgreementId);
        EmployeeDto employeeDto = employeeService.getEmployeeByLoanAgreement(loanAgreementId);
        List<PledgeAgreementDto> currentPledgeAgreementDtoList = pledgeAgreementService
                .getCurrentPledgeAgreementsByLoanAgreement(loanAgreementId);
        List<PledgeAgreementDto> closedPledgeAgreementDtoList = pledgeAgreementService
                .getClosedPledgeAgreementsByLoanAgreement(loanAgreementId);

        model.addAttribute(ATTR_LOAN_AGREEMENT, loanAgreementDto);
        model.addAttribute(ATTR_EMPLOYEE, employeeDto);
        model.addAttribute(ATTR_CURRENT_PLEDGE_AGREEMENT_LIST, currentPledgeAgreementDtoList);
        model.addAttribute(ATTR_CLOSED_PLEDGE_AGREEMENT_LIST, closedPledgeAgreementDtoList);

        return PAGE_DETAIL;
    }

    @GetMapping("/update/card")
    public String loanAgreementCardUpdatePage(@RequestParam("loanAgreementId") Long loanAgreementId,
                                              Model model){

        LoanAgreementDto loanAgreementDto = loanAgreementService.getLoanAgreementById(loanAgreementId);

        model.addAttribute(ATTR_LOAN_AGREEMENT, loanAgreementDto);

        return PAGE_CARD_UPDATE;

    }

    @GetMapping("/insert/card")
    public String loanAgreementCardInsertPage(@RequestParam("clientId") Long clientId,
                                              Model model){

        LoanAgreementDto loanAgreementDto = LoanAgreementDto.builder()
                .clientId(clientId)
                .build();

        model.addAttribute(ATTR_LOAN_AGREEMENT, loanAgreementDto);

        return PAGE_CARD_INSERT;

    }

    @PutMapping("/update")
    public String updateLoanAgreement(@Valid LoanAgreementDto loanAgreementDto,
                                      BindingResult bindingResult,
                                      Model model){

        if(bindingResult.hasErrors()){
            return PAGE_CARD_UPDATE;
        }

        loanAgreementDto = loanAgreementService.updateLoanAgreement(loanAgreementDto);

        return loanAgreementDetailPage(loanAgreementDto.getLoanAgreementId(), model);
    }

    @PostMapping("/insert")
    public String insertLoanAgreement(@Valid LoanAgreementDto loanAgreementDto,
                                        BindingResult bindingResult,
                                        Model model){

        if(bindingResult.hasErrors()){
            return PAGE_CARD_INSERT;
        }

        loanAgreementDto = loanAgreementService.inseertLoanAgreement(loanAgreementDto);

        return loanAgreementDetailPage(loanAgreementDto.getLoanAgreementId(), model);
    }

}
