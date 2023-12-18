package com.app.examhusky.controller.exam;

import com.app.examhusky.service.ExamService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exam/{examId}/candidate/{candidateId}")
public class ExamCandidateEditController {
    private final ExamService examService;

    public ExamCandidateEditController(ExamService examService) {
        this.examService = examService;
    }

    @PutMapping("/add")
    public String addCandidateToExam(@PathVariable Integer examId, @PathVariable Integer candidateId) {
        examService.addCandidateToExam(candidateId, examId);
        return "redirect:/exam/{examId}/candidate";
    }

    @PutMapping("/remove")
    public String removeCandidateFromExam(@PathVariable Integer examId, @PathVariable Integer candidateId) {
        examService.removeCandidateFromExam(candidateId, examId);
        return "redirect:/exam/{examId}/candidate";
    }
}