package com.app.examhusky.service;

import com.app.examhusky.model.Candidate;
import com.app.examhusky.model.Examiner;
import com.app.examhusky.repository.ExaminerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExaminerService {
    private final ExaminerRepository examinerRepository;
    private final SortingAndPaginationService sortingAndPaginationService;
    private final AuthUserService authUserService;

    public ExaminerService(ExaminerRepository examinerRepository,
                           SortingAndPaginationService sortingAndPaginationService,
                           AuthUserService authUserService) {
        this.examinerRepository = examinerRepository;
        this.sortingAndPaginationService = sortingAndPaginationService;
        this.authUserService = authUserService;
    }

    public Examiner findById(Integer id) {
        return examinerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Examiner not found!"));
    }

    public Page<Examiner> findExaminersOfExam(Integer examId,
                                              HttpSession session,
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

        return examinerRepository.findByExams_Id(examId, pageable);
    }

    public Page<Examiner> findAvailableExaminersForExam(Integer examId,
                                              HttpSession session,
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

        return examinerRepository.findExaminersNotAssignedToExam(examId, pageable);
    }

    public Examiner findExaminerByCurrentAuthAccount() {
        return examinerRepository.findByAccount(authUserService.currentAuthAccount()).orElseThrow(() ->
                new EntityNotFoundException("Candidate not found"));
    }

    public boolean isExaminerAssignedToExam(Integer examinerId, Integer examId) {
        return examinerRepository.isExaminerAssignedToExam(examinerId, examId);
    }
}