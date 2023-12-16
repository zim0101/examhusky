package com.app.examhusky.controller.exam;

import com.app.examhusky.service.ExamService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exam/new")
public class ExamNewController {
    private final ExamService examService;

    public ExamNewController(ExamService examService) {
        this.examService = examService;
    }
}