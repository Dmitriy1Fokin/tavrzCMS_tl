package ru.fds.tavrzcms_tl.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.fds.tavrzcms_tl.dictionary.TypeOfPledgeAgreement;
import ru.fds.tavrzcms_tl.dto.EmployeeDto;
import ru.fds.tavrzcms_tl.service.EmployeeService;
import ru.fds.tavrzcms_tl.service.LoanAgreementService;
import ru.fds.tavrzcms_tl.service.PledgeAgreementService;
import ru.fds.tavrzcms_tl.service.feign.TavrzcmsAPIFeignService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private final EmployeeService employeeService;
    private final PledgeAgreementService pledgeAgreementService;
    private final LoanAgreementService loanAgreementService;

    public MainController(EmployeeService employeeService,
                          PledgeAgreementService pledgeAgreementService,
                          LoanAgreementService loanAgreementService) {
        this.employeeService = employeeService;
        this.pledgeAgreementService = pledgeAgreementService;
        this.loanAgreementService = loanAgreementService;
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
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
            model.addAttribute("employeeDto", employeeDto);

            int countOfPA = pledgeAgreementService.countOfCurrentPledgeAgreementForEmployee(employeeDto.getEmployeeId());
            model.addAttribute("countOfAllPledgeAgreement", countOfPA);

            int countOfPervPA = pledgeAgreementService.countOfCurrentPledgeAgreementForEmployee(employeeDto.getEmployeeId(), TypeOfPledgeAgreement.PERV);
            model.addAttribute("countOfPervPledgeAgreements", countOfPervPA);

            int countOfPoslPA = pledgeAgreementService.countOfCurrentPledgeAgreementForEmployee(employeeDto.getEmployeeId(), TypeOfPledgeAgreement.POSL);
            model.addAttribute("countOfPoslPledgeAgreements", countOfPoslPA);

            int countOfLoanAgreements = loanAgreementService.countOfCurrentLoanAgreementsByEmployee(employeeDto.getEmployeeId());
            model.addAttribute("countOfLoanAgreements", countOfLoanAgreements);

            int countOfMonitoringNotDone = pledgeAgreementService.countOfMonitoringNotDone(employeeDto.getEmployeeId());
            model.addAttribute("countOfMonitoringNotDone", countOfMonitoringNotDone);

            int countOfMonitoringIsDone = pledgeAgreementService.countOfMonitoringIsDone(employeeDto.getEmployeeId());
            model.addAttribute("countOfMonitoringIsDone", countOfMonitoringIsDone);

            int countOfMonitoringOverdue = pledgeAgreementService.countOfMonitoringOverdue(employeeDto.getEmployeeId());
            model.addAttribute("countOfMonitoringOverdue", countOfMonitoringOverdue);

            int countOfConclusionNotDone = pledgeAgreementService.countOfConclusionNotDone(employeeDto.getEmployeeId());
            model.addAttribute("countOfConclusionNotDone", countOfConclusionNotDone);

            int countOfConclusionIsDone = pledgeAgreementService.countOfConclusionIsDone(employeeDto.getEmployeeId());
            model.addAttribute("countOfConclusionIsDone", countOfConclusionIsDone);

            int countOfConclusionOverdue = pledgeAgreementService.countOfConclusionOverdue(employeeDto.getEmployeeId());
            model.addAttribute("countOfConclusionOverdue", countOfConclusionOverdue);

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

                model.addAttribute("employeeDtoMapExcludeChief", employeeDtoMapExcludeChief);
            }
        }else if(grantedAuthorities.contains(authorityGuest) || grantedAuthorities.contains(authorityAdmin)){

            model.addAttribute("countOfAllPledgeAgreement", pledgeAgreementService.countOfAllCurrentPledgeAgreements());
            model.addAttribute("countOfPervPledgeAgreements", pledgeAgreementService.countOfAllCurrentPledgeAgreements(TypeOfPledgeAgreement.PERV));
            model.addAttribute("countOfPoslPledgeAgreements", pledgeAgreementService.countOfAllCurrentPledgeAgreements(TypeOfPledgeAgreement.POSL));
            model.addAttribute("countOfLoanAgreements", loanAgreementService.countOfAllCurrentLoanAgreements());
        }


        return "home";
    }
}
