package ru.fds.tavrzcms_tl.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.fds.tavrzcms_tl.dictionary.TypeOfPledgeAgreement;
import ru.fds.tavrzcms_tl.dto.EmployeeDto;
import ru.fds.tavrzcms_tl.dto.LoanAgreementDto;
import ru.fds.tavrzcms_tl.dto.PledgeAgreementDto;
import ru.fds.tavrzcms_tl.dto.PledgeSubjectDto;
import ru.fds.tavrzcms_tl.service.EmployeeService;
import ru.fds.tavrzcms_tl.service.LoanAgreementService;
import ru.fds.tavrzcms_tl.service.PledgeAgreementService;
import ru.fds.tavrzcms_tl.service.PledgeSubjectService;
import ru.fds.tavrzcms_tl.wrapper.PledgeAgreementDtoWrapper;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/pledge_agreement")
public class PledgeAgreementController {

    private final PledgeAgreementService pledgeAgreementService;
    private final LoanAgreementService loanAgreementService;
    private final EmployeeService employeeService;
    private final PledgeSubjectService pledgeSubjectService;

    @Value("${page_size}")
    private Integer pageSize;
    private static final String ATTR_PLEDGE_AGREEMENT = "pledgeAgreementDto";
    private static final String ATTR_PLEDGE_AGREEMENT_LIST = "pledgeAgreementList";
    private static final String ATTR_EMPLOYEE_ID = "employeeId";
    private static final String ATTR_PAGE = "page";
    private static final String ATTR_PLEDGE_SUBJECT_LIST = "pledgeSubjectDtoList";
    private static final String ATTR_PERV_POSL = "pervPosl";
    private static final String PAGE_CARD_UPDATE = "pledge_agreement/card_update";
    private static final String PAGE_CARD_INSERT = "pledge_agreement/card_insert";
    private static final String PAGE_PA = "pledge_agreement/pledge_agreements";
    private static final String PAGE_DETAIL = "pledge_agreement/detail";
    private static final String PAGE_PLEDGE_SUBJECTS = "pledge_agreement/pledge_subjects";

    public PledgeAgreementController(PledgeAgreementService pledgeAgreementService,
                                     LoanAgreementService loanAgreementService,
                                     EmployeeService employeeService,
                                     PledgeSubjectService pledgeSubjectService) {
        this.pledgeAgreementService = pledgeAgreementService;
        this.loanAgreementService = loanAgreementService;
        this.employeeService = employeeService;
        this.pledgeSubjectService = pledgeSubjectService;
    }

    @GetMapping("/pledge_agreements/all/emp")
    public String allPledgeAgreementsPageFoeEmployee(@RequestParam("employeeId") Long employeeId,
                                                     @RequestParam("page") Integer page,
                                                     Model model) {
        Pageable pageable = PageRequest.of(page, pageSize);

        List<PledgeAgreementDto> pledgeAgreementDtoList = pledgeAgreementService.getCurrentPledgeAgreementByEmployee(employeeId, pageable);

        model.addAttribute(ATTR_PLEDGE_AGREEMENT_LIST, pledgeAgreementDtoList);
        model.addAttribute(ATTR_EMPLOYEE_ID, employeeId);
        model.addAttribute(ATTR_PAGE, page);

        return PAGE_PA;
    }

    @GetMapping("/pledge_agreements/all/guest")
    public String allPledgeAgreementsPageForGuest(@RequestParam(ATTR_PAGE) Integer page,
                                                  Model model) {
        Pageable pageable = PageRequest.of(page, pageSize);

        List<PledgeAgreementDto> pledgeAgreementDtoList = pledgeAgreementService.getCurrentPledgeAgreements(pageable);

        model.addAttribute(ATTR_PLEDGE_AGREEMENT_LIST, pledgeAgreementDtoList);
        model.addAttribute(ATTR_PAGE, page);

        return PAGE_PA;
    }

    @GetMapping("/pledge_agreements/perv_posl/emp")
    public String pervPoslPledgeAgreementsPageForEmployee(@RequestParam(ATTR_EMPLOYEE_ID) Long employeeId,
                                                          @RequestParam(ATTR_PAGE) Integer page,
                                                          @RequestParam("pervPosl") TypeOfPledgeAgreement typeOfPledgeAgreement,
                                                          Model model) {
        Pageable pageable = PageRequest.of(page, pageSize);

        List<PledgeAgreementDto> pledgeAgreementDtoList = pledgeAgreementService.getCurrentPledgeAgreementByEmployee(employeeId,
                typeOfPledgeAgreement,
                pageable);

        model.addAttribute(ATTR_PLEDGE_AGREEMENT_LIST, pledgeAgreementDtoList);
        model.addAttribute(ATTR_EMPLOYEE_ID, employeeId);
        model.addAttribute(ATTR_PAGE, page);
        model.addAttribute(ATTR_PERV_POSL, typeOfPledgeAgreement.toString());

        return PAGE_PA;
    }

