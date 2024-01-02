package com.app.examhusky.service;

import com.app.examhusky.model.Candidate;
import com.app.examhusky.model.CandidateExamResult;
import com.app.examhusky.model.Exam;
import com.app.examhusky.repository.CandidateExamResultRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CandidateExamResultService {
    private final CandidateExamResultRepository candidateExamResultRepository;
    private final CandidateService candidateService;
    private final AuthUserService authUserService;

    public CandidateExamResultService(CandidateExamResultRepository candidateExamResultRepository,
                                      CandidateService candidateService,
                                      AuthUserService authUserService) {
        this.candidateExamResultRepository = candidateExamResultRepository;
        this.candidateService = candidateService;
        this.authUserService = authUserService;
    }

    public List<CandidateExamResult> findAllResultByExamId(Integer examId) {
        return candidateExamResultRepository.findByExamId(examId);
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
        candidateExamResult.setDecidedBy(authUserService.currentAuthAccount());
        candidateExamResultRepository.save(candidateExamResult);
    }
}