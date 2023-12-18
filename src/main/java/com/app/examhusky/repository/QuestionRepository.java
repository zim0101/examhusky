package com.app.examhusky.repository;

import com.app.examhusky.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Page<Question> findAllByDeletedFalse(Pageable pageable);

    Page<Question> findByExams_Id(Integer examId, Pageable pageable);

    // TODO: MUST!!! Optimize this query: dont select unnecessary columns
    //  and map with a Dto with only the necessary fields!

    /**
     * Retrieves a pageable list of questions who are not assigned to a specific exam.
     *
     * @param examId   The ID of the exam to check for question assignments.
     * @param pageable Pagination information.
     * @return A pageable list of questions not assigned to the specified exam.
     */
    @Query("SELECT e FROM Question e LEFT JOIN e.exams ex WHERE ex.id <> :examId OR ex.id IS NULL")
    Page<Question> findCandidatesNotAssignedToExam(Integer examId, Pageable pageable);
}
