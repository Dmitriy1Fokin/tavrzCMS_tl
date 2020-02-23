package ru.fds.tavrzcms_tl.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.fds.tavrzcms_tl.dto.AuditResultDto;
import ru.fds.tavrzcms_tl.dto.ClientDto;
import ru.fds.tavrzcms_tl.dto.EmployeeDto;
import ru.fds.tavrzcms_tl.dto.LoanAgreementDto;
import ru.fds.tavrzcms_tl.dto.PledgeAgreementDto;
import ru.fds.tavrzcms_tl.wrapper.PledgeAgreementDtoWrapper;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@FeignClient("${feign_tavrz_cms_api.name}")
public interface TavrzcmsAPIFeignService {

    @GetMapping("/audit/loan_agreement")
    Collection<AuditResultDto> getAuditResultAboutLoanAgreement(@RequestParam("loanAgreementId") Long loanAgreementId);

    @GetMapping("/audit/pledge_agreement")
    Collection<AuditResultDto> getAuditResultAboutPledgeAgreement(@RequestParam("pledgeAgreementId") Long pledgeAgreementId);

    @GetMapping("/audit/pledge_subject")
    Collection<AuditResultDto> getAuditResultAboutPledgeSubject(@RequestParam("pledgeSubjectId") Long pledgeSubjectId);

    @GetMapping("/audit")
    Collection<AuditResultDto> getAuditResult(Pageable pageable);

    @GetMapping("/audit/ignore")
    AuditResultDto setIgnore(@RequestParam("auditResultId") String auditResultId);

    @GetMapping("/audit/actual")
    AuditResultDto setActual(@RequestParam("auditResultId") String auditResultId);



    @GetMapping(value = "/employee/{employeeId}")
    EmployeeDto getEmployee(@PathVariable("employeeId") Long employeeId);

    @GetMapping("/employee/all")
    List<EmployeeDto> getAllEmployees();

    @GetMapping("/employee/excludeEmployee")
    List<EmployeeDto> getEmployeesExcludeEmployee(@RequestParam("employeeId") Long employeeId);

    @GetMapping("/employee/loan_agreement")
    EmployeeDto getEmployeeByLoanAgreement(@RequestParam("loanAgreementId") Long loanAgreementId);

    @GetMapping("/employee/pledge_agreement")
    EmployeeDto getEmployeeByPledgeAgreement(@RequestParam("pledgeAgreementId") Long pledgeAgreementId);

    @PostMapping("/employee/insert")
    EmployeeDto insertEmployee(@Valid @RequestBody EmployeeDto employeeDto);

    @PutMapping("/employee/update")
    EmployeeDto updateEmployee(@Valid @RequestBody EmployeeDto employeeDto);



    @GetMapping("/loan_agreement/{loanAgreementId}")
    LoanAgreementDto getLoanAgreement(@PathVariable("loanAgreementId") Long loanAgreementId);

    @GetMapping("/loan_agreement/current")
    List<LoanAgreementDto> getLoanAgreements(Pageable pageable);

    @GetMapping("/loan_agreement/current/count")
    Integer getCountOfLoanAgreements();

    @GetMapping("/loan_agreement/current_la_for_client")
    List<LoanAgreementDto> getCurrentLoanAgreementsByClient(@RequestParam("clientId") Long clientId);

    @GetMapping("/loan_agreement/closed_la_for_client")
    List<LoanAgreementDto> getClosedLoanAgreementsByClient(@RequestParam("clientId") Long clientId);

    @GetMapping("/loan_agreement/current_la_for_employee")
    List<LoanAgreementDto> getCurrentLoanAgreementByEmployee(Pageable pageable,
                                                                    @RequestParam("employeeId") Long employeeId);

    @GetMapping("/loan_agreement/current_la_for_employee/count")
    Integer getCountOfCurrentLoanAgreementByEmployee(@RequestParam("employeeId") Long employeeId);

    @GetMapping("/loan_agreement/current_la_for_pledge_agreement")
    List<LoanAgreementDto> getCurrentLoanAgreementByPledgeAgreement(@RequestParam("pledgeAgreementId") Long pledgeAgreementId);

    @GetMapping("/loan_agreement/closed_la_for_pledge_agreement")
    List<LoanAgreementDto> getClosedLoanAgreementByPledgeAgreement(@RequestParam("pledgeAgreementId") Long pledgeAgreementId);

    @GetMapping("/loan_agreement/search")
    List<LoanAgreementDto> getLoanAgreementBySearchCriteria(@RequestParam Map<String, String> reqParam, Pageable pageable);

    @PostMapping("/loan_agreement/insert")
    LoanAgreementDto insertLoanAgreement(@Valid @RequestBody LoanAgreementDto loanAgreementDto);

    @PutMapping("/loan_agreement/update")
    LoanAgreementDto updateLoanAgreement(@Valid @RequestBody LoanAgreementDto loanAgreementDto);

