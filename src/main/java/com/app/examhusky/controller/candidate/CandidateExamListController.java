package com.app.examhusky.controller.exam;

import com.app.examhusky.model.Exam;
import com.app.examhusky.service.ExamService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/candidate/exams")
public class CandidateExamListController {
    private final ExamService examService;

    public CandidateExamListController(ExamService examService) {
        this.examService = examService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ModelAttribute("examList")
    public Page<Exam> addExamListToModel(HttpSession session,
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