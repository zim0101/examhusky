package com.app.examhusky.controller.candidate;

import com.app.examhusky.model.Exam;
import com.app.examhusky.service.ExamService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/candidate/exams")
@PreAuthorize("hasRole('ROLE_CANDIDATE')")
public class CandidateExamListController {
    private final ExamService examService;

    public CandidateExamListController(ExamService examService) {
        this.examService = examService;
    }

    @ModelAttribute("exams")
    public Page<Exam> addExamPageToModel(HttpSession session,
                                         @RequestParam("page") Optional<Integer> page,
                                         @RequestParam("size") Optional<Integer> size,
                                         @RequestParam("sortField") Optional<String> sortField,
                                         @RequestParam("orderBy") Optional<String> orderBy) {

        return examService.sortAndPaginateAllExamsOfCandidate(session, page,
                size, sortField, orderBy);
    }

    @GetMapping
    public String paginatedList() {
        return "candidate/exam";
    }
}