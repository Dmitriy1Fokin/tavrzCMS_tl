package ru.fds.tavrzcms_tl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.fds.tavrzcms_tl.dto.InsuranceDto;
import ru.fds.tavrzcms_tl.dto.PledgeSubjectDto;
import ru.fds.tavrzcms_tl.service.InsuranceService;
import ru.fds.tavrzcms_tl.service.PledgeSubjectService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/insurance")
public class InsuranceController {

    private final PledgeSubjectService pledgeSubjectService;
    private final InsuranceService insuranceService;

    private static final String ATTR_PLEDGE_SUBJECT = "pledgeSubject";
    private static final String ATTR_INSURANCE_LIST = "insuranceList";
    private static final String ATTR_INSURANCE = "insuranceDto";
    private static final String PAGE_INSURANCES = "insurance/insurances";
    private static final String PAGE_CARD_INSERT = "insurance/card_insert";
    private static final String PAGE_CARD_UPDATE = "insurance/card_update";

    public InsuranceController(InsuranceService insuranceService,
                               PledgeSubjectService pledgeSubjectService) {
        this.insuranceService = insuranceService;
        this.pledgeSubjectService = pledgeSubjectService;
    }

    @GetMapping("/insurances")
    public String insurancesPage(@RequestParam("pledgeSubjectId") Long pledgeSubjectId,
                                 Model model){

        PledgeSubjectDto pledgeSubjectDto = pledgeSubjectService.getPledgeSubjectById(pledgeSubjectId);
        List<InsuranceDto> insuranceList = insuranceService.getInsurancesByPledgeSubject(pledgeSubjectId);

        model.addAttribute(ATTR_PLEDGE_SUBJECT, pledgeSubjectDto);
        model.addAttribute(ATTR_INSURANCE_LIST, insuranceList);

        return PAGE_INSURANCES;
    }

    @GetMapping("/insert/card")
    public String insuranceNewCard(@RequestParam("pledgeSubjectId") Long pledgeSubjectId,
                                   Model model){

        InsuranceDto insuranceDto = InsuranceDto.builder()
                .pledgeSubjectId(pledgeSubjectId)
                .build();

        model.addAttribute(ATTR_INSURANCE, insuranceDto);

        return PAGE_CARD_INSERT;
    }

    @PostMapping("/insert")
    public String insuranceInsert(@Valid InsuranceDto insuranceDto,
                                  BindingResult bindingResult,
                                  Model model){

        if(bindingResult.hasErrors())
            return PAGE_CARD_INSERT;

        insuranceService.insertInsurance(insuranceDto);

        return insurancesPage(insuranceDto.getPledgeSubjectId(), model);
    }

    @GetMapping("/update/card")
    public String insuranceUpdateCard(@RequestParam("insuranceId") Long insuranceId,
                                      Model model){

        InsuranceDto insuranceDto = insuranceService.getInsuranceById(insuranceId);

        model.addAttribute(ATTR_INSURANCE, insuranceDto);

        return PAGE_CARD_UPDATE;
    }

    @PostMapping("/update")
    public String insuranceUpdate(@Valid InsuranceDto insuranceDto,
                                  BindingResult bindingResult,
                                  Model model){

        if(bindingResult.hasErrors())
            return PAGE_CARD_UPDATE;

        insuranceDto = insuranceService.updateInsurance(insuranceDto);

        return insurancesPage(insuranceDto.getPledgeSubjectId(), model);
    }
}
