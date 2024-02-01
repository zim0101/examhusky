package com.app.examhusky.controller.examiner;

import com.app.examhusky.model.Examiner;
import com.app.examhusky.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/examiner")
public class ExaminerListController {
    private final AccountService accountService;

    ExaminerListController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ModelAttribute("examiners")
    public Page<Examiner>
    addExaminerPageToModel(HttpSession session,
                                   @RequestParam("page") Optional<Integer> page,
                                   @RequestParam("size") Optional<Integer> size,
                                   @RequestParam("sortField") Optional<String> sortField,
                                   @RequestParam("orderBy") Optional<String> orderBy) {

        return accountService.sortAndPaginateActiveExaminerList(session, page, size, sortField,
                orderBy);
    }

    @GetMapping
    public String paginatedList() {
        return "examiner/list";
    }
}