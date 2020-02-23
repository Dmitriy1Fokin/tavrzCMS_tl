package ru.fds.tavrzcms_tl.service;

import org.springframework.stereotype.Service;
import ru.fds.tavrzcms_tl.dto.EncumbranceDto;
import ru.fds.tavrzcms_tl.service.feign.TavrzcmsAPIFeignService;

import java.util.List;

@Service
public class EncumbranceService {

    private final TavrzcmsAPIFeignService tavrzcmsAPIFeignService;

    public EncumbranceService(TavrzcmsAPIFeignService tavrzcmsAPIFeignService) {
        this.tavrzcmsAPIFeignService = tavrzcmsAPIFeignService;
    }

    public List<EncumbranceDto> getEncumbranceByPledgeSubject(Long pledgeSubjectId){
        return tavrzcmsAPIFeignService.getEncumbrancesByPledgeSubject(pledgeSubjectId);
    }
}
