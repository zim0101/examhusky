package com.app.examhusky.service;

import com.app.examhusky.model.Exam;
import com.app.examhusky.model.Examiner;
import com.app.examhusky.model.enums.ExamState;
import com.app.examhusky.repository.ExamRepository;
import com.app.examhusky.repository.ExaminerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ExamService {
    private final ExamRepository examRepository;
    private final ExaminerRepository examinerRepository;
    private final SortingAndPaginationService sortingAndPaginationService;
    private static final Logger log = LoggerFactory.getLogger(ExamService.class);

    public ExamService(ExamRepository examRepository,
                       ExaminerRepository examinerRepository,
                       SortingAndPaginationService sortingAndPaginationService) {
        this.examRepository = examRepository;
        this.examinerRepository = examinerRepository;
        this.sortingAndPaginationService = sortingAndPaginationService;
    }

    public Exam findById(Integer id) {
        return examRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Exam not found!"));
    }

    public Page<Exam> sortAndPaginateAllActiveExams(HttpSession session,
                                          Optional<Integer> page,
                                          Optional<Integer> size,
                                          Optional<String> sortField,
                                          Optional<String> orderBy) {
        sortingAndPaginationService.removePageAndSortingDataFromSessionOnReload(
                session, page, size, sortField, orderBy);

        Pageable pageable = sortingAndPaginationService.buildPageable(
                sortingAndPaginationService.getPageFromSession(session, page),
                size.orElse(null),
                sortingAndPaginationService.getSortingFieldFromSession(session, sortField),
                sortingAndPaginationService.getOrderByFromSession(session, orderBy)
        );
        if (sortField.isPresent() && orderBy.isPresent()) {
            return examRepository.findByDeletedFalse(pageable);
        } else {
            return examRepository.findByDeletedFalseOrderByStartDateDesc(pageable);
        }
    }

    public void createOrUpdate(Exam exam) {
        examRepository.save(exam);
    }

    public void publish(Integer id) {
        Exam exam = findById(id);
        exam.setState(ExamState.PUBLISHED);
        examRepository.save(exam);
    }

    @Transactional
    public void addExaminerToExam(Integer examinerId, Integer examId) {
        Exam exam = findById(examId);
        Examiner examiner = examinerRepository.findById(examinerId).orElseThrow(() ->
                new EntityNotFoundException("Examiner not found"));
        examiner.getExams().add(exam);
        exam.getExaminers().add(examiner);
        examinerRepository.save(examiner);
        examRepository.save(exam);
    }

    @Transactional
    public void removeExaminerFromExam(Integer examinerId, Integer examId) {
        Exam exam = findById(examId);
        Examiner examiner = examinerRepository.findById(examinerId).orElseThrow(() ->
                new EntityNotFoundException("Examiner not found"));
        examiner.getExams().remove(exam);
        exam.getExaminers().remove(examiner);
        examinerRepository.save(examiner);
        examRepository.save(exam);
    }

    public void softDelete(Exam exam) {
        exam.setDeleted(Boolean.TRUE);
        examRepository.save(exam);
    }
}