package ru.fds.tavrzcms_tl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.fds.tavrzcms_tl.dto.EncumbranceDto;
import ru.fds.tavrzcms_tl.dto.PledgeSubjectDto;
import ru.fds.tavrzcms_tl.service.EncumbranceService;
import ru.fds.tavrzcms_tl.service.PledgeSubjectService;

import java.util.List;

@Controller
@RequestMapping("/encumbrance")
public class EncumbranceController {

    private final EncumbranceService encumbranceService;
    private final PledgeSubjectService pledgeSubjectService;

    private static final String ATTR_PLEDGE_SUBJECT = "pledgeSubject";
    private static final String ATTR_ENCUMBRANCE_LIST = "encumbranceList";
    private static final String PAGE_ENCUMBRANCES = "encumbrance/encumbrances";

    public EncumbranceController(EncumbranceService encumbranceService,
                                 PledgeSubjectService pledgeSubjectService) {
        this.encumbranceService = encumbranceService;
        this.pledgeSubjectService = pledgeSubjectService;
    }

    @GetMapping("/encumbrances")
    public  String encumbrancePage(@RequestParam("pledgeSubjectId") Long pledgeSubjectId,
                                   Model model){

        PledgeSubjectDto pledgeSubjectDto = pledgeSubjectService.getPledgeSubjectById(pledgeSubjectId);
        List<EncumbranceDto> encumbranceList = encumbranceService.getEncumbranceByPledgeSubject(pledgeSubjectId);

        model.addAttribute(ATTR_PLEDGE_SUBJECT, pledgeSubjectDto);
        model.addAttribute(ATTR_ENCUMBRANCE_LIST, encumbranceList);

        return PAGE_ENCUMBRANCES;
    }
}
