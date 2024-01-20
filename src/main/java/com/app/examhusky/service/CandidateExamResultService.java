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
import java.util.List;
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

    public Page<CandidateExamResult> findAllResultByExamId(Integer examId,
                                                           HttpSession session,
                                                           Optional<Integer> page,
                                                           Optional<Integer> size,
                                                           Optional<String> sortField,
                                                           Optional<String> orderBy) throws AccessDeniedException {
        if (!authUserService.authUserIsAdminGroup()) {
            Examiner examiner = examinerService.findExaminerByCurrentAuthAccount();
            if (!examinerService.isExaminerAssignedToExam(examiner.getId(), examId)) {
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

        return candidateExamResultRepository.findByExamId(examId, pageable);
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

    public void updateTotalMarksOfCandidateForExam(Integer examId, Integer candidateId, Integer totalMarks) {
        CandidateExamResult candidateExamResult = candidateExamResultRepository.findByExamIdAndCandidateId(examId,
                candidateId);
        candidateExamResult.setTotalMarks(totalMarks);
        candidateExamResult.setDecidedBy(authUserService.currentAuthAccount());
        candidateExamResultRepository.save(candidateExamResult);
    }

    public void confirmExaminationForCandidate(Integer examId, Integer candidateId) {
        CandidateExamResult candidateExamResult = candidateExamResultRepository.findByExamIdAndCandidateId(examId,
                candidateId);
        candidateExamResult.setExamined(true);
        candidateExamResultRepository.save(candidateExamResult);
    }

    public void recommendCandidateFromExam(Integer examId, Integer candidateId) {
        CandidateExamResult candidateExamResult = candidateExamResultRepository.findByExamIdAndCandidateId(examId,
                candidateId);
        candidateExamResult.setRecommendation(Recommendation.RECOMMENDED);
        candidateExamResultRepository.save(candidateExamResult);
        // TODO: send email with result and congratulations
    }

    public void notRecommendCandidateFromExam(Integer examId, Integer candidateId) {
        CandidateExamResult candidateExamResult = candidateExamResultRepository.findByExamIdAndCandidateId(examId,
                candidateId);
        candidateExamResult.setRecommendation(Recommendation.NOT_RECOMMENDED);
        candidateExamResultRepository.save(candidateExamResult);
        // TODO: send email with result and feel sorry
    }
}