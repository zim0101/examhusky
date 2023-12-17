package com.app.examhusky.controller.exam;

import com.app.examhusky.model.Exam;
import com.app.examhusky.model.Examiner;
import com.app.examhusky.service.ExamService;
import com.app.examhusky.service.ExaminerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
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

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ModelAttribute("exam")
    public Exam addExamToModel(@PathVariable("examId") Integer id){
        return examService.findById(id);
    }

    @ModelAttribute("examExaminerList")
    public Page<Examiner>
    addExamExaminerListToModel(@PathVariable("examId") Integer id,
                               HttpSession session,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size,
                               @RequestParam("sortField") Optional<String> sortField,
                               @RequestParam("orderBy") Optional<String> orderBy) {

        return examinerService.findExaminersOfExam(id, session, page, size, sortField, orderBy);
    }

    @ModelAttribute("availableExaminerList")
    public Page<Examiner>
    addAvailableExaminerListToModel(@PathVariable("examId") Integer id,
                               HttpSession session,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size,
                               @RequestParam("sortField") Optional<String> sortField,
                               @RequestParam("orderBy") Optional<String> orderBy) {

        return examinerService.findAvailableExaminersForExam(id, session, page, size, sortField,
                orderBy);
    }

    @GetMapping
    public String renderExamWithExaminerView(@ModelAttribute Exam exam) {
        return "exam/examiners";
    }
}