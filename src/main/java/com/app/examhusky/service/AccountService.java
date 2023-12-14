package com.app.examhusky.service;

import com.app.examhusky.dto.CandidateAccountDto;
import com.app.examhusky.dto.ExaminerAccountDto;
import com.app.examhusky.model.Account;
import com.app.examhusky.model.Candidate;
import com.app.examhusky.model.Examiner;
import com.app.examhusky.model.enums.Role;
import com.app.examhusky.repository.AccountRepository;
import com.app.examhusky.repository.CandidateRepository;
import com.app.examhusky.repository.ExaminerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CandidateRepository candidateRepository;
    private final ExaminerRepository examinerRepository;
    private final SortingAndPaginationService sortingAndPaginationService;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository,
                          CandidateRepository candidateRepository,
                          ExaminerRepository examinerRepository, SortingAndPaginationService sortingAndPaginationService,
                          PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.candidateRepository = candidateRepository;
        this.examinerRepository = examinerRepository;
        this.sortingAndPaginationService = sortingAndPaginationService;
        this.passwordEncoder = passwordEncoder;
    }

    public Account findById(Integer id) {
        return accountRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Account  not found!"));
    }

    public Examiner findExaminerById(Integer id) {
        return examinerRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Account  not found!"));
    }

    public Candidate findCandidateById(Integer id) {
        return candidateRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Account  not found!"));
    }

    public Page<Examiner> sortAndPaginateActiveExaminerList(HttpSession session,
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

        return examinerRepository.findAllByDeletedFalse(pageable);
    }

    public Page<Candidate> sortAndPaginateActiveCandidateList(HttpSession session,
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

        return candidateRepository.findAllByDeletedFalse(pageable);
    }

    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Transactional
    public void saveCandidateAccount(CandidateAccountDto candidateAccountDto) {
        Account account = candidateAccountDto.getAccount();
        Candidate candidate = candidateAccountDto.getCandidate();

        account.setPassword(passwordEncoder.encode(account.getPassword()));
        if (account.getRoles() == null) {
            account.setRoles(Set.of(Role.CANDIDATE));
        }
        candidate.setAccount(account);

        accountRepository.save(account);
        candidateRepository.save(candidate);
    }

    @Transactional
    public void saveExaminerAccount(ExaminerAccountDto examinerAccountDto) {
        Account account = examinerAccountDto.getAccount();
        Examiner examiner = examinerAccountDto.getExaminer();

        account.setPassword(passwordEncoder.encode(account.getPassword()));
        if (account.getRoles() == null) {
            account.setRoles(Set.of(Role.EXAMINER));
        }
        examiner.setAccount(account);

        accountRepository.save(account);
        examinerRepository.save(examiner);
    }

    public void updateExaminerAccount(Examiner examiner) {
        examinerRepository.save(examiner);
    }

    public void updateCandidateAccount(Candidate candidate) {
        candidateRepository.save(candidate);
    }

    public void disable(Account account) {
        account.setDisabled(Boolean.TRUE);
        accountRepository.save(account);
    }

    public void enable(Account account) {
        account.setDisabled(Boolean.FALSE);
        accountRepository.save(account);
    }

    @Transactional
    public void softDeleteExaminerAccount(Examiner examiner) {
        Account account = examiner.getAccount();
        account.setDeleted(Boolean.TRUE);
        examiner.setDeleted(Boolean.TRUE);
        examinerRepository.save(examiner);
        accountRepository.save(account);
    }

    @Transactional
    public void softDeleteCandidateAccount(Candidate candidate) {
        Account account = candidate.getAccount();
        account.setDeleted(Boolean.TRUE);
        candidate.setDeleted(Boolean.TRUE);
        candidateRepository.save(candidate);
        accountRepository.save(account);
    }
}
