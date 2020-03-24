package ru.fds.tavrzcms_tl.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.fds.tavrzcms_tl.dictionary.TypeOfCollateral;
import ru.fds.tavrzcms_tl.dictionary.TypeOfPledgeAgreement;
import ru.fds.tavrzcms_tl.dto.CostHistoryDto;
import ru.fds.tavrzcms_tl.dto.EmployeeDto;
import ru.fds.tavrzcms_tl.dto.LoanAgreementDto;
import ru.fds.tavrzcms_tl.dto.MonitoringDto;
import ru.fds.tavrzcms_tl.dto.PledgeAgreementDto;
import ru.fds.tavrzcms_tl.dto.PledgeSubjectAutoDto;
import ru.fds.tavrzcms_tl.dto.PledgeSubjectBuildingDto;
import ru.fds.tavrzcms_tl.dto.PledgeSubjectDto;
import ru.fds.tavrzcms_tl.dto.PledgeSubjectEquipmentDto;
import ru.fds.tavrzcms_tl.dto.PledgeSubjectLandLeaseDto;
import ru.fds.tavrzcms_tl.dto.PledgeSubjectLandOwnershipDto;
import ru.fds.tavrzcms_tl.dto.PledgeSubjectRoomDto;
import ru.fds.tavrzcms_tl.dto.PledgeSubjectSecuritiesDto;
import ru.fds.tavrzcms_tl.dto.PledgeSubjectTboDto;
import ru.fds.tavrzcms_tl.dto.PledgeSubjectVesselDto;
import ru.fds.tavrzcms_tl.service.EmployeeService;
import ru.fds.tavrzcms_tl.service.LoanAgreementService;
import ru.fds.tavrzcms_tl.service.PledgeAgreementService;
import ru.fds.tavrzcms_tl.service.PledgeSubjectService;
import ru.fds.tavrzcms_tl.wrapper.PledgeAgreementDtoWrapper;
import ru.fds.tavrzcms_tl.wrapper.PledgeSubjectDtoNewWrapper;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
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
    private static final String ATTR_PLEDGE_AGREEMENT_ID = "pledgeAgreementId";
    private static final String ATTR_PLEDGE_SUBJECT = "pledgeSubjectDto";
    private static final String ATTR_COST_HISTORY = "costHistoryDto";
    private static final String ATTR_MONITORING = "monitoringDto";
    private static final String PAGE_CARD_UPDATE = "pledge_agreement/card_update";
    private static final String PAGE_CARD_INSERT = "pledge_agreement/card_insert";
    private static final String PAGE_PA = "pledge_agreement/pledge_agreements";
    private static final String PAGE_DETAIL = "pledge_agreement/detail";
    private static final String PAGE_PLEDGE_SUBJECTS = "pledge_agreement/pledge_subjects";
    private static final String PAGE_PLEDGE_SUBJECT_CARD_NEW = "pledge_subject/card_new";

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
                .zsDz(BigDecimal.ZERO)
                .zsZz(BigDecimal.ZERO)
                .rsDz(BigDecimal.ZERO)
                .rsZz(BigDecimal.ZERO)
                .ss(BigDecimal.ZERO)
                .build();

        model.addAttribute(ATTR_PLEDGE_AGREEMENT, pledgeAgreementDto);

        return PAGE_CARD_INSERT;
    }

    @PutMapping("/update")
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

    @PostMapping("/insert")
    public String insertPledgeAgreement(@Valid PledgeAgreementDto pledgeAgreementDto,
                                        BindingResult bindingResult,
                                        Model model){

        if(bindingResult.hasErrors()){
            return PAGE_CARD_INSERT;
        }

        PledgeAgreementDtoWrapper pledgeAgreementDtoWrapper = new PledgeAgreementDtoWrapper(pledgeAgreementDto, Collections.emptyList());
        pledgeAgreementDto = pledgeAgreementService.insertPledgeAgreement(pledgeAgreementDtoWrapper);

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

    @GetMapping("/searchLA")
    public @ResponseBody List<LoanAgreementDto> searchLA(@RequestParam("numLA") String numLA){
        return loanAgreementService.getLoanAgreementByNum(numLA);
    }

    @PostMapping("/insertLA")
    public String insertLA(@RequestParam("pledgeAgreementId") long pledgeAgreementId,
                           @RequestParam("loanAgreementIdArray[]") long[] loanAgreementIdArray,
                           Model model){

        PledgeAgreementDto pledgeAgreementDto = pledgeAgreementService.getPledgeAgreementById(pledgeAgreementId);

        List<Long> loanAgreementIds = loanAgreementService.getCurrentLoanAgreementsByPledgeAgreement(pledgeAgreementDto.getPledgeAgreementId())
                .stream().map(LoanAgreementDto::getLoanAgreementId).collect(Collectors.toList());
        loanAgreementIds.addAll(loanAgreementService.getClosedLoanAgreementsByPledgeAgreement(pledgeAgreementDto.getPledgeAgreementId())
                .stream().map(LoanAgreementDto::getLoanAgreementId).collect(Collectors.toList()));
        loanAgreementIds.addAll(Arrays.stream(loanAgreementIdArray).boxed().collect(Collectors.toList()));

        PledgeAgreementDtoWrapper pledgeAgreementDtoWrapper = new PledgeAgreementDtoWrapper(pledgeAgreementDto, loanAgreementIds);
        pledgeAgreementDto = pledgeAgreementService.updatePledgeAgreement(pledgeAgreementDtoWrapper);

        return pledgeAgreementDetailPage(pledgeAgreementDto.getPledgeAgreementId(), model);
    }

    @GetMapping("/pledge_subject/insert/card")
    public String pledgeSubjectCardNew(@RequestParam("typeOfCollateral") TypeOfCollateral typeOfCollateral,
                                       @RequestParam("pledgeAgreementId") Long pledgeAgreementId,
                                       Model model){

        PledgeSubjectDto pledgeSubjectDto = PledgeSubjectDto.builder()
                .typeOfCollateral(typeOfCollateral)
                .build();

        if(typeOfCollateral == TypeOfCollateral.AUTO)
            pledgeSubjectDto.setPledgeSubjectAutoDto(new PledgeSubjectAutoDto());
        else if (typeOfCollateral == TypeOfCollateral.EQUIPMENT)
            pledgeSubjectDto.setPledgeSubjectEquipmentDto(new PledgeSubjectEquipmentDto());
        else if (typeOfCollateral == TypeOfCollateral.BUILDING)
            pledgeSubjectDto.setPledgeSubjectBuildingDto(new PledgeSubjectBuildingDto());
        else if (typeOfCollateral == TypeOfCollateral.LAND_LEASE)
            pledgeSubjectDto.setPledgeSubjectLandLeaseDto(new PledgeSubjectLandLeaseDto());
        else if (typeOfCollateral == TypeOfCollateral.LAND_OWNERSHIP)
            pledgeSubjectDto.setPledgeSubjectLandOwnershipDto(new PledgeSubjectLandOwnershipDto());
        else if (typeOfCollateral == TypeOfCollateral.PREMISE)
            pledgeSubjectDto.setPledgeSubjectRoomDto(new PledgeSubjectRoomDto());
        else if (typeOfCollateral == TypeOfCollateral.SECURITIES)
            pledgeSubjectDto.setPledgeSubjectSecuritiesDto(new PledgeSubjectSecuritiesDto());
        else if (typeOfCollateral == TypeOfCollateral.TBO)
            pledgeSubjectDto.setPledgeSubjectTboDto(new PledgeSubjectTboDto());
        else if (typeOfCollateral == TypeOfCollateral.VESSEL)
            pledgeSubjectDto.setPledgeSubjectVesselDto(new PledgeSubjectVesselDto());

        CostHistoryDto costHistoryDto = new CostHistoryDto();
        MonitoringDto monitoringDto = new MonitoringDto();

        model.addAttribute(ATTR_PLEDGE_SUBJECT, pledgeSubjectDto);
        model.addAttribute(ATTR_PLEDGE_AGREEMENT_ID, pledgeAgreementId);
        model.addAttribute(ATTR_COST_HISTORY, costHistoryDto);
        model.addAttribute(ATTR_MONITORING, monitoringDto);

        return PAGE_PLEDGE_SUBJECT_CARD_NEW;
    }

    @PostMapping("pledge_subject/insert")
    public String insertNewPledgeSubject(@Valid PledgeSubjectDto pledgeSubjectDto,
                                         BindingResult bindingResult,
                                         @Valid CostHistoryDto costHistoryDto,
                                         BindingResult bindingResultCostHistory,
                                         @Valid MonitoringDto monitoringDto,
                                         BindingResult bindingResultMonitoring,
                                         @RequestParam("pledgeAgreementId") Long pledgeAgreementId,
                                         Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute(ATTR_PLEDGE_AGREEMENT_ID, pledgeAgreementId);
            model.addAttribute(ATTR_COST_HISTORY, costHistoryDto);
            model.addAttribute(ATTR_MONITORING, monitoringDto);
            return PAGE_PLEDGE_SUBJECT_CARD_NEW;

        }else if(bindingResultCostHistory.hasErrors()){
            model.addAttribute(ATTR_PLEDGE_SUBJECT, pledgeSubjectDto);
            model.addAttribute(ATTR_PLEDGE_AGREEMENT_ID, pledgeAgreementId);
            model.addAttribute(ATTR_MONITORING, monitoringDto);

            return PAGE_PLEDGE_SUBJECT_CARD_NEW;

        }else if(bindingResultMonitoring.hasErrors()){
            model.addAttribute(ATTR_PLEDGE_SUBJECT, pledgeSubjectDto);
            model.addAttribute(ATTR_PLEDGE_AGREEMENT_ID, pledgeAgreementId);
            model.addAttribute(ATTR_COST_HISTORY, costHistoryDto);

            return PAGE_PLEDGE_SUBJECT_CARD_NEW;
        }

        PledgeSubjectDtoNewWrapper pledgeSubjectDtoNewWrapper = new PledgeSubjectDtoNewWrapper(pledgeSubjectDto,
                costHistoryDto,
                monitoringDto,
                Collections.singletonList(pledgeAgreementId));

        pledgeSubjectService.insertPledgeSubject(pledgeSubjectDtoNewWrapper);

        return pledgeSubjectsPage(pledgeAgreementId, model);
    }

}
