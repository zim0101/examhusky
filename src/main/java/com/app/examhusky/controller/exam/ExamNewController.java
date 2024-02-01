package com.app.examhusky.controller.exam;

import com.app.examhusky.model.Exam;
import com.app.examhusky.model.Question;
import com.app.examhusky.model.QuestionCategory;
import com.app.examhusky.service.ExamService;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/exam/new")
public class ExamNewController {
    private final ExamService examService;

    public ExamNewController(ExamService examService) {
        this.examService = examService;
    }

    @ModelAttribute
    public Exam addExamToModel(){
        return new Exam();
    }

    @GetMapping
    public String renderCreateForm() {
        return "exam/new";
    }

    @PostMapping
    public String submitCreateForm(@Valid @ModelAttribute Exam exam,
                                   BindingResult result) {
        if(result.hasErrors()){
            return "exam/new";
        }
        examService.createOrUpdate(exam);
        return "redirect:/exam";
    }
}