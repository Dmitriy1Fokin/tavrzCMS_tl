package ru.fds.tavrzcms_tl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.fds.tavrzcms_tl.dto.PledgeAgreementDto;
import ru.fds.tavrzcms_tl.service.PledgeAgreementService;

import java.util.List;

@Controller
@RequestMapping("/cost_history")
public class CostHistoryController {

    private final PledgeAgreementService pledgeAgreementService;

    private final String ATTR_PA_CONCLUSION_NOT_DONE = "pledgeAgreementListWithConclusionNotDone";
    private final String ATTR_PA_CONCLUSION_IS_DONE = "pledgeAgreementListWithConclusionIsDone";
    private final String ATTR_PA_CONCLUSION_OVERDUE = "pledgeAgreementListWithConclusionOverdue";
    private final String PAGE_PA = "cost_history/pledge_agreements";

    public CostHistoryController(PledgeAgreementService pledgeAgreementService) {
        this.pledgeAgreementService = pledgeAgreementService;
    }

    @GetMapping("/pledge_agreements")
    public String conclusionsPage(@RequestParam("employeeId") long employeeId,
                                  Model model){

        List<PledgeAgreementDto> pledgeAgreementListWithConclusionNotDone = pledgeAgreementService
                .getPledgeAgreementsWithConclusionNotDone(employeeId);
        List<PledgeAgreementDto> pledgeAgreementListWithConclusionIsDone =  pledgeAgreementService
                .getPledgeAgreementsWithConclusionIsDone(employeeId);
        List<PledgeAgreementDto> pledgeAgreementListWithConclusionOverdue = pledgeAgreementService
                .getPledgeAgreementsWithConclusionOverdue(employeeId);

        model.addAttribute(ATTR_PA_CONCLUSION_NOT_DONE, pledgeAgreementListWithConclusionNotDone);
        model.addAttribute(ATTR_PA_CONCLUSION_IS_DONE, pledgeAgreementListWithConclusionIsDone);
        model.addAttribute(ATTR_PA_CONCLUSION_OVERDUE, pledgeAgreementListWithConclusionOverdue);

        return PAGE_PA;
    }
}
