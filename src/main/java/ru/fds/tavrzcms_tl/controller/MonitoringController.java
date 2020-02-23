package ru.fds.tavrzcms_tl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.fds.tavrzcms_tl.dto.MonitoringDto;
import ru.fds.tavrzcms_tl.dto.PledgeAgreementDto;
import ru.fds.tavrzcms_tl.dto.PledgeSubjectDto;
import ru.fds.tavrzcms_tl.service.MonitoringService;
import ru.fds.tavrzcms_tl.service.PledgeAgreementService;
import ru.fds.tavrzcms_tl.service.PledgeSubjectService;

import java.util.List;

@Controller
@RequestMapping("/monitoring")
public class MonitoringController {

    private final MonitoringService monitoringService;
    private final PledgeAgreementService pledgeAgreementService;
    private final PledgeSubjectService pledgeSubjectService;

    private static final String ATTR_PA_MONITORING_NOT_DONE = "pledgeAgreementListWithMonitoringNotDone";
    private static final String ATTR_PA_MONITORING_IS_DONE = "pledgeAgreementListWithMonitoringIsDone";
    private static final String ATTR_PA_MONITORING_OVERDUE = "pledgeAgreementListWithMonitoringOverdue";
    private static final String ATTR_PLEDGE_SUBJECT = "pledgeSubject";
    private static final String ATTR_MONITORING_LIST = "monitoringList";
    private static final String PAGE_PA = "monitoring/pledge_agreements";
    private static final String PAGE_PS = "monitoring/pledge_subject";

    public MonitoringController(MonitoringService monitoringService,
                                PledgeAgreementService pledgeAgreementService,
                                PledgeSubjectService pledgeSubjectService) {
        this.monitoringService = monitoringService;
        this.pledgeAgreementService = pledgeAgreementService;
        this.pledgeSubjectService = pledgeSubjectService;
    }

    @GetMapping("/pledge_agreements")
    public String monitoringPledgeAgreementsPage(@RequestParam("employeeId") long employeeId,
                                                 Model model){

        List<PledgeAgreementDto> pledgeAgreementListWithMonitoringNotDone = pledgeAgreementService.
                getPledgeAgreementsWithMonitoringNotDone(employeeId);
        List<PledgeAgreementDto> pledgeAgreementListWithMonitoringIsDone = pledgeAgreementService.
                getPledgeAgreementsWithMonitoringIsDone(employeeId) ;
        List<PledgeAgreementDto> pledgeAgreementListWithMonitoringOverdue = pledgeAgreementService
                .getPledgeAgreementsWithMonitoringOverdue(employeeId);
        model.addAttribute(ATTR_PA_MONITORING_NOT_DONE, pledgeAgreementListWithMonitoringNotDone);
        model.addAttribute(ATTR_PA_MONITORING_IS_DONE, pledgeAgreementListWithMonitoringIsDone);
        model.addAttribute(ATTR_PA_MONITORING_OVERDUE, pledgeAgreementListWithMonitoringOverdue);

        return PAGE_PA;
    }

    @GetMapping("/pledge_subject")
    public String monitoringPage(@RequestParam("pledgeSubjectId") long pledgeSubjectId,
                                 Model model){

        PledgeSubjectDto pledgeSubjectDto = pledgeSubjectService.getPledgeSubjectById(pledgeSubjectId);
        List<MonitoringDto> monitoringDtoList = monitoringService.getMonitoringByPledgeSubject(pledgeSubjectId);

        model.addAttribute(ATTR_PLEDGE_SUBJECT, pledgeSubjectDto);
        model.addAttribute(ATTR_MONITORING_LIST, monitoringDtoList);

        return PAGE_PS;
    }
}
