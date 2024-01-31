package com.app.examhusky.controller.exam;

import com.app.examhusky.model.Candidate;
import com.app.examhusky.model.Exam;
import com.app.examhusky.model.Examiner;
import com.app.examhusky.service.CandidateService;
import com.app.examhusky.service.ExamService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/exam/{examId}/candidate")
public class ExamCandidateListController {
    private final ExamService examService;
    private final CandidateService candidateService;

    public ExamCandidateListController(ExamService examService, CandidateService candidateService) {
        this.examService = examService;
        this.candidateService = candidateService;
    }

    @ModelAttribute
    public Exam addExamToModel(@PathVariable("examId") Integer id){
        return examService.findById(id);
    }

    @ModelAttribute
    public Page<Candidate>
    addExamCandidateListToModel(@PathVariable("examId") Integer id,
                               HttpSession session,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size,
                               @RequestParam("sortField") Optional<String> sortField,
                               @RequestParam("orderBy") Optional<String> orderBy) {

        return candidateService.findCandidatesOfExam(id, session, page, size, sortField, orderBy);
    }

    @ModelAttribute
    public Page<Candidate>
    addAvailableCandidateListToModel(@PathVariable("examId") Integer id,
                                    HttpSession session,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size,
                                    @RequestParam("sortField") Optional<String> sortField,
                                    @RequestParam("orderBy") Optional<String> orderBy) {

        return candidateService.findAvailableCandidatesForExam(id, session, page, size, sortField,
                orderBy);
    }

    @GetMapping
    public String renderExamWithCandidateView(@ModelAttribute Exam exam) {
        return "exam/candidates";
    }
}