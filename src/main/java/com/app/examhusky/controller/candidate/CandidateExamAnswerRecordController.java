package com.app.examhusky.controller.candidate;

import com.app.examhusky.model.Candidate;
import com.app.examhusky.model.CandidateExamAnswerRecord;
import com.app.examhusky.model.Exam;
import com.app.examhusky.security.EncryptionService;
import com.app.examhusky.service.CandidateExamAnswerRecordService;
import com.app.examhusky.service.CandidateService;
import com.app.examhusky.service.ExamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/candidate/exam/{id}")
@PreAuthorize("hasRole('ROLE_CANDIDATE')")
public class CandidateExamAnswerRecordController {
    private static final Logger log = LoggerFactory.getLogger(CandidateExamAnswerRecordController.class);
    private final ExamService examService;
    private final CandidateService candidateService;
    private final CandidateExamAnswerRecordService candidateExamAnswerRecordService;
    private final EncryptionService encryptionService;

    public CandidateExamAnswerRecordController(ExamService examService,
                                               CandidateService candidateService,
                                               CandidateExamAnswerRecordService candidateExamAnswerRecordService,
                                               EncryptionService encryptionService) {
        this.examService = examService;
        this.candidateService = candidateService;
        this.candidateExamAnswerRecordService = candidateExamAnswerRecordService;
        this.encryptionService = encryptionService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ModelAttribute("candidate")
    public Candidate addCandidateToModel(){
        return candidateService.findCandidateByCurrentAuthAccount();
    }

    @ModelAttribute("exam")
    public Exam addExamToModel(@PathVariable Integer id){
        return examService.findById(id);
    }

    @ModelAttribute("candidateExamAnswerRecordList")
    public List<CandidateExamAnswerRecord> addCandidateExamAnswerRecordListToModel(@PathVariable Integer id) {
        List<CandidateExamAnswerRecord> records = candidateExamAnswerRecordService
                .getQuestionAndAnswerRecordOfCandidateExam(
                        id,
                        candidateService.findCandidateByCurrentAuthAccount().getId()
                );

        records.forEach((record) -> {
            try {
                record.setEncryptedId(encryptionService.encrypt(record.getId().toString()));
            } catch (EncryptionService.EncryptionException e) {
                throw new RuntimeException(e);
            }
        });

        return records;
    }

    @GetMapping
    public String renderCandidateExamPaper() {
        return "candidate/exam_paper";
    }

    @PutMapping
    public String updateCandidateExamAnswers(@RequestParam("recordId") List<String> recordIds,
                                             @RequestParam("answer") List<String> answers) {
        try {
            candidateExamAnswerRecordService.submitOrUpdateAnswerPaper(
                    recordIds, answers
            );
        } catch (EncryptionService.EncryptionException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/candidate/exam/{id}?success";
    }
}