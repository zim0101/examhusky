package com.app.examhusky.controller.exam;

import com.app.examhusky.service.ExamService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exam/{id}")
public class ExamEditController {
    private final ExamService examService;

    public ExamEditController(ExamService examService) {
        this.examService = examService;
    }
}