package com.app.examhusky.controller.exam;

import com.app.examhusky.model.Exam;
import com.app.examhusky.model.Question;
import com.app.examhusky.service.ExamService;
import com.app.examhusky.service.QuestionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/exam/{examId}/question")
public class ExamQuestionListController {
    private final ExamService examService;
    private final QuestionService questionService;

    public ExamQuestionListController(ExamService examService, QuestionService questionService) {
        this.examService = examService;
        this.questionService = questionService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ModelAttribute("exam")
    public Exam addExamToModel(@PathVariable("examId") Integer id){
        return examService.findById(id);
    }

    @ModelAttribute("examQuestionList")
    public Page<Question>
    addExamQuestionListToModel(@PathVariable Integer examId,
                                HttpSession session,
                                @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size,
                                @RequestParam("sortField") Optional<String> sortField,
                                @RequestParam("orderBy") Optional<String> orderBy) {

        return questionService.findQuestionsOfExam(examId, session, page, size, sortField, orderBy);
    }

    @ModelAttribute("availableQuestionList")
    public Page<Question>
    addAvailableQuestionListToModel(@PathVariable Integer examId,
                                     HttpSession session,
                                     @RequestParam("page") Optional<Integer> page,
                                     @RequestParam("size") Optional<Integer> size,
                                     @RequestParam("sortField") Optional<String> sortField,
                                     @RequestParam("orderBy") Optional<String> orderBy) {

        return questionService.findAvailableQuestionsForExam(examId, session, page, size, sortField,
                orderBy);
    }

    @GetMapping
    public String renderExamWithQuestionView(@ModelAttribute Exam exam) {
        return "exam/questions";
    }
}