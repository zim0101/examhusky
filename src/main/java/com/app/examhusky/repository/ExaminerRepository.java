package com.app.examhusky.repository;

import com.app.examhusky.model.Account;
import com.app.examhusky.model.Examiner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
//    @Query("SELECT e FROM Examiner e JOIN e.exams ex WHERE ex.id <> :examId OR ex.id IS NULL")
//    @Query("""
//            select examiner from Examiner examiner join examiner.exams as exams where exams.id != :examId""")

    @Query("""
        select examiner
        from Examiner examiner
        where not exists (
            select 1
            from Exam exam
            join exam.examiners as examiners
            where exam.id = :examId
            and examiner.id = examiners.id
        )
    """)
    Page<Examiner> findExaminersNotAssignedToExam(@Param("examId") Integer examId, Pageable pageable);

    Optional<Examiner> findByAccount(Account account);

    @Query("""
        SELECT COUNT(examiner) > 0 FROM Examiner examiner\s
        JOIN examiner.exams exam WHERE examiner.id = :examinerId AND exam.id = :examId
        """)
    boolean isExaminerAssignedToExam(@Param("examinerId") Integer examinerId, @Param("examId") Integer examId);
}
