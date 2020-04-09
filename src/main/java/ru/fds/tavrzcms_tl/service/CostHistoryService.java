package ru.fds.tavrzcms_tl.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.fds.tavrzcms_tl.dto.CostHistoryDto;
import ru.fds.tavrzcms_tl.service.feign.TavrzcmsAPIFeignService;

import java.util.List;

@Service
public class CostHistoryService {

    private final TavrzcmsAPIFeignService tavrzcmsAPIFeignService;

    public CostHistoryService(TavrzcmsAPIFeignService tavrzcmsAPIFeignService) {
        this.tavrzcmsAPIFeignService = tavrzcmsAPIFeignService;
    }

    public List<CostHistoryDto> getCostHistoryByPledgeSubject(Long pledgeSubjectId){
        return tavrzcmsAPIFeignService.getCostHistoryByPledgeSubject(pledgeSubjectId);
    }

    public CostHistoryDto getCostHistoryById(Long costHistoryId){
        return tavrzcmsAPIFeignService.getCostHistoryById(costHistoryId);
    }

    @Transactional
    public CostHistoryDto insertCostHistory(CostHistoryDto costHistoryDto){
        return tavrzcmsAPIFeignService.insertCostHistory(costHistoryDto);
    }

    @Transactional
    public CostHistoryDto updateCostHistory(CostHistoryDto costHistoryDto){
        return tavrzcmsAPIFeignService.updateCostHistory(costHistoryDto);
    }

    @Transactional
    public List<CostHistoryDto> insertCostHistoryFromFile(MultipartFile file){
        return tavrzcmsAPIFeignService.insertCostHistoryFromFile(file);
    }
}
