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
@RequestMapping("/exam")
public class ExamListController {
    private final ExamService examService;

    public ExamListController(ExamService examService) {
        this.examService = examService;
    }

    @ModelAttribute("exams")
    public Page<Exam> addExamPageToModel(HttpSession session,
                                   @RequestParam("page") Optional<Integer> page,
                                   @RequestParam("size") Optional<Integer> size,
                                   @RequestParam("sortField") Optional<String> sortField,
                                   @RequestParam("orderBy") Optional<String> orderBy) {
        return examService.sortAndPaginateAllActiveExams(session, page,
                size, sortField, orderBy);
    }

    @GetMapping
    public String paginatedList() {
        return "exam/list";
    }
}