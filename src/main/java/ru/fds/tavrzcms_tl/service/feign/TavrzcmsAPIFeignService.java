package ru.fds.tavrzcms_tl.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import ru.fds.tavrzcms_tl.dto.AuditResultDto;
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
import ru.fds.tavrzcms_tl.wrapper.PledgeAgreementDtoWrapper;
import ru.fds.tavrzcms_tl.wrapper.PledgeSubjectDtoNewWrapper;
import ru.fds.tavrzcms_tl.wrapper.PledgeSubjectUpdateDtoWrapper;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@FeignClient("${feign_tavrz_cms_api.name}")
public interface TavrzcmsAPIFeignService {

    @GetMapping("/audit/loan_agreement")
    Collection<AuditResultDto> getAuditResultAboutLoanAgreement(@RequestParam("loanAgreementId") Long loanAgreementId);

    @GetMapping("/audit/loan_agreement/actual")
    Collection<AuditResultDto> getActualAuditResultAboutLoanAgreement(@RequestParam("loanAgreementId") Long loanAgreementId);

    @GetMapping("/audit/loan_agreement/ignore")
    Collection<AuditResultDto> getIgnoreAuditResultAboutLoanAgreement(@RequestParam("loanAgreementId") Long loanAgreementId);

    @GetMapping("/audit/pledge_agreement")
    Collection<AuditResultDto> getAuditResultAboutPledgeAgreement(@RequestParam("pledgeAgreementId") Long pledgeAgreementId);

    @GetMapping("/audit/pledge_agreement/actual")
    Collection<AuditResultDto> getActualAuditResultAboutPledgeAgreement(@RequestParam("pledgeAgreementId") Long pledgeAgreementId);

    @GetMapping("/audit/pledge_agreement/ignore")
    Collection<AuditResultDto> getIgnoreAuditResultAboutPledgeAgreement(@RequestParam("pledgeAgreementId") Long pledgeAgreementId);

    @GetMapping("/audit/pledge_subject")
    Collection<AuditResultDto> getAuditResultAboutPledgeSubject(@RequestParam("pledgeSubjectId") Long pledgeSubjectId);

    @GetMapping("/audit/pledge_subject/actual")
    Collection<AuditResultDto> getActualAuditResultAboutPledgeSubject(@RequestParam("pledgeSubjectId") Long pledgeSubjectId);

    @GetMapping("/audit/pledge_subject/ignore")
    Collection<AuditResultDto> getIgnoreAuditResultAboutPledgeSubject(@RequestParam("pledgeSubjectId") Long pledgeSubjectId);

    @GetMapping("/audit")
    Collection<AuditResultDto> getAuditResult(Pageable pageable);

    @GetMapping("/audit/executeAudit")
    void executeAudit();

    @GetMapping("/audit/ignore")
    AuditResultDto setIgnore(@RequestParam("auditResultId") String auditResultId);

    @GetMapping("/audit/actual")
    AuditResultDto setActual(@RequestParam("auditResultId") String auditResultId);



    @GetMapping("/employee/{employeeId}")
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

    @GetMapping("/loan_agreement/num")
    List<LoanAgreementDto> getLoanAgreementsByNumLA(@RequestParam("numLA") String numLA);

    @GetMapping("/loan_agreement/current_la_for_employee")
    List<LoanAgreementDto> getCurrentLoanAgreementByEmployee(Pageable pageable,
                                                             @RequestParam("employeeId") Long employeeId);

    @GetMapping("/loan_agreement/current_la_for_employee/count")
    Integer getCountOfCurrentLoanAgreementByEmployee(@RequestParam("employeeId") Long employeeId);

    @GetMapping("/loan_agreement/current_la_for_pledge_agreement")
    List<LoanAgreementDto> getCurrentLoanAgreementByPledgeAgreement(@RequestParam("pledgeAgreementId") Long pledgeAgreementId);

    @GetMapping("/loan_agreement/closed_la_for_pledge_agreement")
    List<LoanAgreementDto> getClosedLoanAgreementByPledgeAgreement(@RequestParam("pledgeAgreementId") Long pledgeAgreementId);

    @GetMapping("/loan_agreement/all_la_for_pledge_agreements")
    List<LoanAgreementDto> getAllLoanAgreementByPledgeAgreements(@RequestParam("pledgeAgreementIds") List<Long> pledgeAgreementIds);