    @PostMapping("/loan_agreement/insert_from_file")
    List<LoanAgreementDto> insertLoanAgreementFromFile(@RequestParam("file") MultipartFile file);

    @PutMapping("/loan_agreement/update_from_file")
    List<LoanAgreementDto> updateLoanAgreementFromFile(@RequestParam("file") MultipartFile file);



    @GetMapping("/pledge_agreement/{pledgeAgreementId}")
    PledgeAgreementDto getPledgeAgreement(@PathVariable("pledgeAgreementId") Long pledgeAgreementId);

    @GetMapping("/pledge_agreement/current")
    List<PledgeAgreementDto> getCurrentPledgeAgreements(Pageable pageable);

    @GetMapping("/pledge_agreement/current/count")
    Integer getCountOfCurrentPledgeAgreements();

    @GetMapping("/pledge_agreement/current/perv")
    List<PledgeAgreementDto> getCurrentPervPledgeAgreements(Pageable pageable);

    @GetMapping("/pledge_agreement/current/count/perv")
    Integer getCountOfCurrentPervPledgeAgreements();

    @GetMapping("/pledge_agreement/current/posl")
    List<PledgeAgreementDto> getCurrentPoslPledgeAgreements(Pageable pageable);

    @GetMapping("/pledge_agreement/current/count/posl")
    Integer getCountOfCurrentPoslPledgeAgreements();

    @GetMapping("/pledge_agreement/current_pa_for_client")
    List<PledgeAgreementDto> getCurrentPledgeAgreementsByClient(@RequestParam("clientId") Long clientId);

    @GetMapping("/pledge_agreement/closed_pa_for_client")
    List<PledgeAgreementDto> getClosedPledgeAgreementsByClient(@RequestParam("clientId") Long clientId);

    @GetMapping("/pledge_agreement/with_conclusion_not_done")
    List<PledgeAgreementDto> getPledgeAgreementsWithConclusionNotDone(@RequestParam("employeeId") Long employeeId);

    @GetMapping("/pledge_agreement/with_conclusion_not_done/count")
    Integer getCountOfPledgeAgreementsWithConclusionNotDone(@RequestParam("employeeId") Long employeeId);

    @GetMapping("/pledge_agreement/with_conclusion_is_done")
    List<PledgeAgreementDto> getPledgeAgreementsWithConclusionIsDone(@RequestParam("employeeId") Long employeeId);

    @GetMapping("/pledge_agreement/with_conclusion_is_done/count")
    Integer getCountOfPledgeAgreementsWithConclusionIsDone(@RequestParam("employeeId") Long employeeId);

    @GetMapping("/pledge_agreement/with_conclusion_overdue")
    List<PledgeAgreementDto> getPledgeAgreementsWithConclusionOverdue(@RequestParam("employeeId") Long employeeId);

    @GetMapping("/pledge_agreement/with_conclusion_overdue/count")
    Integer getCountOfPledgeAgreementsWithConclusionOverdue(@RequestParam("employeeId") Long employeeId);

    @GetMapping("/pledge_agreement/with_monitoring_not_done")
    List<PledgeAgreementDto> getPledgeAgreementsWithMonitoringNotDone(@RequestParam("employeeId") Long employeeId);

    @GetMapping("/pledge_agreement/with_monitoring_not_done/count")
    Integer getCountOfPledgeAgreementsWithMonitoringNotDone(@RequestParam("employeeId") Long employeeId);

    @GetMapping("/pledge_agreement/with_monitoring_is_done")
    List<PledgeAgreementDto> getPledgeAgreementsWithMonitoringIsDone(@RequestParam("employeeId") Long employeeId);

    @GetMapping("/pledge_agreement/with_monitoring_is_done/count")
    Integer getCountOfPledgeAgreementsWithMonitoringIsDone(@RequestParam("employeeId") Long employeeId);

    @GetMapping("/pledge_agreement/with_monitoring_overdue")
    List<PledgeAgreementDto> getPledgeAgreementsWithMonitoringOverdue(@RequestParam("employeeId") Long employeeId);

    @GetMapping("/pledge_agreement/with_monitoring_overdue/count")
    Integer getCountOfPledgeAgreementsWithMonitoringOverdue(@RequestParam("employeeId") Long employeeId);

    @GetMapping("/pledge_agreement/current_pa_for_loan_agreement")
    List<PledgeAgreementDto> getCurrentPledgeAgreementsByLoanAgreement(@RequestParam("loanAgreementId") Long loanAgreementId);

    @GetMapping("/pledge_agreement/closed_pa_for_loan_agreement")
    List<PledgeAgreementDto> getClosedPledgeAgreementsByLoanAgreement(@RequestParam("loanAgreementId") Long loanAgreementId);

