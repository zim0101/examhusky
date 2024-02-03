package com.app.examhusky.controller.exam;

import com.app.examhusky.model.CandidateExamResult;
import com.app.examhusky.model.Exam;
import com.app.examhusky.service.CandidateExamResultService;
import com.app.examhusky.service.ExamService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

@Controller
@RequestMapping("/examination/exam/{id}")
public class ExaminationListController {

    private final ExamService examService;
    private final CandidateExamResultService candidateExamResultService;

    public ExaminationListController(ExamService examService, CandidateExamResultService candidateExamResultService) {
        this.examService = examService;
        this.candidateExamResultService = candidateExamResultService;
    }

    @ModelAttribute
    public Exam addExamToModel(@PathVariable Integer id) {
        return examService.findById(id);
    }

    @ModelAttribute("candidateExamResults")
    public Page<CandidateExamResult>
    addExamResultPageToModel(@ModelAttribute Exam exam,
                             HttpSession session,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size,
                             @RequestParam("sortField") Optional<String> sortField,
                             @RequestParam("orderBy") Optional<String> orderBy) throws AccessDeniedException {
        return candidateExamResultService.findAllResultByExamId(exam, session, page, size, sortField, orderBy);
    }

    @GetMapping("/examine")
    public String examine() {
        return "/result/list";
    }
}