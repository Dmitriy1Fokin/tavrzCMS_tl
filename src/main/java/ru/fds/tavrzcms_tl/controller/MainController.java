package ru.fds.tavrzcms_tl.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.fds.tavrzcms_tl.dictionary.TypeOfPledgeAgreement;
import ru.fds.tavrzcms_tl.dto.ClientDto;
import ru.fds.tavrzcms_tl.dto.ClientManagerDto;
import ru.fds.tavrzcms_tl.dto.CostHistoryDto;
import ru.fds.tavrzcms_tl.dto.EmployeeDto;
import ru.fds.tavrzcms_tl.dto.EncumbranceDto;
import ru.fds.tavrzcms_tl.dto.InsuranceDto;
import ru.fds.tavrzcms_tl.dto.LoanAgreementDto;
import ru.fds.tavrzcms_tl.dto.MonitoringDto;
import ru.fds.tavrzcms_tl.dto.PledgeAgreementDto;
import ru.fds.tavrzcms_tl.dto.PledgeSubjectDto;
import ru.fds.tavrzcms_tl.service.ClientManagerService;
import ru.fds.tavrzcms_tl.service.ClientService;
import ru.fds.tavrzcms_tl.service.CostHistoryService;
import ru.fds.tavrzcms_tl.service.EmployeeService;
import ru.fds.tavrzcms_tl.service.EncumbranceService;
import ru.fds.tavrzcms_tl.service.InsuranceService;
import ru.fds.tavrzcms_tl.service.LoanAgreementService;
import ru.fds.tavrzcms_tl.service.MonitoringService;
import ru.fds.tavrzcms_tl.service.PledgeAgreementService;
import ru.fds.tavrzcms_tl.service.PledgeSubjectService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class MainController {

    private final EmployeeService employeeService;
    private final PledgeAgreementService pledgeAgreementService;
    private final LoanAgreementService loanAgreementService;
    private final ClientService clientService;
    private final PledgeSubjectService pledgeSubjectService;
    private final InsuranceService insuranceService;
    private final EncumbranceService encumbranceService;
    private final CostHistoryService costHistoryService;
    private final MonitoringService monitoringService;;
    private final ClientManagerService clientManagerService;

    private static final String ATTR_EMPLOYEE = "employeeDto";
    private static final String ATTR_COUNT_PA = "countOfAllPledgeAgreement";
    private static final String ATTR_COUNT_PERV_PA = "countOfPervPledgeAgreements";
    private static final String ATTR_COUNT_POSL_PA = "countOfPoslPledgeAgreements";
    private static final String ATTR_COUNT_LA = "countOfLoanAgreements";
    private static final String ATTR_COUNT_MONITORING_NOT_DONE = "countOfMonitoringNotDone";
    private static final String ATTR_COUNT_MONITORING_IS_DONE = "countOfMonitoringIsDone";
    private static final String ATTR_COUNT_MONITORING_OVERDUE = "countOfMonitoringOverdue";
    private static final String ATTR_COUNT_CONCLUSION_NOT_DONE = "countOfConclusionNotDone";
    private static final String ATTR_COUNT_CONCLUSION_IS_DONE = "countOfConclusionIsDone";
    private static final String ATTR_COUNT_CONCLUSION_OVERDUE = "countOfConclusionOverdue";
    private static final String ATTR_EMPLOYEE_EXCLUDE_CHIEF = "employeeDtoMapExcludeChief";
    private static final String ATTR_MESSAGE_ERROR = "messageError";
    private static final String PAGE_LOGIN = "login";
    private static final String PAGE_HOME = "home";
    private static final String PAGE_UPDATE = "update";

    public MainController(EmployeeService employeeService,
                          PledgeAgreementService pledgeAgreementService,
                          LoanAgreementService loanAgreementService,
                          ClientService clientService,
                          PledgeSubjectService pledgeSubjectService,
                          InsuranceService insuranceService,
                          EncumbranceService encumbranceService,
                          CostHistoryService costHistoryService,
                          MonitoringService monitoringService,
                          ClientManagerService clientManagerService) {
        this.employeeService = employeeService;
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

    @GetMapping("/login")
    public String login() {
        return PAGE_LOGIN;
    }

    @GetMapping("/")
    public String homePage(@AuthenticationPrincipal User user, Model model) {
        final GrantedAuthority authorityUser = new SimpleGrantedAuthority("ROLE_USER");
        final GrantedAuthority authorityChief = new SimpleGrantedAuthority("ROLE_USER_CHIEF");
        final GrantedAuthority authorityGuest = new SimpleGrantedAuthority("ROLE_GUEST");
        final GrantedAuthority authorityAdmin = new SimpleGrantedAuthority("ROLE_ADMIN");

        Collection<GrantedAuthority> grantedAuthorities = user.getAuthorities();
        if(grantedAuthorities.contains(authorityUser) || grantedAuthorities.contains(authorityChief)){
            EmployeeDto employeeDto = employeeService.getEmployeeByUser(user);
            model.addAttribute(ATTR_EMPLOYEE, employeeDto);

            int countOfPA = pledgeAgreementService.countOfCurrentPledgeAgreementForEmployee(employeeDto.getEmployeeId());
            model.addAttribute(ATTR_COUNT_PA, countOfPA);

            int countOfPervPA = pledgeAgreementService.countOfCurrentPledgeAgreementForEmployee(employeeDto.getEmployeeId(), TypeOfPledgeAgreement.PERV);
            model.addAttribute(ATTR_COUNT_PERV_PA, countOfPervPA);

            int countOfPoslPA = pledgeAgreementService.countOfCurrentPledgeAgreementForEmployee(employeeDto.getEmployeeId(), TypeOfPledgeAgreement.POSL);
            model.addAttribute(ATTR_COUNT_POSL_PA, countOfPoslPA);

            int countOfLoanAgreements = loanAgreementService.countOfCurrentLoanAgreementsByEmployee(employeeDto.getEmployeeId());
            model.addAttribute(ATTR_COUNT_LA, countOfLoanAgreements);

            int countOfMonitoringNotDone = pledgeAgreementService.countOfMonitoringNotDone(employeeDto.getEmployeeId());
            model.addAttribute(ATTR_COUNT_MONITORING_NOT_DONE, countOfMonitoringNotDone);

            int countOfMonitoringIsDone = pledgeAgreementService.countOfMonitoringIsDone(employeeDto.getEmployeeId());
            model.addAttribute(ATTR_COUNT_MONITORING_IS_DONE, countOfMonitoringIsDone);

            int countOfMonitoringOverdue = pledgeAgreementService.countOfMonitoringOverdue(employeeDto.getEmployeeId());
            model.addAttribute(ATTR_COUNT_MONITORING_OVERDUE, countOfMonitoringOverdue);

            int countOfConclusionNotDone = pledgeAgreementService.countOfConclusionNotDone(employeeDto.getEmployeeId());
            model.addAttribute(ATTR_COUNT_CONCLUSION_NOT_DONE, countOfConclusionNotDone);

            int countOfConclusionIsDone = pledgeAgreementService.countOfConclusionIsDone(employeeDto.getEmployeeId());
            model.addAttribute(ATTR_COUNT_CONCLUSION_IS_DONE, countOfConclusionIsDone);

            int countOfConclusionOverdue = pledgeAgreementService.countOfConclusionOverdue(employeeDto.getEmployeeId());
            model.addAttribute(ATTR_COUNT_CONCLUSION_OVERDUE, countOfConclusionOverdue);

            if(grantedAuthorities.contains(authorityChief)){
                Map<EmployeeDto, List<Integer>> employeeDtoMapExcludeChief = new HashMap<>();
                List<EmployeeDto> employeeListExcludeChief = employeeService.getEmployeesExcludeEmployee(employeeDto.getEmployeeId());
                for(EmployeeDto emp : employeeListExcludeChief){
                    List<Integer> integerList = new ArrayList<>();
                    integerList.add(pledgeAgreementService.countOfCurrentPledgeAgreementForEmployee(emp.getEmployeeId()));
                    integerList.add(pledgeAgreementService.countOfCurrentPledgeAgreementForEmployee(emp.getEmployeeId(), TypeOfPledgeAgreement.PERV));
                    integerList.add(pledgeAgreementService.countOfCurrentPledgeAgreementForEmployee(emp.getEmployeeId(), TypeOfPledgeAgreement.POSL));
                    integerList.add(loanAgreementService.countOfCurrentLoanAgreementsByEmployee(emp.getEmployeeId()));
                    integerList.add(pledgeAgreementService.countOfMonitoringNotDone(emp.getEmployeeId()));
                    integerList.add(pledgeAgreementService.countOfMonitoringIsDone(emp.getEmployeeId()));
                    integerList.add(pledgeAgreementService.countOfMonitoringOverdue(emp.getEmployeeId()));
                    integerList.add(pledgeAgreementService.countOfConclusionNotDone(emp.getEmployeeId()));
                    integerList.add(pledgeAgreementService.countOfConclusionIsDone(emp.getEmployeeId()));
                    integerList.add(pledgeAgreementService.countOfConclusionOverdue(emp.getEmployeeId()));

                    employeeDtoMapExcludeChief.put(emp, integerList);
                }

                model.addAttribute(ATTR_EMPLOYEE_EXCLUDE_CHIEF, employeeDtoMapExcludeChief);
            }
        }else if(grantedAuthorities.contains(authorityGuest) || grantedAuthorities.contains(authorityAdmin)){

            model.addAttribute(ATTR_COUNT_PA, pledgeAgreementService.countOfAllCurrentPledgeAgreements());
            model.addAttribute(ATTR_COUNT_PERV_PA, pledgeAgreementService.countOfAllCurrentPledgeAgreements(TypeOfPledgeAgreement.PERV));
            model.addAttribute(ATTR_COUNT_POSL_PA, pledgeAgreementService.countOfAllCurrentPledgeAgreements(TypeOfPledgeAgreement.POSL));
            model.addAttribute(ATTR_COUNT_LA, loanAgreementService.countOfAllCurrentLoanAgreements());
        }

        return PAGE_HOME;
    }

    @GetMapping("/employee")
    public String homeEmployeePage(@RequestParam("id") Long employeeId, Model model) {


            EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);
            model.addAttribute(ATTR_EMPLOYEE, employeeDto);

            int countOfPA = pledgeAgreementService.countOfCurrentPledgeAgreementForEmployee(employeeDto.getEmployeeId());
            model.addAttribute(ATTR_COUNT_PA, countOfPA);

            int countOfPervPA = pledgeAgreementService.countOfCurrentPledgeAgreementForEmployee(employeeDto.getEmployeeId(), TypeOfPledgeAgreement.PERV);
            model.addAttribute(ATTR_COUNT_PERV_PA, countOfPervPA);

            int countOfPoslPA = pledgeAgreementService.countOfCurrentPledgeAgreementForEmployee(employeeDto.getEmployeeId(), TypeOfPledgeAgreement.POSL);
            model.addAttribute(ATTR_COUNT_POSL_PA, countOfPoslPA);

            int countOfLoanAgreements = loanAgreementService.countOfCurrentLoanAgreementsByEmployee(employeeDto.getEmployeeId());
            model.addAttribute(ATTR_COUNT_LA, countOfLoanAgreements);

            int countOfMonitoringNotDone = pledgeAgreementService.countOfMonitoringNotDone(employeeDto.getEmployeeId());
            model.addAttribute(ATTR_COUNT_MONITORING_NOT_DONE, countOfMonitoringNotDone);

            int countOfMonitoringIsDone = pledgeAgreementService.countOfMonitoringIsDone(employeeDto.getEmployeeId());
            model.addAttribute(ATTR_COUNT_MONITORING_IS_DONE, countOfMonitoringIsDone);

            int countOfMonitoringOverdue = pledgeAgreementService.countOfMonitoringOverdue(employeeDto.getEmployeeId());
            model.addAttribute(ATTR_COUNT_MONITORING_OVERDUE, countOfMonitoringOverdue);

            int countOfConclusionNotDone = pledgeAgreementService.countOfConclusionNotDone(employeeDto.getEmployeeId());
            model.addAttribute(ATTR_COUNT_CONCLUSION_NOT_DONE, countOfConclusionNotDone);

            int countOfConclusionIsDone = pledgeAgreementService.countOfConclusionIsDone(employeeDto.getEmployeeId());
            model.addAttribute(ATTR_COUNT_CONCLUSION_IS_DONE, countOfConclusionIsDone);

            int countOfConclusionOverdue = pledgeAgreementService.countOfConclusionOverdue(employeeDto.getEmployeeId());
            model.addAttribute(ATTR_COUNT_CONCLUSION_OVERDUE, countOfConclusionOverdue);


        return PAGE_HOME;
    }

    @GetMapping("/update")
    public String updatePage(){
        return PAGE_UPDATE;
    }

    @PostMapping("/insert_from_file")
    public String importClientLegalEntityFromExcel(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("whatUpload") String whatUpload,
                                                   Model model){
        if(whatUpload.equals("clientLegalEntity")){
            List<ClientDto> clientDtoList = clientService.insertClientLegalEntityFromFile(file);
            model.addAttribute(clientDtoList);
        }else if(whatUpload.equals("clientIndividual")){
            List<ClientDto> clientDtoList = clientService.insertClientIndividualFromFile(file);
            model.addAttribute(clientDtoList);
        }else if(whatUpload.equals("loanAgreement")){
            List<LoanAgreementDto> loanAgreementDtoList = loanAgreementService.insertLoanAgreementFromFile(file);
            model.addAttribute(loanAgreementDtoList);
        }else if(whatUpload.equals("pledgeAgreement")){
            List<PledgeAgreementDto> pledgeAgreementDtoList = pledgeAgreementService.insertPledgeAgreementFromFile(file);
            model.addAttribute(pledgeAgreementDtoList);
        }else if(whatUpload.equals("psBuilding")){
            List<PledgeSubjectDto> pledgeSubjectDtoList = pledgeSubjectService.insertPledgeSubjectBuildingFromFile(file);
            model.addAttribute(pledgeSubjectDtoList);
        }else if(whatUpload.equals("psRoom")){
            List<PledgeSubjectDto> pledgeSubjectDtoList = pledgeSubjectService.insertPledgeSubjectRoomFromFile(file);
            model.addAttribute(pledgeSubjectDtoList);
        }else if(whatUpload.equals("psLandOwnership")){
            List<PledgeSubjectDto> pledgeSubjectDtoList = pledgeSubjectService.insertPledgeSubjectLandOwnershipFromFile(file);
            model.addAttribute(pledgeSubjectDtoList);
        }else if(whatUpload.equals("psLandLease")){
            List<PledgeSubjectDto> pledgeSubjectDtoList = pledgeSubjectService.insertPledgeSubjectLandLeaseFromFile(file);
            model.addAttribute(pledgeSubjectDtoList);
        }else if(whatUpload.equals("psAuto")){
            List<PledgeSubjectDto> pledgeSubjectDtoList = pledgeSubjectService.insertPledgeSubjectAutoFromFile(file);
            model.addAttribute(pledgeSubjectDtoList);
        }else if(whatUpload.equals("psEquipment")){
            List<PledgeSubjectDto> pledgeSubjectDtoList = pledgeSubjectService.insertPledgeSubjectEquipmentFromFile(file);
            model.addAttribute(pledgeSubjectDtoList);
        }else if(whatUpload.equals("psTBO")){
            List<PledgeSubjectDto> pledgeSubjectDtoList = pledgeSubjectService.insertPledgeSubjectTboFromFile(file);
            model.addAttribute(pledgeSubjectDtoList);
        }else if(whatUpload.equals("psSecurities")){
            List<PledgeSubjectDto> pledgeSubjectDtoList = pledgeSubjectService.insertPledgeSubjectSecurityFromFile(file);
            model.addAttribute(pledgeSubjectDtoList);
        }else if(whatUpload.equals("psVessel")){
            List<PledgeSubjectDto> pledgeSubjectDtoList = pledgeSubjectService.insertPledgeSubjectVesselFromFile(file);
            model.addAttribute(pledgeSubjectDtoList);
        }else if(whatUpload.equals("insurance")){
            List<InsuranceDto> insuranceDtoList = insuranceService.insertInsuranseFromFile(file);
            model.addAttribute(insuranceDtoList);
        }else if(whatUpload.equals("encumbrance")){
            List<EncumbranceDto> encumbranceDtoList = encumbranceService.insertEncumbranceFromFile(file);
            model.addAttribute(encumbranceDtoList);
        }else if(whatUpload.equals("costHistory")){
            List<CostHistoryDto> costHistoryDtoList = costHistoryService.insertCostHistoryFromFile(file);
            model.addAttribute(costHistoryDtoList);
        }else if(whatUpload.equals("monitoring")){
            List<MonitoringDto> monitoringDtoList = monitoringService.insertMonitoringFromFile(file);
            model.addAttribute(monitoringDtoList);
        }else if(whatUpload.equals("clientManager")){
            List<ClientManagerDto> clientManagerDtoList = clientManagerService.insertClientManagerFromFile(file);
            model.addAttribute(clientManagerDtoList);
        }else {
            throw new IllegalArgumentException("Bad request");
        }


            model.addAttribute("messageSuccess", true);
            model.addAttribute("whatUpload", whatUpload);

            return PAGE_UPDATE;
    }


}
