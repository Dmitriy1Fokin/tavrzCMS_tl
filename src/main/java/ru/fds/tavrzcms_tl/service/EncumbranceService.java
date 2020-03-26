package ru.fds.tavrzcms_tl.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fds.tavrzcms_tl.dto.EncumbranceDto;
import ru.fds.tavrzcms_tl.service.feign.TavrzcmsAPIFeignService;

import java.util.List;

@Service
public class EncumbranceService {

    private final TavrzcmsAPIFeignService tavrzcmsAPIFeignService;

    public EncumbranceService(TavrzcmsAPIFeignService tavrzcmsAPIFeignService) {
        this.tavrzcmsAPIFeignService = tavrzcmsAPIFeignService;
    }

    public EncumbranceDto getEncumbranceById(Long encumbranceId){
        return tavrzcmsAPIFeignService.getEncumbrance(encumbranceId);
    }

    public List<EncumbranceDto> getEncumbranceByPledgeSubject(Long pledgeSubjectId){
        return tavrzcmsAPIFeignService.getEncumbrancesByPledgeSubject(pledgeSubjectId);
    }

    @Transactional
    public void insertEncumbrance(EncumbranceDto encumbranceDto){
        tavrzcmsAPIFeignService.insertEncumbrance(encumbranceDto);
    }

    @Transactional
    public EncumbranceDto updateEncumbrance(EncumbranceDto encumbranceDto){
        return tavrzcmsAPIFeignService.updateEncumbrance(encumbranceDto);
    }
}
