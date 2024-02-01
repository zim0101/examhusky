package com.app.examhusky.service;

import com.app.examhusky.model.Candidate;
import com.app.examhusky.model.CandidateExamAnswerRecord;
import com.app.examhusky.model.Exam;
import com.app.examhusky.repository.CandidateExamAnswerRecordRepository;
import com.app.examhusky.security.EncryptionService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CandidateExamAnswerRecordService {

    private static final Logger log = LoggerFactory.getLogger(CandidateExamAnswerRecordService.class);
    private final CandidateExamAnswerRecordRepository candidateExamAnswerRecordRepository;
    private final CandidateExamResultService candidateExamResultService;
    private final CandidateService candidateService;
    private final EncryptionService encryptionService;

    public CandidateExamAnswerRecordService(CandidateExamAnswerRecordRepository candidateExamAnswerRecordRepository,
                                            CandidateExamResultService candidateExamResultService,
                                            CandidateService candidateService,
                                            EncryptionService encryptionService) {
        this.candidateExamAnswerRecordRepository = candidateExamAnswerRecordRepository;
        this.candidateExamResultService = candidateExamResultService;
        this.candidateService = candidateService;
        this.encryptionService = encryptionService;
    }

    public List<CandidateExamAnswerRecord> getQuestionAndAnswerRecordOfCandidate(Integer examId,
                                                                                     Integer candidateId) {
        List<CandidateExamAnswerRecord> records = candidateExamAnswerRecordRepository.findByExamIdAndCandidateId(examId,
                candidateId);
        return encryptCandidateExamAnswerRecordIds(records);
    }

    public List<CandidateExamAnswerRecord> getQuestionAndAnswerRecordOfExamForAuthenticatedCandidate(Integer examId) {
        Integer candidateId = candidateService.findCandidateByCurrentAuthAccount().getId();

        if (!candidateService.isCandidateAssignedToExam(candidateId, examId)) {
            throw new AccessDeniedException("You dont have permission to access this exam resource");
        }

        return getQuestionAndAnswerRecordOfCandidate(
                examId, candidateService.findCandidateByCurrentAuthAccount().getId());
    }

    public List<CandidateExamAnswerRecord>
    encryptCandidateExamAnswerRecordIds(List<CandidateExamAnswerRecord> records) {
        records.forEach((record) -> {
            try {
                record.setEncryptedId(encryptionService.encrypt(record.getId().toString()));
            } catch (EncryptionService.EncryptionException e) {
                throw new RuntimeException(e);
            }
        });

        return records;
    }

    public CandidateExamAnswerRecord findById(Integer id) {
        return candidateExamAnswerRecordRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException("Candidate answer paper not found!"));
    }

    @Transactional
    public void initExamPaperForAuthenticatedCandidate(Exam exam) {
        Candidate candidate = candidateService.findCandidateByCurrentAuthAccount();
        initExamPaperForCandidate(exam, candidate);
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

    @Transactional
    public void submitMarksForCandidate(Integer examId,
                                        Integer candidateId,
                                        List<String> recordIdList,
                                        List<Integer> marksList) throws EncryptionService.EncryptionException {
        Integer totalMarks = 0;

        for (int i = 0; i < recordIdList.size(); i++) {
            Integer decryptedId = Integer.valueOf(encryptionService.decrypt(recordIdList.get(i)));
            log.info("DecryptedId: {}", decryptedId);
            CandidateExamAnswerRecord candidateExamAnswerRecord = findById(decryptedId);
            candidateExamAnswerRecord.setMarks(marksList.get(i));
            candidateExamAnswerRecordRepository.save(candidateExamAnswerRecord);
            totalMarks += marksList.get(i);
        }

        candidateExamResultService.updateTotalMarksOfCandidateForExam(examId, candidateId, totalMarks);
    }
}