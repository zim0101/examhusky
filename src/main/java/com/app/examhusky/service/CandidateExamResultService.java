package com.app.examhusky.service;

import com.app.examhusky.model.Candidate;
import com.app.examhusky.model.CandidateExamResult;
import com.app.examhusky.model.Exam;
import com.app.examhusky.model.Examiner;
import com.app.examhusky.model.enums.Recommendation;
import com.app.examhusky.repository.CandidateExamResultRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.nio.file.AccessDeniedException;
import java.util.Optional;

@Service
public class CandidateExamResultService {

    private final CandidateExamResultRepository candidateExamResultRepository;
    private final CandidateService candidateService;
    private final ExaminerService examinerService;
    private final AuthUserService authUserService;
    private final SortingAndPaginationService sortingAndPaginationService;

    public CandidateExamResultService(CandidateExamResultRepository candidateExamResultRepository,
                                      CandidateService candidateService,
                                      ExaminerService examinerService,
                                      AuthUserService authUserService,
                                      SortingAndPaginationService sortingAndPaginationService) {
        this.candidateExamResultRepository = candidateExamResultRepository;
        this.candidateService = candidateService;
        this.examinerService = examinerService;
        this.authUserService = authUserService;
        this.sortingAndPaginationService = sortingAndPaginationService;
    }

    public Page<CandidateExamResult> findAllResultByExamId(Exam exam,
                                                           HttpSession session,
                                                           Optional<Integer> page,
                                                           Optional<Integer> size,
                                                           Optional<String> sortField,
                                                           Optional<String> orderBy) throws AccessDeniedException {
        if (!authUserService.authUserIsAdminGroup()) {
            Examiner examiner = examinerService.findExaminerByCurrentAuthAccount();
            if (!examinerService.isExaminerAssignedToExam(examiner, exam)) {
                throw new AccessDeniedException("You are not the examiner of this exam");
            }
        }

        sortingAndPaginationService.removePageAndSortingDataFromSessionOnReload(
                session, page, size, sortField, orderBy);

        Pageable pageable = sortingAndPaginationService.buildPageable(
                sortingAndPaginationService.getPageFromSession(session, page),
                size.orElse(null),
                sortingAndPaginationService.getSortingFieldFromSession(session, sortField),
                sortingAndPaginationService.getOrderByFromSession(session, orderBy)
        );

        return candidateExamResultRepository.findByExamId(exam.getId(), pageable);
    }

    @Transactional
    public void initExamResultForAuthenticatedCandidate(Exam exam) {
        Candidate candidate = candidateService.findCandidateByCurrentAuthAccount();
        initExamResultForCandidate(exam, candidate);
    }

    @Transactional
    public void initExamResultForCandidate(Exam exam, Candidate candidate) {
        CandidateExamResult candidateExamResult = new CandidateExamResult();
        candidateExamResult.setCandidate(candidate);
        candidateExamResult.setExam(exam);
        candidateExamResultRepository.save(candidateExamResult);
    }

    public void updateTotalMarksOfCandidateForExam(Exam exam, Candidate candidate, Integer totalMarks) {
        CandidateExamResult candidateExamResult = candidateExamResultRepository.findByExamAndCandidate(exam, candidate);

        candidateExamResult.setTotalMarks(totalMarks);
        candidateExamResult.setDecidedBy(authUserService.currentAuthAccount());
        candidateExamResultRepository.save(candidateExamResult);
    }

    public void confirmExaminationForCandidate(Exam exam, Candidate candidate) {
        CandidateExamResult candidateExamResult = candidateExamResultRepository.findByExamAndCandidate(exam, candidate);
        candidateExamResult.setExamined(true);
        candidateExamResultRepository.save(candidateExamResult);
    }

    public void recommendCandidateFromExam(Exam exam, Candidate candidate) {
        CandidateExamResult candidateExamResult = candidateExamResultRepository.findByExamAndCandidate(exam, candidate);
        candidateExamResult.setRecommendation(Recommendation.RECOMMENDED);
        candidateExamResultRepository.save(candidateExamResult);
        // TODO: send email with result and congratulations
    }

    public void notRecommendCandidateFromExam(Exam exam, Candidate candidate) {
        CandidateExamResult candidateExamResult = candidateExamResultRepository.findByExamAndCandidate(exam, candidate);
        candidateExamResult.setRecommendation(Recommendation.NOT_RECOMMENDED);
        candidateExamResultRepository.save(candidateExamResult);
        // TODO: send email with result and feel sorry
    }
}