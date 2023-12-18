package com.app.examhusky.controller.exam;

import com.app.examhusky.service.ExamService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exam/{examId}/question/{questionId}")
public class ExamQuestionEditController {
    private final ExamService examService;

    public ExamQuestionEditController(ExamService examService) {
        this.examService = examService;
    }

    @PutMapping("/add")
    public String addQuestionToExam(@PathVariable Integer examId, @PathVariable Integer questionId) {
        examService.addQuestionToExam(questionId, examId);
        return "redirect:/exam/{examId}/question";
    }

    @PutMapping("/remove")
    public String removeQuestionFromExam(@PathVariable Integer examId, @PathVariable Integer questionId) {
        examService.removeQuestionFromExam(questionId, examId);
        return "redirect:/exam/{examId}/question";
    }
}