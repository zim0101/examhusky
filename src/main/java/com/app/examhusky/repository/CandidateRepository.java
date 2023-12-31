package com.app.examhusky.repository;

import com.app.examhusky.model.Account;
import com.app.examhusky.model.Candidate;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

    Optional<Candidate> findByAccount(@NotNull Account account);

    Page<Candidate> findAllByDeletedFalse(Pageable pageable);

    Page<Candidate> findByExams_Id(Integer examId, Pageable pageable);

    // TODO: MUST!!! Optimize this query: dont select unnecessary columns
    //  and map with a Dto with only the necessary fields!

    /**
     * Retrieves a pageable list of candidates who are not assigned to a specific exam.
     *
     * @param examId   The ID of the exam to check for candidate assignments.
     * @param pageable Pagination information.
     * @return A pageable list of candidates not assigned to the specified exam.
     */
    @Query("""
        select candidate
        from Candidate candidate
        where not exists (
            select 1
            from Exam exam
            join exam.candidates as candidates
            where exam.id = :examId
            and candidate.id = candidates.id
        )
    """)
    Page<Candidate> findCandidatesNotAssignedToExam(Integer examId, Pageable pageable);

    @Query("SELECT COUNT(c) > 0 FROM Candidate c JOIN c.exams e WHERE c.id = :candidateId AND e.id = :examId")
    boolean isCandidateAssignedToExam(@Param("candidateId") Integer candidateId, @Param("examId") Integer examId);
}
