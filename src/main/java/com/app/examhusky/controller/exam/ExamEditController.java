package com.app.examhusky.controller.exam;

import com.app.examhusky.model.Exam;
import com.app.examhusky.service.ExamService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/exam/{id}")
public class ExamEditController {
    private final ExamService examService;

    public ExamEditController(ExamService examService) {
        this.examService = examService;
    }

    @ModelAttribute
    public Exam addExamToModel(@PathVariable Integer id){
        return examService.findById(id);
    }

    @GetMapping
    public String renderEditForm() {
        return "exam/edit";
    }

    @PostMapping
    public String submitEditForm(@Valid @ModelAttribute Exam exam, BindingResult result) {
        if (result.hasErrors()) {
            return "exam/edit";
        }

        examService.createOrUpdate(exam);
        return "redirect:/exam";
    }

    @PutMapping("/publish")
    public String publish(@ModelAttribute Exam exam) {
        examService.publish(exam);
        return "redirect:/exam";
    }

    @PutMapping("/start")
    public String start(@ModelAttribute Exam exam) {
        examService.start(exam);
        return "redirect:/exam";
    }

    @PutMapping("/stop")
    public String stop(@ModelAttribute Exam exam) {
        examService.stop(exam);
        return "redirect:/exam";
    }

    @DeleteMapping
    public String delete(@ModelAttribute Exam exam) {
        examService.softDelete(exam);
        return "redirect:/exam";
    }
}