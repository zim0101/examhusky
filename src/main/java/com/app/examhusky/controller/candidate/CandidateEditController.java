package com.app.examhusky.controller.candidate;

import com.app.examhusky.model.Candidate;
import com.app.examhusky.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/candidate/{id}")
public class CandidateEditController {
    private final AccountService accountService;

    CandidateEditController(AccountService accountService) {
        this.accountService = accountService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ModelAttribute
    public Candidate addCandidateToModel(@PathVariable Integer id){
        return accountService.findCandidateById(id);
    }

    @GetMapping
    public String renderEditForm(@ModelAttribute Candidate candidate) {
        return "candidate/edit";
    }

    @PutMapping
    public String submitEditForm(@Valid @ModelAttribute Candidate candidate,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "candidate/edit";
        }
        accountService.updateCandidateAccount(candidate);
        return "redirect:/candidate";
    }

    @PutMapping("/disable")
    public String disable(@ModelAttribute Candidate candidate) {
        accountService.disable(candidate.getAccount());
        return "redirect:/candidate";
    }

    @PutMapping("/enable")
    public String enable(@ModelAttribute Candidate candidate) {
        accountService.enable(candidate.getAccount());
        return "redirect:/candidate";
    }

    @DeleteMapping
    public String delete(@ModelAttribute Candidate candidate) {
        accountService.softDeleteCandidateAccount(candidate);
        return "redirect:/candidate";
    }
}