package com.app.examhusky.controller.candidate;

import com.app.examhusky.model.Candidate;
import com.app.examhusky.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/candidate")
public class CandidateListController {
    private final AccountService accountService;

    CandidateListController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ModelAttribute("candidates")
    public Page<Candidate>
    addCandidatePageToModel(HttpSession session,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam("sortField") Optional<String> sortField,
                           @RequestParam("orderBy") Optional<String> orderBy) {

        return accountService.sortAndPaginateActiveCandidateList(session, page, size, sortField,
                orderBy);
    }

    @GetMapping
    public String paginatedList() {
        return "candidate/list";
    }
}