    @GetMapping("/loan_agreement/search")
    List<LoanAgreementDto> getLoanAgreementBySearchCriteria(@RequestParam Map<String, String> reqParam, Pageable pageable);

    @PostMapping("/loan_agreement/insert")
    LoanAgreementDto insertLoanAgreement(@Valid @RequestBody LoanAgreementDto loanAgreementDto);

    @PutMapping("/loan_agreement/update")
    LoanAgreementDto updateLoanAgreement(@Valid @RequestBody LoanAgreementDto loanAgreementDto);

    @PostMapping(value = "/loan_agreement/insert_from_file", consumes = "multipart/form-data")
    List<LoanAgreementDto> insertLoanAgreementFromFile(@RequestPart("file") MultipartFile file);

    @PutMapping(value = "/loan_agreement/update_from_file", consumes = "multipart/form-data")
    List<LoanAgreementDto> updateLoanAgreementFromFile(@RequestPart("file") MultipartFile file);



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

    @GetMapping("/pledge_agreement/current_pa_for_employee/perv")
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

    @PostMapping(value = "/pledge_agreement/insert/file", consumes = "multipart/form-data")
    List<PledgeAgreementDto> insertPledgeAgreementFromFile(@RequestPart("file") MultipartFile file);

    @PutMapping(value = "/pledge_agreement/update/file", consumes = "multipart/form-data")
    List<PledgeAgreementDto> updatePledgeAgreementFromFile(@RequestPart("file") MultipartFile file);

    @PutMapping("/pledge_agreement/update/withdraw_pledge_subject")
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

    @GetMapping("/client/client_manager/count")
    Integer getCountClientByClientManager(@RequestParam("clientManagerId") Long clientManagerId);

    @GetMapping("/client/search")
    List<ClientDto> getClientBySearchCriteria(@RequestParam Map<String, String> reqParam);

    @PostMapping("/client/insert")
    ClientDto insertClient(@Valid @RequestBody ClientDto clientDto);

    @PutMapping("/client/update")
    ClientDto updateClient(@Valid @RequestBody ClientDto clientDto);

    @PostMapping(value = "/client/insert_from_file/client_legal_entity", consumes = "multipart/form-data")
    List<ClientDto> insertClientLegalEntityFromFile(@RequestPart("file") MultipartFile file);

    @PostMapping(value = "/client/insert_from_file/client_individual", consumes = "multipart/form-data")
    List<ClientDto> insertClientIndividualFromFile(@RequestPart("file") MultipartFile file);

    @PutMapping(value = "/client/update_from_file/client_legal_entity", consumes = "multipart/form-data")
    List<ClientDto> updateClientLegalEntityFromFile(@RequestPart("file") MultipartFile file);

    @PutMapping(value = "/client/update_from_file/client_individual", consumes = "multipart/form-data")
    List<ClientDto> updateClientIndividualFromFile(@RequestPart("file") MultipartFile file);



    @GetMapping("/client_manager/{clientManagerId}")
    ClientManagerDto getClientManager(@PathVariable("clientManagerId") Long clientManagerId);

    @GetMapping("/client_manager/all")
    List<ClientManagerDto> getAllClientManagers();

    @GetMapping("/client_manager/client")
    ClientManagerDto getClientManagerByClient(@RequestParam("clientId") Long clientId);

    @PostMapping("/client_manager/insert")
    ClientManagerDto insertClientManager(@Valid @RequestBody ClientManagerDto clientManagerDto);

    @PutMapping("/client_manager/update")
    ClientManagerDto updateClientManager(@Valid @RequestBody ClientManagerDto clientManagerDto);

    @PostMapping(value = "/client_manager/insert_from_file", consumes = "multipart/form-data")
    List<ClientManagerDto> insertClientManagerFromFile(@RequestPart("file") MultipartFile file);

    @PutMapping(value = "/client_manager/update_from_file", consumes = "multipart/form-data")
    List<ClientManagerDto> updateClientManagerFromFile(@RequestPart("file") MultipartFile file);



    @GetMapping("/pledge_subject/{pledgeSubjectId}")
    PledgeSubjectDto getPledgeSubject(@PathVariable("pledgeSubjectId") Long pledgeSubjectId);

