package ru.fds.tavrzcms_tl.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import ru.fds.tavrzcms_tl.dto.EmployeeDto;
import ru.fds.tavrzcms_tl.exception.NotFoundException;
import ru.fds.tavrzcms_tl.repository.AppUserRepository;
import ru.fds.tavrzcms_tl.service.feign.TavrzcmsAPIFeignService;

import java.util.List;

@Service
public class EmployeeService {

    private final AppUserRepository appUserRepository;
    private final TavrzcmsAPIFeignService tavrzcmsAPIFeignService;

    public EmployeeService(AppUserRepository appUserRepository,
                           TavrzcmsAPIFeignService tavrzcmsAPIFeignService) {
        this.appUserRepository = appUserRepository;
        this.tavrzcmsAPIFeignService = tavrzcmsAPIFeignService;
    }

    public EmployeeDto getEmployeeByUser(User user){
        return appUserRepository.findByName(user.getUsername())
                .map(appUser -> tavrzcmsAPIFeignService.getEmployee(appUser.getEmployeeId()))
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public List<EmployeeDto> getEmployeesExcludeEmployee(Long employeeId){
        return tavrzcmsAPIFeignService.getEmployeesExcludeEmployee(employeeId);
    }

    public EmployeeDto getEmployeeByPledgeAgreement(Long pledgeAgreementId){
        return tavrzcmsAPIFeignService.getEmployeeByPledgeAgreement(pledgeAgreementId);
    }

    public EmployeeDto getEmployeeById(Long employeeId){
        return tavrzcmsAPIFeignService.getEmployee(employeeId);
    }

    public EmployeeDto getEmployeeByLoanAgreement(Long loanAgreementId){
        return tavrzcmsAPIFeignService.getEmployeeByLoanAgreement(loanAgreementId);
    }

    public List<EmployeeDto> getAllEmployees(){
        return tavrzcmsAPIFeignService.getAllEmployees();
    }
}
