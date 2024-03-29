package com.app.examhusky.controller.exam;

import com.app.examhusky.model.Candidate;
import com.app.examhusky.model.CandidateExamAnswerRecord;
import com.app.examhusky.model.Exam;
import com.app.examhusky.security.EncryptionService;
import com.app.examhusky.service.CandidateExamAnswerRecordService;
import com.app.examhusky.service.CandidateExamResultService;
import com.app.examhusky.service.CandidateService;
import com.app.examhusky.service.ExamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/examination/exam/{examId}/candidate/{candidateId}")
public class ExaminationEditController {

    private static final Logger log = LoggerFactory.getLogger(ExaminationEditController.class);
    private final ExamService examService;
    private final CandidateService candidateService;
    private final CandidateExamAnswerRecordService candidateExamAnswerRecordService;
    private final CandidateExamResultService candidateExamResultService;

    public ExaminationEditController(ExamService examService,
                                     CandidateService candidateService,
                                     CandidateExamAnswerRecordService candidateExamAnswerRecordService,
                                     CandidateExamResultService candidateExamResultService) {
        this.examService = examService;
        this.candidateService = candidateService;
        this.candidateExamAnswerRecordService = candidateExamAnswerRecordService;
        this.candidateExamResultService = candidateExamResultService;
    }

    @ModelAttribute
    public Exam addExamToModel(@PathVariable Integer examId){
        return examService.findById(examId);
    }

    @ModelAttribute
    public Candidate addCandidateToModel(@PathVariable Integer candidateId){
        return candidateService.findById(candidateId);
    }

    @ModelAttribute
    public List<CandidateExamAnswerRecord> addCandidateExamAnswerRecordList(@ModelAttribute Exam exam,
                                                                            @ModelAttribute Candidate candidate) {
        return candidateExamAnswerRecordService.getQuestionAndAnswerRecordOfCandidate(exam, candidate);
    }

    @GetMapping
    public String renderExaminationEditView() {
        return "/result/edit";
    }

    @PutMapping
    public String
    updateCandidateAnswerMarks(@ModelAttribute Exam exam,
                               @ModelAttribute Candidate candidate,
                               @RequestParam("recordId") List<String> recordIds,
                               @RequestParam("marks") List<Integer> marks) throws EncryptionService.EncryptionException {
        candidateExamAnswerRecordService.submitMarksForCandidate(exam, candidate, recordIds, marks);

        return "redirect:/examination/exam/{examId}/candidate/{candidateId}";
    }

    @PutMapping("/confirm-examination")
    public String confirmExamination(@ModelAttribute Exam exam, @ModelAttribute Candidate candidate) {
        candidateExamResultService.confirmExaminationForCandidate(exam, candidate);
        return "redirect:/examination/exam/{examId}/examine";
    }

    @PutMapping("/recommend")
    public String recommendCandidate(@ModelAttribute Exam exam, @ModelAttribute Candidate candidate) {
        candidateExamResultService.recommendCandidateFromExam(exam, candidate);
        return "redirect:/examination/exam/{examId}/examine";
    }

    @PutMapping("/not-recommend")
    public String notRecommendCandidate(@ModelAttribute Exam exam, @ModelAttribute Candidate candidate) {
        candidateExamResultService.notRecommendCandidateFromExam(exam, candidate);
        return "redirect:/examination/exam/{examId}/examine";
    }
}