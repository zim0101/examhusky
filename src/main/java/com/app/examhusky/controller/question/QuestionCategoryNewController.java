package com.app.examhusky.controller.question;

import com.app.examhusky.model.QuestionCategory;
import com.app.examhusky.service.QuestionCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/question_category/new")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class QuestionCategoryNewController {
    private final QuestionCategoryService questionCategoryService;

    public QuestionCategoryNewController(QuestionCategoryService questionCategoryService) {
        this.questionCategoryService = questionCategoryService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ModelAttribute("questionCategory")
    public Object addQuestionCategoryToModel(){
        return new QuestionCategory();
    }

    @GetMapping
    public String renderCreateForm() {
        return "question_category/new";
    }

    @PostMapping
    public String submitCreateForm(@Valid @ModelAttribute QuestionCategory questionCategory,
                                   BindingResult result) {
        if(result.hasErrors()){
            return "question_category/new";
        }
        questionCategoryService.createOrUpdate(questionCategory);
        return "redirect:/question_category";
    }
}