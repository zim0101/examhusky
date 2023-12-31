package com.app.examhusky.controller.exam;

import com.app.examhusky.model.Candidate;
import com.app.examhusky.model.CandidateExamAnswerRecord;
import com.app.examhusky.model.Exam;
import com.app.examhusky.security.EncryptionService;
import com.app.examhusky.service.CandidateExamAnswerRecordService;
import com.app.examhusky.service.CandidateService;
import com.app.examhusky.service.ExamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/examination/exam/{examId}/candidate/{candidateId}")
@Slf4j
public class ExaminationEditController {
    private final ExamService examService;
    private final CandidateService candidateService;
    private final CandidateExamAnswerRecordService candidateExamAnswerRecordService;

    public ExaminationEditController(ExamService examService,
                                     CandidateService candidateService,
                                     CandidateExamAnswerRecordService candidateExamAnswerRecordService) {
        this.examService = examService;
        this.candidateService = candidateService;
        this.candidateExamAnswerRecordService = candidateExamAnswerRecordService;
    }

    @ModelAttribute("exam")
    public Exam addExamToModel(@PathVariable Integer examId){
        return examService.findById(examId);
    }

    @ModelAttribute("candidate")
    public Candidate addCandidateToModel(@PathVariable Integer candidateId){
        return candidateService.findById(candidateId);
    }

    @ModelAttribute("candidateExamAnswerRecordList")
    public List<CandidateExamAnswerRecord> addCandidateExamAnswerRecordList(@PathVariable Integer examId,
                                                                            @PathVariable Integer candidateId) {
        return candidateExamAnswerRecordService.getQuestionAndAnswerRecordOfCandidate(examId, candidateId);
    }

    @GetMapping
    public String renderExaminationEditView() {
        return "/result/edit";
    }

    @PutMapping
    public String
    updateCandidateAnswerMarks(@PathVariable Integer examId,
                               @PathVariable Integer candidateId,
                               @RequestParam("recordId") List<String> recordIds,
                               @RequestParam("marks") List<Integer> marks) throws EncryptionService.EncryptionException {
        candidateExamAnswerRecordService.submitMarksForCandidate(examId, candidateId, recordIds, marks);

        return "redirect:/examination/exam/{examId}/candidate/{candidateId}";
    }
}