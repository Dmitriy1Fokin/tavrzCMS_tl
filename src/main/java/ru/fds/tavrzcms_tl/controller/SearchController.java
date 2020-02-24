package ru.fds.tavrzcms_tl.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.fds.tavrzcms_tl.dto.ClientDto;
import ru.fds.tavrzcms_tl.dto.LoanAgreementDto;
import ru.fds.tavrzcms_tl.dto.PledgeAgreementDto;
import ru.fds.tavrzcms_tl.dto.PledgeSubjectDto;
import ru.fds.tavrzcms_tl.service.ClientService;
import ru.fds.tavrzcms_tl.service.LoanAgreementService;
import ru.fds.tavrzcms_tl.service.PledgeAgreementService;
import ru.fds.tavrzcms_tl.service.PledgeSubjectService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final LoanAgreementService loanAgreementService;
    private final PledgeAgreementService pledgeAgreementService;
    private final PledgeSubjectService pledgeSubjectService;
    private final ClientService clientService;

    private static final String ATTR_RESULT_LIST = "resultList";
    private static final String ATTR_REQ_PARAM = "reqParam";
    private static final String PAGE_SEARCH = "search/search";
    private static final String PAGE_RESULT = "search/search_results";

    public SearchController(LoanAgreementService loanAgreementService,
                            PledgeAgreementService pledgeAgreementService,
                            PledgeSubjectService pledgeSubjectService,
                            ClientService clientService) {
        this.loanAgreementService = loanAgreementService;
        this.pledgeAgreementService = pledgeAgreementService;
        this.pledgeSubjectService = pledgeSubjectService;
        this.clientService = clientService;
    }

    @GetMapping("/search")
    public String searchPage(){
        return PAGE_SEARCH;
    }

    @GetMapping("/search_results")
    public String searchResultsPage(@RequestParam Map<String, String> reqParam,
                                    Model model){

        Pageable pageable = PageRequest.of(0, 200);

        if(reqParam.get("typeOfSearch").equals("searchLA")){
            List<LoanAgreementDto> loanAgreementDtoList = loanAgreementService.getLoanAgreementBySearchCriteria(reqParam, pageable);
            model.addAttribute(ATTR_RESULT_LIST, loanAgreementDtoList);
        }else if(reqParam.get("typeOfSearch").equals("searchPA")){
            List<PledgeAgreementDto> pledgeAgreementDtoList = pledgeAgreementService.getPledgeAgreementBySearchCriteria(reqParam, pageable);
            model.addAttribute(ATTR_RESULT_LIST, pledgeAgreementDtoList);
        }else if(reqParam.get("typeOfSearch").equals("searchPS")){
            List<PledgeSubjectDto> pledgeSubjectDtoList = pledgeSubjectService.getPledgeSubjectBySearchCriteria(reqParam, pageable);
            model.addAttribute(ATTR_RESULT_LIST, pledgeSubjectDtoList);
        }else if(reqParam.get("typeOfSearch").equals("searchClient")){
            List<ClientDto> clientDtoList = clientService.getClientBySearchCriteria(reqParam);
            model.addAttribute(ATTR_RESULT_LIST, clientDtoList);
        }

        model.addAttribute(ATTR_REQ_PARAM, reqParam);

        return PAGE_RESULT;
    }
}
