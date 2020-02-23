package ru.fds.tavrzcms_tl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.fds.tavrzcms_tl.dto.CostHistoryDto;
import ru.fds.tavrzcms_tl.dto.PledgeAgreementDto;
import ru.fds.tavrzcms_tl.dto.PledgeSubjectDto;
import ru.fds.tavrzcms_tl.service.CostHistoryService;
import ru.fds.tavrzcms_tl.service.PledgeAgreementService;
import ru.fds.tavrzcms_tl.service.PledgeSubjectService;

import java.util.List;

@Controller
@RequestMapping("/cost_history")
public class CostHistoryController {

    private final PledgeAgreementService pledgeAgreementService;
    private final PledgeSubjectService pledgeSubjectService;
    private final CostHistoryService costHistoryService;

    private static final String ATTR_PA_CONCLUSION_NOT_DONE = "pledgeAgreementListWithConclusionNotDone";
    private static final String ATTR_PA_CONCLUSION_IS_DONE = "pledgeAgreementListWithConclusionIsDone";
    private static final String ATTR_PA_CONCLUSION_OVERDUE = "pledgeAgreementListWithConclusionOverdue";
    private static final String ATTR_PLEDGE_SUBJECT = "pledgeSubject";
    private static final String ATTR_COST_HISTORY_LIST = "costHistoryList";
    private static final String PAGE_PA = "cost_history/pledge_agreements";
    private static final String PAGE_PS = "cost_history/pledge_subject";

    public CostHistoryController(PledgeAgreementService pledgeAgreementService,
                                 PledgeSubjectService pledgeSubjectService,
                                 CostHistoryService costHistoryService) {
        this.pledgeAgreementService = pledgeAgreementService;
        this.pledgeSubjectService = pledgeSubjectService;
        this.costHistoryService = costHistoryService;
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

    @GetMapping("/pledge_subject")
    public String costHistoryPage(@RequestParam("pledgeSubjectId") long pledgeSubjectId,
                                  Model model){

        PledgeSubjectDto pledgeSubjectDto = pledgeSubjectService.getPledgeSubjectById(pledgeSubjectId);
        List<CostHistoryDto> costHistoryList = costHistoryService.getCostHistoryByPledgeSubject(pledgeSubjectId);

        model.addAttribute(ATTR_PLEDGE_SUBJECT, pledgeSubjectDto);
        model.addAttribute(ATTR_COST_HISTORY_LIST, costHistoryList);

        return PAGE_PS;
    }

}
