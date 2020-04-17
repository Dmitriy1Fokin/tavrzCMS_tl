package ru.fds.tavrzcms_tl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.fds.tavrzcms_tl.dto.ClientDto;
import ru.fds.tavrzcms_tl.dto.ClientManagerDto;
import ru.fds.tavrzcms_tl.dto.CostHistoryDto;
import ru.fds.tavrzcms_tl.dto.EncumbranceDto;
import ru.fds.tavrzcms_tl.dto.InsuranceDto;
import ru.fds.tavrzcms_tl.dto.LoanAgreementDto;
import ru.fds.tavrzcms_tl.dto.MonitoringDto;
import ru.fds.tavrzcms_tl.dto.PledgeAgreementDto;
import ru.fds.tavrzcms_tl.dto.PledgeSubjectDto;
import ru.fds.tavrzcms_tl.service.ClientManagerService;
import ru.fds.tavrzcms_tl.service.ClientService;
import ru.fds.tavrzcms_tl.service.CostHistoryService;
import ru.fds.tavrzcms_tl.service.EncumbranceService;
import ru.fds.tavrzcms_tl.service.InsuranceService;
import ru.fds.tavrzcms_tl.service.LoanAgreementService;
import ru.fds.tavrzcms_tl.service.MonitoringService;
import ru.fds.tavrzcms_tl.service.PledgeAgreementService;
import ru.fds.tavrzcms_tl.service.PledgeSubjectService;

import java.util.List;

@Controller
@RequestMapping("/update")
public class UpdateController {

    private final PledgeAgreementService pledgeAgreementService;
    private final LoanAgreementService loanAgreementService;
    private final ClientService clientService;
    private final PledgeSubjectService pledgeSubjectService;
    private final InsuranceService insuranceService;
    private final EncumbranceService encumbranceService;
    private final CostHistoryService costHistoryService;
    private final MonitoringService monitoringService;
    private final ClientManagerService clientManagerService;

    private static final String PAGE_UPDATE = "update";

    public UpdateController(PledgeAgreementService pledgeAgreementService,
                            LoanAgreementService loanAgreementService,
                            ClientService clientService,
                            PledgeSubjectService pledgeSubjectService,
                            InsuranceService insuranceService,
                            EncumbranceService encumbranceService,
                            CostHistoryService costHistoryService,
                            MonitoringService monitoringService,
                            ClientManagerService clientManagerService) {
        this.pledgeAgreementService = pledgeAgreementService;
        this.loanAgreementService = loanAgreementService;
        this.clientService = clientService;
        this.pledgeSubjectService = pledgeSubjectService;
        this.insuranceService = insuranceService;
        this.encumbranceService = encumbranceService;
        this.costHistoryService = costHistoryService;
        this.monitoringService = monitoringService;
        this.clientManagerService = clientManagerService;
    }

    @GetMapping("/")
    public String updatePage(){
        return PAGE_UPDATE;
    }

    @PostMapping("/insert_from_file")
    public String importClientLegalEntityFromExcel(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("whatUpload") String whatUpload,
                                                   Model model){
        switch (whatUpload) {
            case "clientLegalEntity":
                insertClientLegalEntity(file, model);
                break;
            case "clientIndividual":
                insertClientIndividual(file, model);
                break;
            case "loanAgreement":
                insertLoanAgreement(file, model);
                break;
            case "pledgeAgreement":
                insertPldgeAgreement(file, model);
                break;
            case "psBuilding":
                insertPledgeSubjectBuilding(file, model);
                break;
            case "psRoom":
                insertPledgeSubjectRoom(file, model);
                break;
            case "psLandOwnership":
                insertPledgeSubjectLandOwnershp(file, model);
                break;
            case "psLandLease":
                insertPledgeSubjectLandLease(file, model);
                break;
            case "psAuto":
                insertPledgeSubjectAuto(file, model);
                break;
            case "psEquipment":
                insertPledgeSubjectEquipment(file, model);
                break;
            case "psTBO":
                insertPledgeSubjectTbo(file, model);
                break;
            case "psSecurities":
                insertPledgeSubjectSecurites(file, model);
                break;
            case "psVessel":
                insertPledgeSubjectVessel(file, model);
                break;
            case "insurance":
                insertInsurance(file, model);
                break;
            case "encumbrance":
                insertEncumbrance(file, model);
                break;
            case "costHistory":
                nsertCostHistory(file, model);
                break;
            case "monitoring":
                insertMonitoring(file, model);
                break;
            case "clientManager":
                insertClientManager(file, model);
                break;
            default:
                throw new IllegalArgumentException("Bad request");
        }


        model.addAttribute("messageSuccess", true);
        model.addAttribute("whatUpload", whatUpload);

        return PAGE_UPDATE;
    }

    private void insertClientManager(@RequestParam("file") MultipartFile file, Model model) {
        List<ClientManagerDto> clientManagerDtoList = clientManagerService.insertClientManagerFromFile(file);
        model.addAttribute(clientManagerDtoList);
    }

