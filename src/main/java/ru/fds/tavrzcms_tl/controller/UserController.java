package ru.fds.tavrzcms_tl.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.fds.tavrzcms_tl.dictionary.TypeOfPledgeAgreement;
import ru.fds.tavrzcms_tl.dto.EmployeeDto;
import ru.fds.tavrzcms_tl.service.EmployeeService;
import ru.fds.tavrzcms_tl.service.LoanAgreementService;
import ru.fds.tavrzcms_tl.service.PledgeAgreementService;
import ru.fds.tavrzcms_tl.service.UserDetailsServiceImpl;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    private final EmployeeService employeeService;
    private final PledgeAgreementService pledgeAgreementService;
    private final LoanAgreementService loanAgreementService;
    private final UserDetailsServiceImpl userDetailsService;

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
    private static final String ATTR_ERROR_MSG = "errorMessage";

    private static final String PAGE_HOME_EMPLOYEE = "home/home_employee";
    private static final String PAGE_HOME_EMPLOYEE_CHIEF = "home/home_employee_chief";
    private static final String PAGE_HOME_GUEST = "home/home_guest";
    private static final String PAGE_HOME_EMPLOYEE_FROM_CHIEF = "home/home_employee_from_chief";
    private static final String PAGE_EMPLOYEE_CARD_UPDATE = "user/employee_card_update";
    private static final String PAGE_UPDATE_PASSWORD = "user/card_update_pass";

    public UserController(EmployeeService employeeService,
                          PledgeAgreementService pledgeAgreementService,
                          LoanAgreementService loanAgreementService,
                          UserDetailsServiceImpl userDetailsService) {
        this.employeeService = employeeService;
        this.pledgeAgreementService = pledgeAgreementService;
        this.loanAgreementService = loanAgreementService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/")
    public String homePage(@AuthenticationPrincipal User user, Model model) {
        final GrantedAuthority authorityUser = new SimpleGrantedAuthority("ROLE_USER");
        final GrantedAuthority authorityChief = new SimpleGrantedAuthority("ROLE_USER_CHIEF");

        Collection<GrantedAuthority> grantedAuthorities = user.getAuthorities();

        if(grantedAuthorities.contains(authorityUser)){
            EmployeeDto employeeDto = employeeService.getEmployeeByUser(user);
            setEmployeeAttribute(employeeDto, model);

            return PAGE_HOME_EMPLOYEE;
        }else if(grantedAuthorities.contains(authorityChief)){
            EmployeeDto employeeDto = employeeService.getEmployeeByUser(user);
            setEmployeeAttribute(employeeDto, model);

            List<EmployeeDto> employeeListExcludeChief = employeeService.getEmployeesExcludeEmployee(employeeDto.getEmployeeId());
            Map<EmployeeDto, List<Integer>> employeeDtoMapExcludeChief = new HashMap<>(employeeListExcludeChief.size());
            employeeListExcludeChief.forEach(emp -> {
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
            });

            model.addAttribute(ATTR_EMPLOYEE_EXCLUDE_CHIEF, employeeDtoMapExcludeChief);

            return PAGE_HOME_EMPLOYEE_CHIEF;
        }else {
            model.addAttribute(ATTR_COUNT_PA, pledgeAgreementService.countOfAllCurrentPledgeAgreements());
            model.addAttribute(ATTR_COUNT_PERV_PA, pledgeAgreementService.countOfAllCurrentPledgeAgreements(TypeOfPledgeAgreement.PERV));
            model.addAttribute(ATTR_COUNT_POSL_PA, pledgeAgreementService.countOfAllCurrentPledgeAgreements(TypeOfPledgeAgreement.POSL));
            model.addAttribute(ATTR_COUNT_LA, loanAgreementService.countOfAllCurrentLoanAgreements());

            return PAGE_HOME_GUEST;
        }
    }

    @GetMapping("/employee")
    public String homeEmployeePage(@RequestParam("id") Long employeeId, Model model) {

            EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);
            setEmployeeAttribute(employeeDto, model);

        return PAGE_HOME_EMPLOYEE_FROM_CHIEF;
    }

    private void setEmployeeAttribute(EmployeeDto employeeDto, Model model){

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
    }

    @GetMapping("/employee/update/card")
    public String employeeUpdateCard(@RequestParam("employeeId") Long employeeId,
                                     Model model){

        EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);

        model.addAttribute(employeeDto);

        return PAGE_EMPLOYEE_CARD_UPDATE;
    }

    @PostMapping("/employee/update")
    public String employeeUpdate(@Valid EmployeeDto employeeDto,
                                 BindingResult bindingResult,
                                 Model model){
        if(bindingResult.hasErrors()){
            return PAGE_EMPLOYEE_CARD_UPDATE;
        }

        employeeDto = employeeService.updateEmployee(employeeDto);

        return homeEmployeePage(employeeDto.getEmployeeId(), model);
    }

    @GetMapping("/user/update/password/card")
    public String userPasswordUpdateCard(){
        return PAGE_UPDATE_PASSWORD;
    }

    @PostMapping("/user/update/password")
    public String userPasswordUpdate(@RequestParam("password") String password,
                                     @RequestParam("passwordConfirm") String passwordConfirm,
                                     @RequestParam("oldPassword") String oldPassword,
                                     Model model){
        User user = (User) userDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if(!userDetailsService.checkIfValidOldPassword(user, oldPassword)){
            model.addAttribute(ATTR_ERROR_MSG, "wrong old password");
            return PAGE_UPDATE_PASSWORD;
        }

        if(!password.equals(passwordConfirm)){
            model.addAttribute(ATTR_ERROR_MSG, "Password mismatch");
            return PAGE_UPDATE_PASSWORD;
        }

        userDetailsService.updatePassword(user, password);

        return homePage(user, model);
    }
}
