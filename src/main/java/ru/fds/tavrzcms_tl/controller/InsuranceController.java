package ru.fds.tavrzcms_tl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.fds.tavrzcms_tl.dto.InsuranceDto;
import ru.fds.tavrzcms_tl.dto.PledgeSubjectDto;
import ru.fds.tavrzcms_tl.service.InsuranceService;
import ru.fds.tavrzcms_tl.service.PledgeSubjectService;

import java.util.List;

@Controller
@RequestMapping("/insurance")
public class InsuranceController {

    private final PledgeSubjectService pledgeSubjectService;
    private final InsuranceService insuranceService;

    private static final String ATTR_PLEDGE_SUBJECT = "pledgeSubject";
    private static final String ATTR_INSURANCE_LIST = "insuranceList";
    private static final String PAGE_INSURANCES = "insurance/insurances";

    public InsuranceController(InsuranceService insuranceService,
                               PledgeSubjectService pledgeSubjectService) {
        this.insuranceService = insuranceService;
        this.pledgeSubjectService = pledgeSubjectService;
    }

    @GetMapping("/insurances")
    public String insurancesPage(@RequestParam("pledgeSubjectId") long pledgeSubjectId,
                                 Model model){

        PledgeSubjectDto pledgeSubjectDto = pledgeSubjectService.getPledgeSubjectById(pledgeSubjectId);
        List<InsuranceDto> insuranceList = insuranceService.getInsurancesByPledgeSubject(pledgeSubjectId);

        model.addAttribute(ATTR_PLEDGE_SUBJECT, pledgeSubjectDto);
        model.addAttribute(ATTR_INSURANCE_LIST, insuranceList);

        return PAGE_INSURANCES;
    }
}
