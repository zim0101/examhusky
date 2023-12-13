package com.app.examhusky.service;

import com.app.examhusky.model.Question;
import com.app.examhusky.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final SortingAndPaginationService sortingAndPaginationService;
    private final AuthUserService authUserService;

    public QuestionService(QuestionRepository questionRepository, SortingAndPaginationService sortingAndPaginationService, AuthUserService authUserService) {
        this.questionRepository = questionRepository;
        this.sortingAndPaginationService = sortingAndPaginationService;
        this.authUserService = authUserService;
    }

    public Question findById(Integer id) {
        return questionRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Question Category not found!"));
    }

    public Page<Question> sortAndPaginateList(HttpSession session,
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
        return questionRepository.findAllByDeletedFalse(pageable);
    }

    public void createOrUpdate(Question question) {
        question.setEditor(authUserService.currentAuthAccount());
        question.setDeleted(Boolean.FALSE);
        questionRepository.save(question);
    }

    public void disable(Question question) {
        question.setDisabled(Boolean.TRUE);
        questionRepository.save(question);
    }

    public void enable(Question question) {
        question.setDisabled(Boolean.FALSE);
        questionRepository.save(question);
    }

    public void softDelete(Question question) {
        question.setDeleted(Boolean.TRUE);
        questionRepository.save(question);
    }
}