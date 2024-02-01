package com.app.examhusky.controller.exam;

import com.app.examhusky.model.Exam;
import com.app.examhusky.model.Question;
import com.app.examhusky.service.ExamService;
import com.app.examhusky.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exam/{examId}/question/{questionId}")
public class ExamQuestionEditController {
    private final ExamService examService;
    private final QuestionService questionService;

    public ExamQuestionEditController(ExamService examService, QuestionService questionService) {
        this.examService = examService;
        this.questionService = questionService;
    }

    @ModelAttribute
    public Exam addExamToModel(@PathVariable Integer examId) {
        return examService.findById(examId);
    }

    @ModelAttribute
    public Question addQuestionToModel(@PathVariable Integer questionId) {
        return questionService.findById(questionId);
    }

    @PutMapping("/add")
    public String addQuestionToExam(@ModelAttribute Exam exam, @ModelAttribute Question question) {
        examService.addQuestionToExam(exam, question);
        return "redirect:/exam/{examId}/question";
    }

    @PutMapping("/remove")
    public String removeQuestionFromExam(@PathVariable Integer examId, @PathVariable Integer questionId) {
        examService.removeQuestionFromExam(questionId, examId);
        return "redirect:/exam/{examId}/question";
    }
}