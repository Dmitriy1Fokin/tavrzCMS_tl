package ru.fds.tavrzcms_tl.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.fds.tavrzcms_tl.dto.LoanAgreementDto;
import ru.fds.tavrzcms_tl.dto.PledgeAgreementDto;
import ru.fds.tavrzcms_tl.dto.PledgeSubjectDto;
import ru.fds.tavrzcms_tl.service.LoanAgreementService;
import ru.fds.tavrzcms_tl.service.PledgeAgreementService;
import ru.fds.tavrzcms_tl.service.PledgeSubjectService;
import ru.fds.tavrzcms_tl.wrapper.PledgeSubjectUpdateDtoWrapper;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/pledge_subject")
public class PledgeSubjectController {

    private final PledgeSubjectService pledgeSubjectService;
    private final PledgeAgreementService pledgeAgreementService;
    private final LoanAgreementService loanAgreementService;

    private static final String ATTR_PLEDGE_AGREEMENT_LIST = "pledgeAgreementDtoList";
    private static final String ATTR_LOAN_AGREEMENT_LIST = "loanAgreementDtoList";
    private static final String ATTR_PLEDGE_SUBJECT = "pledgeSubjectDto";
    private static final String ATTR_COST_HISTORY = "costHistoryDto";
    private static final String ATTR_MONITORING = "monitoringDto";
    private static final String PAGE_DETAIL = "pledge_subject/detail";
    private static final String PAGE_CARD_UPDATE = "pledge_subject/card_update";
    private static final String PAGE_CARD_NEW = "pledge_subject/card_new";


    public PledgeSubjectController(PledgeSubjectService pledgeSubjectService,
                                   PledgeAgreementService pledgeAgreementService,
                                   LoanAgreementService loanAgreementService) {
        this.pledgeSubjectService = pledgeSubjectService;
        this.pledgeAgreementService = pledgeAgreementService;
        this.loanAgreementService = loanAgreementService;
    }

    @GetMapping("/detail")
    public String pledgeSubjectDetailPage(@RequestParam("pledgeSubjectId") Long pledgeSubjectId,
                                          Model model){

        PledgeSubjectDto pledgeSubjectDto = pledgeSubjectService.getPledgeSubjectById(pledgeSubjectId);
        List<PledgeAgreementDto> pledgeAgreementDtoList = pledgeAgreementService
                .getPledgeAgreementByPledgeSubject(pledgeSubjectId);
        List<LoanAgreementDto> loanAgreementDtoList = loanAgreementService.getLoanAgreementByPledgeAgreements(pledgeAgreementDtoList);

        model.addAttribute(ATTR_PLEDGE_SUBJECT, pledgeSubjectDto);
        model.addAttribute(ATTR_PLEDGE_AGREEMENT_LIST, pledgeAgreementDtoList);
        model.addAttribute(ATTR_LOAN_AGREEMENT_LIST, loanAgreementDtoList);

        return PAGE_DETAIL;
    }

    @GetMapping("/card_update")
    public String pledgeSubjectCardUpdate(@RequestParam("pledgeSubjectId") Long pledgeSubjectId,
                                          Model model){

        PledgeSubjectDto pledgeSubjectDto = pledgeSubjectService.getPledgeSubjectById(pledgeSubjectId);

        model.addAttribute(ATTR_PLEDGE_SUBJECT, pledgeSubjectDto);

        return PAGE_CARD_UPDATE;
    }

    @PostMapping("/update_pledge_subject")
    public String updatePledgeSubject(@Valid PledgeSubjectDto pledgeSubjectDto,
                                      BindingResult bindingResult,
                                      Model model){

        if(bindingResult.hasErrors()) {
            return PAGE_CARD_UPDATE;
        }

        List<Long> pledgeAgreementIds = pledgeAgreementService.getPledgeAgreementByPledgeSubject(pledgeSubjectDto.getPledgeSubjectId())
                .stream().map(PledgeAgreementDto::getPledgeAgreementId).collect(Collectors.toList());

        pledgeSubjectDto = pledgeSubjectService.updatePledgeSubject(new PledgeSubjectUpdateDtoWrapper(pledgeSubjectDto, pledgeAgreementIds));

        return pledgeSubjectDetailPage(pledgeSubjectDto.getPledgeSubjectId(), model);
    }
}
