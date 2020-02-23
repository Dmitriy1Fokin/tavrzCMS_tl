package ru.fds.tavrzcms_tl.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.fds.tavrzcms_tl.dto.LoanAgreementDto;
import ru.fds.tavrzcms_tl.service.LoanAgreementService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/loan_agreement")
public class LoanAgreementController {

    private final LoanAgreementService loanAgreementService;

    private final String ATTR_LOAN_AGREEMENT_LIST = "loanAgreementList";
    private final String ATTR_PAGE = "page";
    private final String ATTR_EMPLOYEE_ID = "employeeId";
    private final String PAGE_LOAN_AGREEMENTS = "loan_agreement/loan_agreements";

    public LoanAgreementController(LoanAgreementService loanAgreementService) {
        this.loanAgreementService = loanAgreementService;
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


}
