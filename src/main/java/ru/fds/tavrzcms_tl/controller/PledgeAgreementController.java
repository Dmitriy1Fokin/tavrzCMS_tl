package ru.fds.tavrzcms_tl.controller;

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
import ru.fds.tavrzcms_tl.exception.NotFoundException;
import ru.fds.tavrzcms_tl.service.EmployeeService;
import ru.fds.tavrzcms_tl.service.LoanAgreementService;
import ru.fds.tavrzcms_tl.service.PledgeAgreementService;
import ru.fds.tavrzcms_tl.wrapper.PledgeAgreementDtoWrapper;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/pledge_agreement")
public class PledgeAgreementController {

    private final PledgeAgreementService pledgeAgreementService;
    private final LoanAgreementService loanAgreementService;
    private final EmployeeService employeeService;

    private static final String ATTR_PLEDGE_AGREEMENT = "pledgeAgreementDto";
    private static final String ATTR_WHAT_DO = "whatDo";
    private static final String ATTR_PLEDGE_AGREEMENT_LIST = "pledgeAgreementList";
    private static final String ATTR_EMPLOYEE_ID = "employeeId";
    private static final String ATTR_PAGE = "page";
    private static final String ATTR_SIZE = "size";
    private static final String PAGE_CARD = "pledge_agreement/card";
    private static final String PAGE_PA = "pledge_agreement/pledge_agreements";
    private static final String PAGE_DETAIL = "pledge_agreement/detail";
    private static final String MSG_WRONG_LINK = "Неверная ссылка";

    public PledgeAgreementController(PledgeAgreementService pledgeAgreementService,
                                     LoanAgreementService loanAgreementService,
                                     EmployeeService employeeService) {
        this.pledgeAgreementService = pledgeAgreementService;
        this.loanAgreementService = loanAgreementService;
        this.employeeService = employeeService;
    }

