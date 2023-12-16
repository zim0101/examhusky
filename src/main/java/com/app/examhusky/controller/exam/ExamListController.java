package com.app.examhusky.controller.exam;

import com.app.examhusky.service.ExamService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exam")
public class ExamListController {
    private final ExamService examService;

    public ExamListController(ExamService examService) {
        this.examService = examService;
    }
}