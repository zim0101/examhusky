package com.app.examhusky.service;

import com.app.examhusky.model.Candidate;
import com.app.examhusky.repository.CandidateRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private final SortingAndPaginationService sortingAndPaginationService;

    public CandidateService(CandidateRepository candidateRepository, SortingAndPaginationService sortingAndPaginationService) {
        this.candidateRepository = candidateRepository;
        this.sortingAndPaginationService = sortingAndPaginationService;
    }

    public Page<Candidate> findCandidatesOfExam(Integer examId,
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

        return candidateRepository.findByExams_Id(examId, pageable);
    }

    public Page<Candidate> findAvailableCandidatesForExam(Integer examId,
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

        return candidateRepository.findCandidatesNotAssignedToExam(examId, pageable);
    }
}