    @GetMapping("/pledge_agreements_all_emp")
    public String allPledgeAgreementsPageFoeEmployee(@RequestParam("employeeId") Long employeeId,
                                                     @RequestParam("page") Optional<Integer> page,
                                                     @RequestParam("size") Optional<Integer> size,
                                                     Model model) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(50));

        List<PledgeAgreementDto> pledgeAgreementDtoList = pledgeAgreementService.getCurrentPledgeAgreementByEmployee(employeeId, pageable);

        model.addAttribute(ATTR_PLEDGE_AGREEMENT_LIST, pledgeAgreementDtoList);
        model.addAttribute(ATTR_EMPLOYEE_ID, employeeId);
        model.addAttribute(ATTR_PAGE, page.orElse(0));
        model.addAttribute(ATTR_SIZE, size.orElse(50));

        return PAGE_PA;
    }

    @GetMapping("/pledge_agreements_all_guest")
    public String allPledgeAgreementsPageForGuest(@RequestParam(ATTR_PAGE) Optional<Integer> page,
                                                  @RequestParam(ATTR_SIZE) Optional<Integer> size,
                                                  Model model) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(50));

        List<PledgeAgreementDto> pledgeAgreementDtoList = pledgeAgreementService.getCurrentPledgeAgreements(pageable);

        model.addAttribute(ATTR_PLEDGE_AGREEMENT_LIST, pledgeAgreementDtoList);
        model.addAttribute(ATTR_PAGE, page.orElse(0));
        model.addAttribute(ATTR_SIZE, size.orElse(50));

        return PAGE_PA;
    }

    @GetMapping("/pledge_agreements_perv_posl_emp")
    public String pervPoslPledgeAgreementsPageForEmployee(@RequestParam(ATTR_EMPLOYEE_ID) Long employeeId,
                                                          @RequestParam(ATTR_PAGE) Optional<Integer> page,
                                                          @RequestParam(ATTR_SIZE) Optional<Integer> size,
                                                          @RequestParam("pervPosl") TypeOfPledgeAgreement typeOfPledgeAgreement,
                                                          Model model) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(50));

        List<PledgeAgreementDto> pledgeAgreementDtoList = pledgeAgreementService.getCurrentPledgeAgreementByEmployee(employeeId,
                typeOfPledgeAgreement,
                pageable);

        model.addAttribute(ATTR_PLEDGE_AGREEMENT_LIST, pledgeAgreementDtoList);
        model.addAttribute(ATTR_EMPLOYEE_ID, employeeId);
        model.addAttribute(ATTR_PAGE, page.orElse(0));
        model.addAttribute(ATTR_SIZE, size.orElse(50));
        model.addAttribute("pervPosl", typeOfPledgeAgreement.toString());

        return PAGE_PA;
    }

    @GetMapping("/pledge_agreements_perv_posl_guest")
    public String pervPoslPledgeAgreementsPageForGuest(@RequestParam(ATTR_PAGE) Optional<Integer> page,
                                                       @RequestParam(ATTR_SIZE) Optional<Integer> size,
                                                       @RequestParam("pervPosl") TypeOfPledgeAgreement typeOfPledgeAgreement,
                                                       Model model) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(50));

        List<PledgeAgreementDto> pledgeAgreementDtoList = pledgeAgreementService.getCurrentPledgeAgreements(typeOfPledgeAgreement, pageable);

        model.addAttribute(ATTR_PLEDGE_AGREEMENT_LIST, pledgeAgreementDtoList);
        model.addAttribute(ATTR_PAGE, page.orElse(0));
        model.addAttribute(ATTR_SIZE, size.orElse(50));
        model.addAttribute("pervPosl", typeOfPledgeAgreement.toString());

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

    @GetMapping("/card")
    public String pledgeAgreementCard(@RequestParam("pledgeAgreementId") Optional<Long> pledgeAgreementId,
                                      @RequestParam("clientId") Optional<Long> clientId,
                                      @RequestParam("whatDo") String whatDo,
                                      Model model){
        if(whatDo.equals("changePA")){

            PledgeAgreementDto pledgeAgreementDto = pledgeAgreementService.getPledgeAgreementById(pledgeAgreementId
                    .orElseThrow(() -> new NotFoundException("Pledge Agreement not found")));

            model.addAttribute(ATTR_PLEDGE_AGREEMENT, pledgeAgreementDto);
            model.addAttribute(ATTR_WHAT_DO, whatDo);

            return PAGE_CARD;

        }else if(whatDo.equals("newPA")){

            PledgeAgreementDto pledgeAgreementDto = PledgeAgreementDto.builder()
                    .clientId(clientId.orElseThrow(() -> new NotFoundException("Client not found")))
                    .build();

            model.addAttribute(ATTR_PLEDGE_AGREEMENT, pledgeAgreementDto);
            model.addAttribute(ATTR_WHAT_DO, whatDo);

            return PAGE_CARD;

        }else
            throw new IllegalArgumentException(MSG_WRONG_LINK);
    }

    @PostMapping("/update_insert")
    public String updateInsertPledgeAgreement(@Valid PledgeAgreementDto pledgeAgreementDto,
                                              BindingResult bindingResult,
                                              @RequestParam("whatDo") String whatDo,
                                              Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute(ATTR_WHAT_DO, whatDo);
            return PAGE_CARD;
        }

        List<Long> loanAgreementIds = loanAgreementService.getCurrentLoanAgreementsByPledgeAgreement(pledgeAgreementDto.getPledgeAgreementId())
                .stream().map(LoanAgreementDto::getLoanAgreementId).collect(Collectors.toList());

        loanAgreementIds.addAll(loanAgreementService.getClosedLoanAgreementsByPledgeAgreement(pledgeAgreementDto.getPledgeAgreementId())
                .stream().map(LoanAgreementDto::getLoanAgreementId).collect(Collectors.toList()));

        PledgeAgreementDtoWrapper pledgeAgreementDtoWrapper = new PledgeAgreementDtoWrapper(pledgeAgreementDto, loanAgreementIds);
        pledgeAgreementDto = pledgeAgreementService.updatePledgeAgreement(pledgeAgreementDtoWrapper);

        return pledgeAgreementDetailPage(pledgeAgreementDto.getPledgeAgreementId(), model);
    }

}
