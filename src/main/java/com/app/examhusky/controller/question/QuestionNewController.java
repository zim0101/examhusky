package com.app.examhusky.controller.question;

import com.app.examhusky.model.Question;
import com.app.examhusky.model.QuestionCategory;
import com.app.examhusky.service.QuestionCategoryService;
import com.app.examhusky.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/question/new")
public class QuestionNewController {
    private final QuestionService questionService;
    private final QuestionCategoryService questionCategoryService;

    public QuestionNewController(QuestionService questionService, QuestionCategoryService questionCategoryService) {
        this.questionService = questionService;
        this.questionCategoryService = questionCategoryService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ModelAttribute
    public Question addQuestionToModel(){
        return new Question();
    }

    @ModelAttribute
    public List<QuestionCategory> addQuestionCategoryListToModel(){
        return questionCategoryService.findAllActive();
    }

    @GetMapping
    public String renderCreateForm() {
        return "question/new";
    }

    @PostMapping
    public String submitCreateForm(@Valid @ModelAttribute Question question,
                                   BindingResult result) {
        if(result.hasErrors()){
            return "question/new";
        }
        questionService.createOrUpdate(question);
        return "redirect:/question";
    }
}