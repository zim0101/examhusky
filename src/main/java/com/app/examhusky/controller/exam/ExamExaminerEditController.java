package com.app.examhusky.controller.exam;

import com.app.examhusky.model.Exam;
import com.app.examhusky.model.Examiner;
import com.app.examhusky.service.ExamService;
import com.app.examhusky.service.ExaminerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exam/{examId}/examiner/{examinerId}")
public class ExamExaminerEditController {
    private final ExamService examService;
    private final ExaminerService examinerService;

    public ExamExaminerEditController(ExamService examService, ExaminerService examinerService) {
        this.examService = examService;
        this.examinerService = examinerService;
    }

    @ModelAttribute
    public Exam addExamToModel(@PathVariable Integer examId) {
        return examService.findById(examId);
    }

    @ModelAttribute
    public Examiner addExaminerToModel(@PathVariable Integer examinerId) {
        return examinerService.findById(examinerId);
    }

    @PutMapping("/add")
    public String addExaminerToExam(@ModelAttribute Exam exam, @ModelAttribute Examiner examiner) {
        examService.addExaminerToExam(exam, examiner);
        return "redirect:/exam/{examId}/examiner";
    }

    @PutMapping("/remove")
    public String removeExaminerFromExam(@ModelAttribute Exam exam, @ModelAttribute Examiner examiner) {
        examService.removeExaminerFromExam(exam, examiner);
        return "redirect:/exam/{examId}/examiner";
    }
}