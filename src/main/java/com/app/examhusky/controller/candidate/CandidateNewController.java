package com.app.examhusky.controller.candidate;

import com.app.examhusky.dto.CandidateAccountDto;
import com.app.examhusky.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/candidate/new")
public class CandidateNewController {
    private final AccountService accountService;

    public CandidateNewController(AccountService accountService) {
        this.accountService = accountService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ModelAttribute("candidateAccountDto")
    public CandidateAccountDto addCandidateAccountDtoToModel(){
        return new CandidateAccountDto();
    }

    @GetMapping
    public String renderCreateForm() {
        return "candidate/new";
    }

    @PostMapping
    public String submitCreateForm(@Valid @ModelAttribute CandidateAccountDto candidateAccountDto,
                                   BindingResult result) {
        if(result.hasErrors()){
            return "candidate/new";
        }
        accountService.saveCandidateAccount(candidateAccountDto);
        return "redirect:/candidate";
    }
}