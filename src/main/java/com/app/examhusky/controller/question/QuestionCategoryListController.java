package com.app.examhusky.controller.question;

import com.app.examhusky.model.QuestionCategory;
import com.app.examhusky.service.QuestionCategoryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/question_category")
public class QuestionCategoryListController {

    private final QuestionCategoryService questionCategoryService;

    public QuestionCategoryListController(QuestionCategoryService questionCategoryService) {
        this.questionCategoryService = questionCategoryService;
    }

    @ModelAttribute("questionCategoris")
    public Page<QuestionCategory>
    addQuestionCategoryPageToModel(HttpSession session,
                       @RequestParam("page") Optional<Integer> page,
                       @RequestParam("size") Optional<Integer> size,
                       @RequestParam("sortField") Optional<String> sortField,
                       @RequestParam("orderBy") Optional<String> orderBy) {

        return questionCategoryService.sortAndPaginateList(session, page,
                size, sortField, orderBy);
    }

    @GetMapping
    public String paginatedList() {
        return "question_category/list";
    }
}