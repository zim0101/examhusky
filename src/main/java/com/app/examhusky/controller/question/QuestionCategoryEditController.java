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
@RequestMapping("/question_category/{id}")
public class QuestionCategoryEditController {

    private final QuestionCategoryService questionCategoryService;

    public QuestionCategoryEditController(QuestionCategoryService questionCategoryService) {
        this.questionCategoryService = questionCategoryService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ModelAttribute
    public QuestionCategory addQuestionCategoryToModel(@PathVariable Integer id){
        return questionCategoryService.findById(id);
    }

    @GetMapping
    public String renderEditForm(@ModelAttribute QuestionCategory questionCategory) {
        return "question_category/edit";
    }

    @PutMapping
    public String submitEditForm(@Valid @ModelAttribute QuestionCategory questionCategory,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "question_category/edit";
        }
        questionCategoryService.createOrUpdate(questionCategory);
        return "redirect:/question_category";
    }

    @PutMapping("/disable")
    public String disable(@ModelAttribute QuestionCategory questionCategory) {
        questionCategoryService.disable(questionCategory);
        return "redirect:/question_category";
    }

    @PutMapping("/enable")
    public String enable(@ModelAttribute QuestionCategory questionCategory) {
        questionCategoryService.enable(questionCategory);
        return "redirect:/question_category";
    }

    @DeleteMapping
    public String delete(@ModelAttribute QuestionCategory questionCategory) {
        questionCategoryService.softDelete(questionCategory);
        return "redirect:/question_category";
    }
}