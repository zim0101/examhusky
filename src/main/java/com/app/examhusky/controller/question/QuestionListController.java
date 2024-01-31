package com.app.examhusky.controller.question;

import com.app.examhusky.model.Question;
import com.app.examhusky.service.QuestionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/question")
public class QuestionListController {
    private final QuestionService questionService;

    public QuestionListController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ModelAttribute
    public Page<Question>
    addQuestionCategoryListToModel(HttpSession session,
                                   @RequestParam("page") Optional<Integer> page,
                                   @RequestParam("size") Optional<Integer> size,
                                   @RequestParam("sortField") Optional<String> sortField,
                                   @RequestParam("orderBy") Optional<String> orderBy) {

        return questionService.sortAndPaginateList(session, page,
                size, sortField, orderBy);
    }

    @GetMapping
    public String paginatedList() {
        return "question/list";
    }
}