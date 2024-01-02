package com.app.examhusky.controller.exam;

import com.app.examhusky.model.Exam;
import com.app.examhusky.service.ExamService;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/exam/{id}")
public class ExamEditController {
    private final ExamService examService;

    public ExamEditController(ExamService examService) {
        this.examService = examService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ModelAttribute("exam")
    public Exam addExamToModel(@PathVariable Integer id){
        return examService.findById(id);
    }

    @GetMapping
    public String renderEditForm(@ModelAttribute Exam exam) {
        return "exam/edit";
    }

    @PutMapping
    public String submitEditForm(@Valid @ModelAttribute Exam exam,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "exam/edit";
        }
        examService.createOrUpdate(exam);
        return "redirect:/exam";
    }

    @PutMapping("/publish")
    public String publish(@PathVariable Integer id) {
        examService.publish(id);
        return "redirect:/exam";
    }

    @PutMapping("/start")
    public String start(@PathVariable Integer id) {
        examService.start(id);
        return "redirect:/exam";
    }

    @PutMapping("/stop")
    public String stop(@PathVariable Integer id) {
        examService.stop(id);
        return "redirect:/exam";
    }

    @DeleteMapping
    public String delete(@ModelAttribute Exam exam) {
        examService.softDelete(exam);
        return "redirect:/exam";
    }
}