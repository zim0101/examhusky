package com.app.examhusky.controller.exam;

import com.app.examhusky.model.CandidateExamResult;
import com.app.examhusky.service.CandidateExamResultService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

@Controller
@RequestMapping("/examination/exam/{id}")
public class ExaminationListController {
    private final CandidateExamResultService candidateExamResultService;

    public ExaminationListController(CandidateExamResultService candidateExamResultService) {
        this.candidateExamResultService = candidateExamResultService;
    }

    @ModelAttribute("examResultList")
    public Page<CandidateExamResult>
    addExamResultListToModel(@PathVariable Integer id,
                             HttpSession session,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size,
                             @RequestParam("sortField") Optional<String> sortField,
                             @RequestParam("orderBy") Optional<String> orderBy) throws AccessDeniedException {
        return candidateExamResultService.findAllResultByExamId(id, session, page, size, sortField, orderBy);
    }

    @GetMapping("/examine")
    public String examine() {
        return "/result/list";
    }
}