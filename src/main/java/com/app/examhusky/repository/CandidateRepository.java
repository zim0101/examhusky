package com.app.examhusky.repository;

import com.app.examhusky.model.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

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
    @Query("SELECT e FROM Candidate e LEFT JOIN e.exams ex WHERE ex.id <> :examId OR ex.id IS NULL")
    Page<Candidate> findCandidatesNotAssignedToExam(Integer examId, Pageable pageable);
}
