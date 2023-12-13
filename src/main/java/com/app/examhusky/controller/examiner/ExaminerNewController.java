package com.app.examhusky.controller.examiner;

import com.app.examhusky.dto.ExaminerAccountDto;
import com.app.examhusky.model.Designation;
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
@RequestMapping("/examiner/new")
public class ExaminerNewController {
    private final AccountService accountService;
    private final DesignationService designationService;

    public ExaminerNewController(AccountService accountService, DesignationService designationService) {
        this.accountService = accountService;
        this.designationService = designationService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ModelAttribute("designationList")
    public List<Designation> addDesignationListToModel() {
        return designationService.findAllActive();
    }

    @ModelAttribute("examinerAccountDto")
    public ExaminerAccountDto addExaminerAccountDtoToModel(){
        return new ExaminerAccountDto();
    }

    @GetMapping
    public String renderCreateForm() {
        return "examiner/new";
    }

    @PostMapping
    public String submitCreateForm(@Valid @ModelAttribute ExaminerAccountDto examinerAccountDto,
                                   BindingResult result) {
        if(result.hasErrors()){
            return "examiner/new";
        }
        accountService.saveExaminerAccount(examinerAccountDto);
        return "redirect:/examiner";
    }
}