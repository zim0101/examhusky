package com.app.examhusky.controller.exam;

import com.app.examhusky.service.CandidateExamResultService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/examination/exam/id/")
public class ExaminationListController {
    private final CandidateExamResultService candidateExamResultService;

    public ExaminationListController(CandidateExamResultService candidateExamResultService) {
        this.candidateExamResultService = candidateExamResultService;
    }

    @GetMapping("/examine")
    public String examine() {

        return "";
    }
}