    @GetMapping("/pledge_subject/pledge_agreement")
    List<PledgeSubjectDto> getPledgeSubjectByPledgeAgreement(@RequestParam("pledgeAgreementId") Long pledgeAgreementId);

    @GetMapping("/pledge_subject/search_by_name")
    List<PledgeSubjectDto> getPledgeSubjectsByName(@RequestParam("namePS") @NotBlank String namePS);

    @GetMapping("/pledge_subject/search_by_cadastral_num")
    List<PledgeSubjectDto> getPledgeSubjectsByCadastralNum(@RequestParam("cadastralNum") @NotBlank String cadastralNum);

    @GetMapping("/pledge_subject/search")
    List<PledgeSubjectDto> getPledgeSubjectBySearchCriteria(@RequestParam Map<String, String> reqParam, Pageable pageable);

    @PostMapping("/pledge_subject/insert")
    PledgeSubjectDto insertPledgeSubject(@Valid @RequestBody PledgeSubjectDtoNewWrapper pledgeSubjectDtoNewWrapper);

    @PutMapping("/pledge_subject/update")
    PledgeSubjectDto updatePledgeSubject(@Valid @RequestBody PledgeSubjectUpdateDtoWrapper pledgeSubjectUpdateDtoWrapper);

    @PostMapping(value = "/pledge_subject/insert_from_file/auto", consumes = "multipart/form-data")
    List<PledgeSubjectDto> insertPledgeSubjectAutoFromFile(@RequestPart("file") MultipartFile file);

    @PostMapping(value = "/pledge_subject/insert_from_file/equipment", consumes = "multipart/form-data")
    List<PledgeSubjectDto> insertPledgeSubjectEquipmentFromFile(@RequestPart("file") MultipartFile file);

    @PostMapping(value = "/pledge_subject/insert_from_file/building", consumes = "multipart/form-data")
    List<PledgeSubjectDto> insertPledgeSubjectBuildingFromFile(@RequestPart("file") MultipartFile file);

    @PostMapping(value = "/pledge_subject/insert_from_file/land_lease", consumes = "multipart/form-data")
    List<PledgeSubjectDto> insertPledgeSubjectLandLeaseFromFile(@RequestPart("file") MultipartFile file);

    @PostMapping(value = "/pledge_subject/insert_from_file/land_ownership", consumes = "multipart/form-data")
    List<PledgeSubjectDto> insertPledgeSubjectLandOwnershipFromFile(@RequestPart("file") MultipartFile file);

    @PostMapping(value = "/pledge_subject/insert_from_file/premise", consumes = "multipart/form-data")
    List<PledgeSubjectDto> insertPledgeSubjectPremiseFromFile(@RequestPart("file") MultipartFile file);

    @PostMapping(value = "/pledge_subject/insert_from_file/securities", consumes = "multipart/form-data")
    List<PledgeSubjectDto> insertPledgeSubjectSecuritiesFromFile(@RequestPart("file") MultipartFile file);

    @PostMapping(value = "/pledge_subject/insert_from_file/tbo", consumes = "multipart/form-data")
    List<PledgeSubjectDto> insertPledgeSubjectTboFromFile(@RequestPart("file") MultipartFile file);

    @PostMapping(value = "/pledge_subject/insert_from_file/vessel", consumes = "multipart/form-data")
    List<PledgeSubjectDto> insertPledgeSubjectVesselFromFile(@RequestPart("file") MultipartFile file);

    @PutMapping(value = "/pledge_subject/update_from_file", consumes = "multipart/form-data")
    List<PledgeSubjectDto> updatePledgeSubjectFromFile(@RequestPart("file") MultipartFile file);



    @GetMapping("/cost_history/{costHistoryId}")
    CostHistoryDto getCostHistoryById(@PathVariable("costHistoryId") Long costHistoryId);

    @GetMapping("/cost_history/pledge_subject")
    List<CostHistoryDto> getCostHistoryByPledgeSubject(@RequestParam("pledgeSubjectId") Long pledgeSubjectId);

    @PostMapping("/cost_history/insert")
    CostHistoryDto insertCostHistory(@Valid @RequestBody CostHistoryDto costHistoryDto);

