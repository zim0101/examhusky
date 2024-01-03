package com.app.examhusky.repository;

import com.app.examhusky.model.CandidateExamAnswerRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateExamAnswerRecordRepository extends JpaRepository<CandidateExamAnswerRecord, Integer> {
    List<CandidateExamAnswerRecord> findByExamIdAndCandidateId(Integer examId, Integer candidateId);
    CandidateExamAnswerRecord findByExamIdAndCandidateIdAndQuestionId(Integer examId, Integer candidateId,
                                                                            Integer questionId);
    List<CandidateExamAnswerRecord> findByExamIdAndQuestionId(Integer examId, Integer questionId);
}