    private void insertMonitoring(@RequestParam("file") MultipartFile file, Model model) {
        List<MonitoringDto> monitoringDtoList = monitoringService.insertMonitoringFromFile(file);
        model.addAttribute(monitoringDtoList);
    }

    private void nsertCostHistory(@RequestParam("file") MultipartFile file, Model model) {
        List<CostHistoryDto> costHistoryDtoList = costHistoryService.insertCostHistoryFromFile(file);
        model.addAttribute(costHistoryDtoList);
    }

    private void insertEncumbrance(@RequestParam("file") MultipartFile file, Model model) {
        List<EncumbranceDto> encumbranceDtoList = encumbranceService.insertEncumbranceFromFile(file);
        model.addAttribute(encumbranceDtoList);
    }

    private void insertInsurance(@RequestParam("file") MultipartFile file, Model model) {
        List<InsuranceDto> insuranceDtoList = insuranceService.insertInsuranceFromFile(file);
        model.addAttribute(insuranceDtoList);
    }

    private void insertPledgeSubjectVessel(@RequestParam("file") MultipartFile file, Model model) {
        List<PledgeSubjectDto> pledgeSubjectDtoList = pledgeSubjectService.insertPledgeSubjectVesselFromFile(file);
        model.addAttribute(pledgeSubjectDtoList);
    }

    private void insertPledgeSubjectSecurites(@RequestParam("file") MultipartFile file, Model model) {
        List<PledgeSubjectDto> pledgeSubjectDtoList = pledgeSubjectService.insertPledgeSubjectSecurityFromFile(file);
        model.addAttribute(pledgeSubjectDtoList);
    }

    private void insertPledgeSubjectTbo(@RequestParam("file") MultipartFile file, Model model) {
        List<PledgeSubjectDto> pledgeSubjectDtoList = pledgeSubjectService.insertPledgeSubjectTboFromFile(file);
        model.addAttribute(pledgeSubjectDtoList);
    }

    private void insertPledgeSubjectEquipment(@RequestParam("file") MultipartFile file, Model model) {
        List<PledgeSubjectDto> pledgeSubjectDtoList = pledgeSubjectService.insertPledgeSubjectEquipmentFromFile(file);
        model.addAttribute(pledgeSubjectDtoList);
    }

    private void insertPledgeSubjectAuto(@RequestParam("file") MultipartFile file, Model model) {
        List<PledgeSubjectDto> pledgeSubjectDtoList = pledgeSubjectService.insertPledgeSubjectAutoFromFile(file);
        model.addAttribute(pledgeSubjectDtoList);
    }

    private void insertPledgeSubjectLandLease(@RequestParam("file") MultipartFile file, Model model) {
        List<PledgeSubjectDto> pledgeSubjectDtoList = pledgeSubjectService.insertPledgeSubjectLandLeaseFromFile(file);
        model.addAttribute(pledgeSubjectDtoList);
    }

    private void insertPledgeSubjectLandOwnershp(@RequestParam("file") MultipartFile file, Model model) {
        List<PledgeSubjectDto> pledgeSubjectDtoList = pledgeSubjectService.insertPledgeSubjectLandOwnershipFromFile(file);
        model.addAttribute(pledgeSubjectDtoList);
    }

    private void insertPledgeSubjectRoom(@RequestParam("file") MultipartFile file, Model model) {
        List<PledgeSubjectDto> pledgeSubjectDtoList = pledgeSubjectService.insertPledgeSubjectRoomFromFile(file);
        model.addAttribute(pledgeSubjectDtoList);
    }

    private void insertPledgeSubjectBuilding(@RequestParam("file") MultipartFile file, Model model) {
        List<PledgeSubjectDto> pledgeSubjectDtoList = pledgeSubjectService.insertPledgeSubjectBuildingFromFile(file);
        model.addAttribute(pledgeSubjectDtoList);
    }

    private void insertPldgeAgreement(@RequestParam("file") MultipartFile file, Model model) {
        List<PledgeAgreementDto> pledgeAgreementDtoList = pledgeAgreementService.insertPledgeAgreementFromFile(file);
        model.addAttribute(pledgeAgreementDtoList);
    }

    private void insertLoanAgreement(@RequestParam("file") MultipartFile file, Model model) {
        List<LoanAgreementDto> loanAgreementDtoList = loanAgreementService.insertLoanAgreementFromFile(file);
        model.addAttribute(loanAgreementDtoList);
    }

    private void insertClientIndividual(@RequestParam("file") MultipartFile file, Model model) {
        List<ClientDto> clientDtoList = clientService.insertClientIndividualFromFile(file);
        model.addAttribute(clientDtoList);
    }

    private void insertClientLegalEntity(MultipartFile file, Model model) {
        List<ClientDto> clientDtoList = clientService.insertClientLegalEntityFromFile(file);
        model.addAttribute(clientDtoList);
    }
}