    @PutMapping("/cost_history/update")
    CostHistoryDto updateCostHistory(@Valid @RequestBody CostHistoryDto costHistoryDto);

    @PostMapping(value = "/cost_history/insert_from_file", consumes = "multipart/form-data")
    List<CostHistoryDto> insertCostHistoryFromFile(@RequestPart("file") MultipartFile file);

    @PutMapping(value = "/cost_history/update_from_file", consumes = "multipart/form-data")
    List<CostHistoryDto> updateCostHistoryFromFile(@RequestPart("file") MultipartFile file);



    @GetMapping("/monitoring/{monitoringId}")
    MonitoringDto getMonitoringById(@PathVariable("monitoringId") Long monitoringId);

    @GetMapping("/monitoring/pledge_subject")
    List<MonitoringDto> getMonitoringsByPledgeSubject(@RequestParam("pledgeSubjectId") Long pledgeSubjectId);

    @PostMapping("/monitoring/insert")
    MonitoringDto insertMonitoring(@Valid @RequestBody MonitoringDto monitoringDto,
                                          @RequestParam("pledgeSubjectId") Long pledgeSubjectId);

    @PostMapping("/monitoring/insert/pledge_agreement")
    List<MonitoringDto> insertMonitoringByPledgeAgreement(@Valid @RequestBody MonitoringDto monitoringDto,
                                                                 @RequestParam("pledgeAgreementId") Long pledgeAgreementId);

    @PostMapping("/monitoring/insert/client")
    List<MonitoringDto> insertMonitoringByClient(@Valid @RequestBody MonitoringDto monitoringDto,
                                                        @RequestParam("clientId") Long clienttId);

    @PostMapping(value = "/monitoring/insert_from_file", consumes = "multipart/form-data")
    List<MonitoringDto> insertMonitoringFromFile(@RequestPart("file") MultipartFile file);

    @PutMapping("/monitoring/update")
    MonitoringDto updateMonitoring(@Valid @RequestBody MonitoringDto monitoringDto);

    @PutMapping(value = "/monitoring/update_from_file", consumes = "multipart/form-data")
    List<MonitoringDto> updateMonitoringFromFile(@RequestPart("file") MultipartFile file);



    @GetMapping("/insurance/{insuranceId}")
    InsuranceDto getInsurance(@PathVariable("insuranceId") Long insuranceId);

    @GetMapping("/insurance/pledge_subject")
    List<InsuranceDto> getInsurancesByPledgeSubject(@RequestParam("pledgeSubjectId") Long pledgeSubjectId);

    @PostMapping("/insurance/insert")
    InsuranceDto insertInsurance(@Valid @RequestBody InsuranceDto insuranceDto);

    @PutMapping("/insurance/update")
    InsuranceDto updateInsurance(@Valid @RequestBody InsuranceDto insuranceDto);

    @PostMapping(value = "/insurance/insert_from_file", consumes = "multipart/form-data")
    List<InsuranceDto> insertInsuranceFromFile(@RequestPart("file") MultipartFile file);

    @PutMapping(value = "/insurance/update_from_file", consumes = "multipart/form-data")
    List<InsuranceDto> updateInsuranceFromFile(@RequestPart("file") MultipartFile file);



    @GetMapping("/encumbrance/{encumbranceId}")
    EncumbranceDto getEncumbrance(@PathVariable("encumbranceId") Long encumbranceId);

    @GetMapping("/encumbrance/pledge_subject")
    List<EncumbranceDto> getEncumbrancesByPledgeSubject(@RequestParam("pledgeSubjectId") Long pledgeSubjectId);

    @PostMapping("/encumbrance/insert")
    EncumbranceDto insertEncumbrance(@Valid @RequestBody EncumbranceDto encumbranceDto);

    @PutMapping("/encumbrance/update")
    EncumbranceDto updateEncumbrance(@Valid @RequestBody EncumbranceDto encumbranceDto);

    @PostMapping(value = "/encumbrance/insert_from_file", consumes = "multipart/form-data")
    List<EncumbranceDto> insertEncumbranceFromFile(@RequestPart("file") MultipartFile file);

    @PutMapping(value = "/encumbrance/update_from_file", consumes = "multipart/form-data")
    List<EncumbranceDto> updateEncumbranceFromFile(@RequestPart("file") MultipartFile file);

}
