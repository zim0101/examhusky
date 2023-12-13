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
@RequestMapping("/question/{id}")
@PreAuthorize("hasRole('ROLE_ADMIN') && hasRole('ROLE_EXAMINER')")
public class QuestionEditController {
    private final QuestionService questionService;
    private final QuestionCategoryService questionCategoryService;

    public QuestionEditController(QuestionService questionService,
                                  QuestionCategoryService questionCategoryService) {
        this.questionService = questionService;
        this.questionCategoryService = questionCategoryService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ModelAttribute("question")
    public Question addQuestionToModel(@PathVariable Integer id){
        return questionService.findById(id);
    }

    @ModelAttribute("questionCategoryList")
    public List<QuestionCategory> addQuestionCategoryListToModel(){
        return questionCategoryService.findAllActive();
    }

    @GetMapping
    public String renderEditForm(@ModelAttribute Question question) {
        return "question/edit";
    }

    @PutMapping
    public String submitEditForm(@Valid @ModelAttribute Question question,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "question/edit";
        }
        questionService.createOrUpdate(question);
        return "redirect:/question";
    }

    @PutMapping("/disable")
    public String disable(@ModelAttribute Question question) {
        questionService.disable(question);
        return "redirect:/question";
    }

    @PutMapping("/enable")
    public String enable(@ModelAttribute Question question) {
        questionService.enable(question);
        return "redirect:/question";
    }

    @DeleteMapping
    public String delete(@ModelAttribute Question question) {
        questionService.softDelete(question);
        return "redirect:/question";
    }
}