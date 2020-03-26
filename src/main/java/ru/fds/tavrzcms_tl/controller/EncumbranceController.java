package ru.fds.tavrzcms_tl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.fds.tavrzcms_tl.dto.EncumbranceDto;
import ru.fds.tavrzcms_tl.dto.PledgeSubjectDto;
import ru.fds.tavrzcms_tl.service.EncumbranceService;
import ru.fds.tavrzcms_tl.service.PledgeSubjectService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/encumbrance")
public class EncumbranceController {

    private final EncumbranceService encumbranceService;
    private final PledgeSubjectService pledgeSubjectService;

    private static final String ATTR_PLEDGE_SUBJECT = "pledgeSubject";
    private static final String ATTR_ENCUMBRANCE_LIST = "encumbranceList";
    private static final String ATTR_ENCUMBRANCE = "encumbranceDto";
    private static final String PAGE_ENCUMBRANCES = "encumbrance/encumbrances";
    private static final String PAGE_CARD_INSERT = "encumbrance/card_insert";
    private static final String PAGE_CARD_UPDATE = "encumbrance/card_update";

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

    @GetMapping("/insert/card")
    public String encumbranceNewCard(@RequestParam("pledgeSubjectId") Long pledgeSubjectId,
                                     Model model){

        EncumbranceDto encumbranceDto = EncumbranceDto.builder()
                .pledgeSubjectId(pledgeSubjectId)
                .build();

        model.addAttribute(ATTR_ENCUMBRANCE, encumbranceDto);

        return PAGE_CARD_INSERT;
    }

    @PostMapping("/insert")
    public String encumbranceInsert(@Valid EncumbranceDto encumbranceDto,
                                    BindingResult bindingResult,
                                    Model model){

        if(bindingResult.hasErrors())
            return PAGE_CARD_INSERT;

        encumbranceService.insertEncumbrance(encumbranceDto);

        return encumbrancePage(encumbranceDto.getPledgeSubjectId(), model);
    }

    @GetMapping("/update/card")
    public String encumbranceUpdateCard(@RequestParam("encumbranceId") Long encumbranceId,
                                        Model model){

        EncumbranceDto encumbranceDto = encumbranceService.getEncumbranceById(encumbranceId);

        model.addAttribute(ATTR_ENCUMBRANCE, encumbranceDto);

        return PAGE_CARD_UPDATE;
    }

    @PostMapping("/update")
    public String encumbranceUpdate(@Valid EncumbranceDto encumbranceDto,
                                    BindingResult bindingResult,
                                    Model model){

        if(bindingResult.hasErrors())
            return PAGE_CARD_UPDATE;

        encumbranceDto = encumbranceService.updateEncumbrance(encumbranceDto);

        return encumbrancePage(encumbranceDto.getPledgeSubjectId(), model);
    }
}
