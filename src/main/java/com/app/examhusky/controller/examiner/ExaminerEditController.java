package com.app.examhusky.controller.examiner;

import com.app.examhusky.model.*;
import com.app.examhusky.service.AccountService;
import com.app.examhusky.service.DesignationService;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/examiner/{id}")
public class ExaminerEditController {
    private final AccountService accountService;
    private final DesignationService designationService;

    ExaminerEditController(AccountService accountService, DesignationService designationService) {
        this.accountService = accountService;
        this.designationService = designationService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ModelAttribute("examiner")
    public Examiner addExaminerToModel(@PathVariable Integer id){
        return accountService.findExaminerById(id);
    }

    @ModelAttribute("designationList")
    public List<Designation> addDesignationListToModel() {
        return designationService.findAllActive();
    }

    @GetMapping
    public String renderEditForm(@ModelAttribute Examiner examiner) {
        return "examiner/edit";
    }

    @PutMapping
    public String submitEditForm(@Valid @ModelAttribute Examiner examiner,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "examiner/edit";
        }
        accountService.updateExaminerAccount(examiner);
        return "redirect:/examiner";
    }

    @PutMapping("/disable")
    public String disable(@ModelAttribute Examiner examiner) {
        accountService.disable(examiner.getAccount());
        return "redirect:/examiner";
    }

    @PutMapping("/enable")
    public String enable(@ModelAttribute Examiner examiner) {
        accountService.enable(examiner.getAccount());
        return "redirect:/examiner";
    }

    @DeleteMapping
    public String delete(@ModelAttribute Examiner examiner) {
        accountService.softDeleteExaminerAccount(examiner);
        return "redirect:/examiner";
    }
}