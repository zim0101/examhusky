package com.app.examhusky.controller.exam;

import com.app.examhusky.service.ExamService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exam/{examId}/examiner/{examinerId}")
public class ExamExaminerEditController {
    private final ExamService examService;

    public ExamExaminerEditController(ExamService examService) {
        this.examService = examService;
    }

    @PutMapping("/add")
    public String addExaminerToExam(@PathVariable Integer examId, @PathVariable Integer examinerId) {
        examService.addExaminerToExam(examinerId, examId);
        return "redirect:/exam/{examId}/examiner";
    }

    @PutMapping("/remove")
    public String removeExaminerFromExam(@PathVariable Integer examId, @PathVariable Integer examinerId) {
        examService.removeExaminerFromExam(examinerId, examId);
        return "redirect:/exam/{examId}/examiner";
    }
}