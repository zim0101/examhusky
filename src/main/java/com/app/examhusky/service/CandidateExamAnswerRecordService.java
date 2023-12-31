package com.app.examhusky.service;

import com.app.examhusky.controller.candidate.CandidateExamAnswerRecordController;
import com.app.examhusky.model.Candidate;
import com.app.examhusky.model.CandidateExamAnswerRecord;
import com.app.examhusky.model.Exam;
import com.app.examhusky.repository.CandidateExamAnswerRecordRepository;
import com.app.examhusky.repository.ExamRepository;
import com.app.examhusky.security.EncryptionService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CandidateExamAnswerRecordService {
    private static final Logger log = LoggerFactory.getLogger(CandidateExamAnswerRecordService.class);
    private final CandidateExamAnswerRecordRepository candidateExamAnswerRecordRepository;
    private final ExamRepository examRepository;
    private final EncryptionService encryptionService;

    public CandidateExamAnswerRecordService(CandidateExamAnswerRecordRepository candidateExamAnswerRecordRepository,
                                            ExamRepository examRepository,
                                            EncryptionService encryptionService) {
        this.candidateExamAnswerRecordRepository = candidateExamAnswerRecordRepository;
        this.examRepository = examRepository;
        this.encryptionService = encryptionService;
    }

    public List<CandidateExamAnswerRecord> getQuestionAndAnswerRecordOfCandidateExam(Integer examId,
                                                                                     Integer candidateId) {
        return candidateExamAnswerRecordRepository.findByExamIdAndCandidateId(examId, candidateId);
    }

    public CandidateExamAnswerRecord findById(Integer id) {
        return candidateExamAnswerRecordRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException("Candidate answer paper not found!"));
    }

    @Transactional
    public void initExamPaperForAllCandidatesOfExam(Integer examId) {
        Exam exam = examRepository.findById(examId).orElseThrow(() -> new EntityNotFoundException("Exam Not Found!"));
        exam.getCandidates().forEach(candidate -> {
            initExamPaperForCandidate(exam, candidate);
        });
    }

    @Transactional
    public void initExamPaperForCandidate(Exam exam, Candidate candidate) {
        exam.getQuestions().forEach(question -> {
            CandidateExamAnswerRecord candidateExamAnswerRecord = new CandidateExamAnswerRecord();
            candidateExamAnswerRecord.setCandidate(candidate);
            candidateExamAnswerRecord.setExam(exam);
            candidateExamAnswerRecord.setQuestion(question);
            candidateExamAnswerRecord.setAnswer("");
            candidateExamAnswerRecordRepository.save(candidateExamAnswerRecord);
        });
    }

    @Transactional
    public void submitOrUpdateAnswerPaper(List<String> recordIds,
                                          List<String> answers) throws EncryptionService.EncryptionException {
        // TODO: check submit time
        for (int i = 0; i < recordIds.size(); i++) {
            Integer decryptedId = Integer.valueOf(encryptionService.decrypt(recordIds.get(i)));
            log.info("DecryptedId: {}", decryptedId);
            CandidateExamAnswerRecord candidateExamAnswerRecord = findById(decryptedId);
            candidateExamAnswerRecord.setAnswer(answers.get(i));
            candidateExamAnswerRecordRepository.save(candidateExamAnswerRecord);
        }
    }
}