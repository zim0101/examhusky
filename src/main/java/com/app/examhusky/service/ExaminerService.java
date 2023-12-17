package com.app.examhusky.service;

import com.app.examhusky.model.Examiner;
import com.app.examhusky.repository.ExaminerRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExaminerService {
    private final ExaminerRepository examinerRepository;
    private final SortingAndPaginationService sortingAndPaginationService;

    public ExaminerService(ExaminerRepository examinerRepository, SortingAndPaginationService sortingAndPaginationService) {
        this.examinerRepository = examinerRepository;
        this.sortingAndPaginationService = sortingAndPaginationService;
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
}