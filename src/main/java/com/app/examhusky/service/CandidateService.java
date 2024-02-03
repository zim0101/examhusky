package com.app.examhusky.service;

import com.app.examhusky.model.Candidate;
import com.app.examhusky.model.Exam;
import com.app.examhusky.repository.CandidateRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final AuthUserService authUserService;
    private final SortingAndPaginationService sortingAndPaginationService;

    public CandidateService(CandidateRepository candidateRepository,
                            AuthUserService authUserService,
                            SortingAndPaginationService sortingAndPaginationService) {
        this.candidateRepository = candidateRepository;
        this.authUserService = authUserService;
        this.sortingAndPaginationService = sortingAndPaginationService;
    }

    public Candidate findById(Integer id) {
        return candidateRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Candidate not found"));
    }

    public Page<Candidate> findCandidatesOfExam(Exam exam,
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

        return candidateRepository.findByExams_Id(exam.getId(), pageable);
    }

    public Page<Candidate> findAvailableCandidatesForExam(Exam exam,
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

        return candidateRepository.findCandidatesNotAssignedToExam(exam.getId(), pageable);
    }

    public Candidate findCandidateByCurrentAuthAccount() {
        return candidateRepository.findByAccount(authUserService.currentAuthAccount()).orElseThrow(() ->
                new EntityNotFoundException("Candidate not found"));
    }

    public boolean isCandidateAssignedToExam(Candidate candidate, Exam exam) {
        return candidateRepository.isCandidateAssignedToExam(candidate.getId(), exam.getId());
    }
}