    @GetMapping("/pledge_agreement/search_by_num")
    List<PledgeAgreementDto> getPledgeAgreementsByNum(@RequestParam("numPA") String numPA, Pageable pageable);

    @GetMapping("/pledge_agreement/current_pa_for_employee")
    List<PledgeAgreementDto> getCurrentPledgeAgreementByEmployee(@RequestParam("employeeId") Long employeeId, Pageable pageable);

    @GetMapping("/pledge_agreement/current_pa_for_employee/count")
    Integer getCountOfCurrentPledgeAgreementByEmployee(@RequestParam("employeeId") Long employeeId);

    @GetMapping("/current_pa_for_employee/perv")
    List<PledgeAgreementDto> getCurrentPervPledgeAgreementByEmployee(@RequestParam("employeeId") Long employeeId, Pageable pageable);

    @GetMapping("/pledge_agreement/current_pa_for_employee/count/perv")
    Integer getCountOfCurrentPervPledgeAgreementByEmployee(@RequestParam("employeeId") Long employeeId);

    @GetMapping("/pledge_agreement/current_pa_for_employee/posl")
    List<PledgeAgreementDto> getCurrentPoslPledgeAgreementByEmployee(@RequestParam("employeeId") Long employeeId, Pageable pageable);

    @GetMapping("/pledge_agreement/current_pa_for_employee/count/posl")
    Integer getCountOfCurrentPoslPledgeAgreementByEmployee(@RequestParam("employeeId") Long employeeId);

    @GetMapping("/pledge_agreement/pledge_subject")
    List<PledgeAgreementDto> getPledgeAgreementsByPledgeSubjects(@RequestParam("pledgeSubjectId") Long pledgeSubjectId);

    @GetMapping("/pledge_agreement/search")
    List<PledgeAgreementDto> getPledgeAgreementBySearchCriteria(@RequestParam Map<String, String> reqParam, Pageable pageable);

    @PostMapping("/pledge_agreement/insert")
    PledgeAgreementDto insertPledgeAgreement(@Valid @RequestBody PledgeAgreementDtoWrapper pledgeAgreementDtoWrapper);

    @PutMapping("/pledge_agreement/update")
    PledgeAgreementDto updatePledgeAgreement(@Valid @RequestBody PledgeAgreementDtoWrapper pledgeAgreementDtoWrapper);

    @PostMapping("/pledge_agreement/insert/file")
    List<PledgeAgreementDto> insertPledgeAgreementFromFile(@RequestParam("file") MultipartFile file);

    @PutMapping("/pledge_agreement/update/file")
    List<PledgeAgreementDto> updatePledgeAgreementFromFile(@RequestParam("file") MultipartFile file);

    @PutMapping("/pledge_agreementupdate/withdraw_pledge_subject")
    PledgeAgreementDto withdrawPledgeSubjectFromPledgeAgreement(@RequestParam("pledgeSubjectId") Long pledgeSubjectId,
                                                                       @RequestParam("pledgeAgreementId") Long pledgeAgreementId);

    @PutMapping("/pledge_agreement/update/insert_current_pledge_subject")
    PledgeAgreementDto insertCurrentPledgeSubjectInPledgeAgreement(@RequestParam("pledgeSubjectsIdArray") List<Long> pledgeSubjectsIdArray,
                                                                          @RequestParam("pledgeAgreementId") Long pledgeAgreementId);



    @GetMapping("/client")
    List<ClientDto> getAllClients(Pageable pageable);

    @GetMapping("/client/{clientId}")
    ClientDto getClient(@PathVariable("clientId") Long clientId);

    @GetMapping("/client/employee")
    List<ClientDto> getClientsByEmployee(@RequestParam("employeeId") Long employeeId);

    @GetMapping("/client/client_manager")
    List<ClientDto> getClientsByClientManager(@RequestParam("clientManagerId") Long clientManagerId);

    @GetMapping("/client/search")
    List<ClientDto> getClientBySearchCriteria(@RequestParam Map<String, String> reqParam);

    @PostMapping("/client/insert")
    ClientDto insertClient(@Valid @RequestBody ClientDto clientDto);

    @PutMapping("/client/update")
    ClientDto updateClient(@Valid @RequestBody ClientDto clientDto);

    @PostMapping("/client/insert_from_file/client_legal_entity")
    List<ClientDto> insertClientLegalEntityFromFile(@RequestParam("file") MultipartFile file);

    @PostMapping("/client/insert_from_file/client_individual")
    List<ClientDto> insertClientIndividualFromFile(@RequestParam("file") MultipartFile file);

    @PutMapping("/client/update_from_file/client_legal_entity")
    List<ClientDto> updateClientLegalEntityFromFile(@RequestParam("file") MultipartFile file);

    @PutMapping("/client/update_from_file/client_individual")
    List<ClientDto> updateClientIndividualFromFile(@RequestParam("file") MultipartFile file);

}
