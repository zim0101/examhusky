package com.app.examhusky.repository;

import com.app.examhusky.model.Examiner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExaminerRepository extends JpaRepository<Examiner, Integer> {
    Page<Examiner> findAllByDeletedFalse(Pageable pageable);

    Page<Examiner> findByExams_Id(Integer examId, Pageable pageable);

    // TODO: MUST!!! Optimize this query: dont select unnecessary columns
    //  and map with a Dto with only the necessary fields!

    /**
     * Retrieves a pageable list of examiners who are not assigned to a specific exam.
     *
     * @param examId   The ID of the exam to check for examiner assignments.
     * @param pageable Pagination information.
     * @return A pageable list of examiners not assigned to the specified exam.
     */
    @Query("SELECT e FROM Examiner e LEFT JOIN e.exams ex WHERE ex.id <> :examId OR ex.id IS NULL")
    Page<Examiner> findExaminersNotAssignedToExam(@Param("examId") Integer examId, Pageable pageable);
}
