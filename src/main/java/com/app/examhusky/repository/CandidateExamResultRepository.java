package com.app.examhusky.repository;

import com.app.examhusky.model.CandidateExamResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateExamResultRepository extends JpaRepository<CandidateExamResult, Integer> {
    CandidateExamResult findByExamIdAndCandidateId(Integer examId, Integer candidateId);

    Page<CandidateExamResult> findByExamId(Integer examId, Pageable pageable);
}
