package ru.fds.tavrzcms_tl.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.fds.tavrzcms_tl.dto.PledgeSubjectDto;
import ru.fds.tavrzcms_tl.service.feign.TavrzcmsAPIFeignService;
import ru.fds.tavrzcms_tl.wrapper.PledgeSubjectDtoNewWrapper;
import ru.fds.tavrzcms_tl.wrapper.PledgeSubjectUpdateDtoWrapper;

import java.util.List;
import java.util.Map;

@Service
public class PledgeSubjectService {

    private final TavrzcmsAPIFeignService tavrzcmsAPIFeignService;

    public PledgeSubjectService(TavrzcmsAPIFeignService tavrzcmsAPIFeignService) {
        this.tavrzcmsAPIFeignService = tavrzcmsAPIFeignService;
    }

    public List<PledgeSubjectDto> getPledgeSubjectsByPledgeAgreement(Long pledgeAgreementId){
        return tavrzcmsAPIFeignService.getPledgeSubjectByPledgeAgreement(pledgeAgreementId);
    }

    public PledgeSubjectDto getPledgeSubjectById(Long pledgeSubjectId){
        return tavrzcmsAPIFeignService.getPledgeSubject(pledgeSubjectId);
    }

    public List<PledgeSubjectDto> getPledgeSubjectBySearchCriteria(Map<String, String> searchParam, Pageable pageable){
        return tavrzcmsAPIFeignService.getPledgeSubjectBySearchCriteria(searchParam, pageable);
    }

    public List<PledgeSubjectDto> getPledgeSubjectByCadastralNum(String cadastralNum){
        return tavrzcmsAPIFeignService.getPledgeSubjectsByCadastralNum(cadastralNum);
    }

    public List<PledgeSubjectDto> getPledgeSubjectsByName(String name){
        return tavrzcmsAPIFeignService.getPledgeSubjectsByName(name);
    }

    @Transactional
    public PledgeSubjectDto updatePledgeSubject(PledgeSubjectUpdateDtoWrapper pledgeSubjectUpdateDtoWrapper){
        return tavrzcmsAPIFeignService.updatePledgeSubject(pledgeSubjectUpdateDtoWrapper);
    }

    @Transactional
    public PledgeSubjectDto insertPledgeSubject(PledgeSubjectDtoNewWrapper pledgeSubjectDtoNewWrapper){
        return tavrzcmsAPIFeignService.insertPledgeSubject(pledgeSubjectDtoNewWrapper);
    }

    @Transactional
    public List<PledgeSubjectDto> insertPledgeSubjectAutoFromFile(MultipartFile file){
        return tavrzcmsAPIFeignService.insertPledgeSubjectAutoFromFile(file);
    }

    @Transactional
    public List<PledgeSubjectDto> insertPledgeSubjectEquipmentFromFile(MultipartFile file){
        return tavrzcmsAPIFeignService.insertPledgeSubjectEquipmentFromFile(file);
    }

    @Transactional
    public List<PledgeSubjectDto> insertPledgeSubjectBuildingFromFile(MultipartFile file){
        return tavrzcmsAPIFeignService.insertPledgeSubjectBuildingFromFile(file);
    }

    @Transactional
    public List<PledgeSubjectDto> insertPledgeSubjectLandOwnershipFromFile(MultipartFile file){
        return tavrzcmsAPIFeignService.insertPledgeSubjectLandOwnershipFromFile(file);
    }

    @Transactional
    public List<PledgeSubjectDto> insertPledgeSubjectLandLeaseFromFile(MultipartFile file){
        return tavrzcmsAPIFeignService.insertPledgeSubjectLandLeaseFromFile(file);
    }

    @Transactional
    public List<PledgeSubjectDto> insertPledgeSubjectRoomFromFile(MultipartFile file){
        return tavrzcmsAPIFeignService.insertPledgeSubjectPremiseFromFile(file);
    }

    @Transactional
    public List<PledgeSubjectDto> insertPledgeSubjectSecurityFromFile(MultipartFile file){
        return tavrzcmsAPIFeignService.insertPledgeSubjectSecuritiesFromFile(file);
    }

    @Transactional
    public List<PledgeSubjectDto> insertPledgeSubjectTboFromFile(MultipartFile file){
        return tavrzcmsAPIFeignService.insertPledgeSubjectTboFromFile(file);
    }

    @Transactional
    public List<PledgeSubjectDto> insertPledgeSubjectVesselFromFile(MultipartFile file){
        return tavrzcmsAPIFeignService.insertPledgeSubjectVesselFromFile(file);
    }
}
