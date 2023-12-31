package com.app.examhusky.controller.candidate;

import com.app.examhusky.model.CandidateExamAnswerRecord;
import com.app.examhusky.model.Exam;
import com.app.examhusky.service.CandidateExamAnswerRecordService;
import com.app.examhusky.service.CandidateService;
import com.app.examhusky.service.ExamService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/candidate/exam/{id}")
public class CandidateExamAnswerRecordListController {
    private final ExamService examService;
    private final CandidateService candidateService;
    private final CandidateExamAnswerRecordService candidateExamAnswerRecordService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    public CandidateExamAnswerRecordListController(ExamService examService,
                                                   CandidateService candidateService,
                                                   CandidateExamAnswerRecordService candidateExamAnswerRecordService) {
        this.examService = examService;
        this.candidateService = candidateService;
        this.candidateExamAnswerRecordService = candidateExamAnswerRecordService;
    }

    @ModelAttribute("exam")
    public Exam addExamToModel(@PathVariable Integer id){
        return examService.findById(id);
    }

    @ModelAttribute("candidateExamAnswerRecordList")
    public List<CandidateExamAnswerRecord> addCandidateExamAnswerRecordListToModel(@PathVariable Integer id) {

        return candidateExamAnswerRecordService.getQuestionAndAnswerRecordOfCandidateExam(id,
                candidateService.findCandidateByCurrentAuthAccount().getId());
    }

    @GetMapping
    public String renderCandidateExamPaper() {
        return "candidate/exam_paper";
    }
}