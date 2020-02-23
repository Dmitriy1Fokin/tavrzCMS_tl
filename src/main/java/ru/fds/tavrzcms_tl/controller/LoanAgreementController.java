package ru.fds.tavrzcms_tl.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.fds.tavrzcms_tl.dto.EmployeeDto;
import ru.fds.tavrzcms_tl.dto.LoanAgreementDto;
import ru.fds.tavrzcms_tl.dto.PledgeAgreementDto;
import ru.fds.tavrzcms_tl.service.EmployeeService;
import ru.fds.tavrzcms_tl.service.LoanAgreementService;
import ru.fds.tavrzcms_tl.service.PledgeAgreementService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/loan_agreement")
public class LoanAgreementController {

    private final LoanAgreementService loanAgreementService;
    private final EmployeeService employeeService;
    private final PledgeAgreementService pledgeAgreementService;

    private static final String ATTR_LOAN_AGREEMENT_LIST = "loanAgreementList";
    private static final String ATTR_PAGE = "page";
    private static final String ATTR_EMPLOYEE_ID = "employeeId";
    private static final String ATTR_LOAN_AGREEMENT = "loanAgreementDto";
    private static final String ATTR_EMPLOYEE = "employeeDto";
    private static final String ATTR_CURRENT_PLEDGE_AGREEMENT_LIST = "currentPledgeAgreementDtoList";
    private static final String ATTR_CLOSED_PLEDGE_AGREEMENT_LIST = "closedPledgeAgreementDtoList";
    private static final String PAGE_LOAN_AGREEMENTS = "loan_agreement/loan_agreements";
    private static final String PAGE_DETAIL = "loan_agreement/detail";

    public LoanAgreementController(LoanAgreementService loanAgreementService,
                                   EmployeeService employeeService,
                                   PledgeAgreementService pledgeAgreementService) {
        this.loanAgreementService = loanAgreementService;
        this.employeeService = employeeService;
        this.pledgeAgreementService = pledgeAgreementService;
    }

    @GetMapping("/loan_agreements_emp")
    public String loanAgreementsPageForEmployee(@RequestParam("employeeId")Long employeeId,
                                                @RequestParam("page") Optional<Integer> page,
                                                @RequestParam("size") Optional<Integer> size,
                                                Model model) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(50));

        List<LoanAgreementDto> loanAgreementDtoList = loanAgreementService.getCurrentLoanAgreementByEmployee(employeeId, pageable);

        model.addAttribute(ATTR_LOAN_AGREEMENT_LIST, loanAgreementDtoList);
        model.addAttribute(ATTR_EMPLOYEE_ID, employeeId);
        model.addAttribute(ATTR_PAGE, page.orElse(0));

        return PAGE_LOAN_AGREEMENTS;
    }

    @GetMapping("/loan_agreements_guest")
    public String loanAgreementsPageFoeGuest(@RequestParam("page") Optional<Integer> page,
                                             @RequestParam("size") Optional<Integer> size,
                                             Model model) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(50));

        List<LoanAgreementDto> loanAgreementDtoList = loanAgreementService.getLoanAgreements(pageable);

        model.addAttribute(ATTR_LOAN_AGREEMENT_LIST, loanAgreementDtoList);
        model.addAttribute(ATTR_PAGE, page.orElse(0));

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

}
