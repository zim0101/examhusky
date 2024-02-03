package com.app.examhusky.controller.exam;

import com.app.examhusky.model.Candidate;
import com.app.examhusky.model.Exam;
import com.app.examhusky.service.CandidateService;
import com.app.examhusky.service.ExamService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exam/{examId}/candidate/{candidateId}")
public class ExamCandidateEditController {
    private final ExamService examService;
    private final CandidateService candidateService;

    public ExamCandidateEditController(ExamService examService, CandidateService candidateService) {
        this.examService = examService;
        this.candidateService = candidateService;
    }

    @ModelAttribute
    public Exam addExamToModel(@PathVariable Integer examId) {
        return examService.findById(examId);
    }

    @ModelAttribute
    public Candidate addCandidateToModel(@PathVariable Integer candidateId) {
        return candidateService.findById(candidateId);
    }

    @PutMapping("/add")
    public String addCandidateToExam(@ModelAttribute Exam exam, @ModelAttribute Candidate candidate) {
        examService.addCandidateToExam(candidate, exam);
        return "redirect:/exam/{examId}/candidate";
    }

    @PutMapping("/remove")
    public String removeCandidateFromExam(@ModelAttribute Exam exam, @ModelAttribute Candidate candidate) {
        examService.removeCandidateFromExam(candidate, exam);
        return "redirect:/exam/{examId}/candidate";
    }
}