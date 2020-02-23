package ru.fds.tavrzcms_tl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.fds.tavrzcms_tl.dto.PledgeAgreementDto;
import ru.fds.tavrzcms_tl.service.MonitoringService;
import ru.fds.tavrzcms_tl.service.PledgeAgreementService;

import java.util.List;

@Controller
@RequestMapping("/monitoring")
public class MonitoringController {

    private final MonitoringService monitoringService;
    private final PledgeAgreementService pledgeAgreementService;

    private final String ATTR_PA_MONITORING_NOT_DONE = "pledgeAgreementListWithMonitoringNotDone";
    private final String ATTR_PA_MONITORING_IS_DONE = "pledgeAgreementListWithMonitoringIsDone";
    private final String ATTR_PA_MONITORING_OVERDUE = "pledgeAgreementListWithMonitoringOverdue";
    private final String PAGE_PA = "monitoring/pledge_agreements";

    public MonitoringController(MonitoringService monitoringService,
                                PledgeAgreementService pledgeAgreementService) {
        this.monitoringService = monitoringService;
        this.pledgeAgreementService = pledgeAgreementService;
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
}
