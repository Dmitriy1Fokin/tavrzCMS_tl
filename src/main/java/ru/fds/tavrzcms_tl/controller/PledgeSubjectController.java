package ru.fds.tavrzcms_tl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.fds.tavrzcms_tl.dictionary.TypeOfCollateral;
import ru.fds.tavrzcms_tl.dto.AuditResultDto;
import ru.fds.tavrzcms_tl.dto.CostHistoryDto;
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
import ru.fds.tavrzcms_tl.service.AuditResultService;
import ru.fds.tavrzcms_tl.service.LoanAgreementService;
import ru.fds.tavrzcms_tl.service.PledgeAgreementService;
import ru.fds.tavrzcms_tl.service.PledgeSubjectService;
import ru.fds.tavrzcms_tl.wrapper.PledgeSubjectDtoNewWrapper;
import ru.fds.tavrzcms_tl.wrapper.PledgeSubjectUpdateDtoWrapper;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/pledge_subject")
public class PledgeSubjectController {

    private final PledgeSubjectService pledgeSubjectService;
    private final PledgeAgreementService pledgeAgreementService;
    private final LoanAgreementService loanAgreementService;
    private final AuditResultService auditResultService;

    private static final String ATTR_PLEDGE_AGREEMENT_LIST = "pledgeAgreementDtoList";
    private static final String ATTR_LOAN_AGREEMENT_LIST = "loanAgreementDtoList";
    private static final String ATTR_PLEDGE_AGREEMENT_ID = "pledgeAgreementId";
    private static final String ATTR_PLEDGE_SUBJECT = "pledgeSubjectDto";
    private static final String ATTR_AUDIT_RESULT = "auditResultList";
    private static final String ATTR_COST_HISTORY = "costHistoryDto";
    private static final String ATTR_MONITORING = "monitoringDto";
    private static final String PAGE_DETAIL = "pledge_subject/detail";
    private static final String PAGE_CARD_UPDATE = "pledge_subject/card_update";
    private static final String PAGE_CARD_NEW = "pledge_subject/card_new";


    public PledgeSubjectController(PledgeSubjectService pledgeSubjectService,
                                   PledgeAgreementService pledgeAgreementService,
                                   LoanAgreementService loanAgreementService,
                                   AuditResultService auditResultService) {
        this.pledgeSubjectService = pledgeSubjectService;
        this.pledgeAgreementService = pledgeAgreementService;
        this.loanAgreementService = loanAgreementService;
        this.auditResultService = auditResultService;
    }

    @GetMapping("/detail")
    public String pledgeSubjectDetailPage(@RequestParam("pledgeSubjectId") Long pledgeSubjectId,
                                          Model model){

        PledgeSubjectDto pledgeSubjectDto = pledgeSubjectService.getPledgeSubjectById(pledgeSubjectId);
        List<PledgeAgreementDto> pledgeAgreementDtoList = pledgeAgreementService
                .getPledgeAgreementByPledgeSubject(pledgeSubjectId);
        List<LoanAgreementDto> loanAgreementDtoList = loanAgreementService.getLoanAgreementByPledgeAgreements(pledgeAgreementDtoList);
        List<AuditResultDto> auditResultDtoList = auditResultService.getActualAuditResultAboutPledgeSubject(pledgeSubjectId);

        model.addAttribute(ATTR_PLEDGE_SUBJECT, pledgeSubjectDto);
        model.addAttribute(ATTR_PLEDGE_AGREEMENT_LIST, pledgeAgreementDtoList);
        model.addAttribute(ATTR_LOAN_AGREEMENT_LIST, loanAgreementDtoList);
        model.addAttribute(ATTR_AUDIT_RESULT, auditResultDtoList);

        return PAGE_DETAIL;
    }

    @GetMapping("/update/card")
    public String pledgeSubjectCardUpdate(@RequestParam("pledgeSubjectId") Long pledgeSubjectId,
                                          Model model){

        PledgeSubjectDto pledgeSubjectDto = pledgeSubjectService.getPledgeSubjectById(pledgeSubjectId);

        model.addAttribute(ATTR_PLEDGE_SUBJECT, pledgeSubjectDto);

        return PAGE_CARD_UPDATE;
    }

    @PutMapping("/update")
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

    @GetMapping("/audit/set_ignore")
    public String setIgnoreAuditResult(@RequestParam("auditResultId") String auditResultId,
                                       @RequestParam("pledgeSubjectId") Long pledgeSubjectId,
                                       Model model){
        auditResultService.setIgnore(auditResultId);
        return pledgeSubjectDetailPage(pledgeSubjectId, model);
    }

    @GetMapping("/insert/card")
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

        return PAGE_CARD_NEW;
    }

    @PostMapping("/insert")
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
            return PAGE_CARD_NEW;

        }else if(bindingResultCostHistory.hasErrors()){
            model.addAttribute(ATTR_PLEDGE_SUBJECT, pledgeSubjectDto);
            model.addAttribute(ATTR_PLEDGE_AGREEMENT_ID, pledgeAgreementId);
            model.addAttribute(ATTR_MONITORING, monitoringDto);

            return PAGE_CARD_NEW;

        }else if(bindingResultMonitoring.hasErrors()){
            model.addAttribute(ATTR_PLEDGE_SUBJECT, pledgeSubjectDto);
            model.addAttribute(ATTR_PLEDGE_AGREEMENT_ID, pledgeAgreementId);
            model.addAttribute(ATTR_COST_HISTORY, costHistoryDto);

            return PAGE_CARD_NEW;
        }

        PledgeSubjectDtoNewWrapper pledgeSubjectDtoNewWrapper = new PledgeSubjectDtoNewWrapper(pledgeSubjectDto,
                costHistoryDto,
                monitoringDto,
                Collections.singletonList(pledgeAgreementId));

        pledgeSubjectDto = pledgeSubjectService.insertPledgeSubject(pledgeSubjectDtoNewWrapper);

        return pledgeSubjectDetailPage(pledgeSubjectDto.getPledgeSubjectId(), model);
    }
}