    @GetMapping("/pledge_agreements/perv_posl/guest")
    public String pervPoslPledgeAgreementsPageForGuest(@RequestParam(ATTR_PAGE) Integer page,
                                                       @RequestParam("pervPosl") TypeOfPledgeAgreement typeOfPledgeAgreement,
                                                       Model model) {
        Pageable pageable = PageRequest.of(page, pageSize);

        List<PledgeAgreementDto> pledgeAgreementDtoList = pledgeAgreementService.getCurrentPledgeAgreements(typeOfPledgeAgreement, pageable);

        model.addAttribute(ATTR_PLEDGE_AGREEMENT_LIST, pledgeAgreementDtoList);
        model.addAttribute(ATTR_PAGE, page);
        model.addAttribute(ATTR_PERV_POSL, typeOfPledgeAgreement.toString());

        return PAGE_PA;
    }

    @GetMapping("/detail")
    public String pledgeAgreementDetailPage(@RequestParam("pledgeAgreementId") long pledgeAgreementId,
                                            Model model){

        PledgeAgreementDto pledgeAgreementDto = pledgeAgreementService.getPledgeAgreementById(pledgeAgreementId);
        EmployeeDto employeeDto = employeeService.getEmployeeByPledgeAgreement(pledgeAgreementId);
        List<LoanAgreementDto> currentLoanAgreementDtoList = loanAgreementService.getCurrentLoanAgreementsByPledgeAgreement(pledgeAgreementId);
        List<LoanAgreementDto> closedLoanAgreementDtoList = loanAgreementService.getClosedLoanAgreementsByPledgeAgreement(pledgeAgreementId);

        model.addAttribute(ATTR_PLEDGE_AGREEMENT, pledgeAgreementDto);
        model.addAttribute("employeeDto", employeeDto);
        model.addAttribute("currentLoanAgreementDtoList",currentLoanAgreementDtoList);
        model.addAttribute("closedLoanAgreementDtoList", closedLoanAgreementDtoList);

        return PAGE_DETAIL;
    }

    @GetMapping("/update/card")
    public String pledgeAgreementCardUpdatePage(@RequestParam("pledgeAgreementId") Long pledgeAgreementId,
                                                Model model){
        PledgeAgreementDto pledgeAgreementDto = pledgeAgreementService.getPledgeAgreementById(pledgeAgreementId);

        model.addAttribute(ATTR_PLEDGE_AGREEMENT, pledgeAgreementDto);

        return PAGE_CARD_UPDATE;
    }

    @GetMapping("/insert/card")
    public String pledgeAgreementCardInsertPage(@RequestParam("clientId") Long clientId,
                                                Model model){
        PledgeAgreementDto pledgeAgreementDto = PledgeAgreementDto.builder()
                .clientId(clientId)
                .build();

        model.addAttribute(ATTR_PLEDGE_AGREEMENT, pledgeAgreementDto);

        return PAGE_CARD_INSERT;
    }

    @PostMapping("/update")
    public String updatePledgeAgreement(@Valid PledgeAgreementDto pledgeAgreementDto,
                                              BindingResult bindingResult,
                                              Model model){

        if(bindingResult.hasErrors()){
            return PAGE_CARD_UPDATE;
        }

        List<Long> loanAgreementIds = loanAgreementService.getCurrentLoanAgreementsByPledgeAgreement(pledgeAgreementDto.getPledgeAgreementId())
                .stream().map(LoanAgreementDto::getLoanAgreementId).collect(Collectors.toList());

        loanAgreementIds.addAll(loanAgreementService.getClosedLoanAgreementsByPledgeAgreement(pledgeAgreementDto.getPledgeAgreementId())
                .stream().map(LoanAgreementDto::getLoanAgreementId).collect(Collectors.toList()));

        PledgeAgreementDtoWrapper pledgeAgreementDtoWrapper = new PledgeAgreementDtoWrapper(pledgeAgreementDto, loanAgreementIds);
        pledgeAgreementDto = pledgeAgreementService.updatePledgeAgreement(pledgeAgreementDtoWrapper);

        return pledgeAgreementDetailPage(pledgeAgreementDto.getPledgeAgreementId(), model);
    }

    @GetMapping("/pledge_subjects")
    public String pledgeSubjectsPage(@RequestParam("pledgeAgreementId") long pledgeAgreementId,
                                     Model model){

        PledgeAgreementDto pledgeAgreementDto = pledgeAgreementService.getPledgeAgreementById(pledgeAgreementId);

        List<PledgeSubjectDto> pledgeSubjectDtoList = pledgeSubjectService.getPledgeSubjectsByPledgeAgreement(pledgeAgreementId);

        model.addAttribute(ATTR_PLEDGE_AGREEMENT, pledgeAgreementDto);
        model.addAttribute(ATTR_PLEDGE_SUBJECT_LIST, pledgeSubjectDtoList);

        return PAGE_PLEDGE_SUBJECTS;
    }

}
