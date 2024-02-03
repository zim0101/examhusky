package com.app.examhusky.controller.exam;

import com.app.examhusky.model.Exam;
import com.app.examhusky.model.Examiner;
import com.app.examhusky.service.ExamService;
import com.app.examhusky.service.ExaminerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/exam/{examId}/examiner")
public class ExamExaminerListController {
    private final ExamService examService;
    private final ExaminerService examinerService;

    public ExamExaminerListController(ExamService examService, ExaminerService examinerService) {
        this.examService = examService;
        this.examinerService = examinerService;
    }

    @ModelAttribute
    public Exam addExamToModel(@PathVariable Integer examId){
        return examService.findById(examId);
    }

    @ModelAttribute("examExaminers")
    public Page<Examiner>
    addExamExaminerPageToModel(@ModelAttribute Exam exam,
                               HttpSession session,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size,
                               @RequestParam("sortField") Optional<String> sortField,
                               @RequestParam("orderBy") Optional<String> orderBy) {

        return examinerService.findExaminersOfExam(exam, session, page, size, sortField, orderBy);
    }

    @ModelAttribute("availableExaminers")
    public Page<Examiner>
    addAvailableExaminerPageToModel(@ModelAttribute Exam exam,
                               HttpSession session,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size,
                               @RequestParam("sortField") Optional<String> sortField,
                               @RequestParam("orderBy") Optional<String> orderBy) {

        return examinerService.findAvailableExaminersForExam(exam, session, page, size, sortField,
                orderBy);
    }

    @GetMapping
    public String renderExamWithExaminerView(@ModelAttribute Exam exam) {
        return "exam/examiners";
    }
}