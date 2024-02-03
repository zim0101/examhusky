package com.app.examhusky.service;

import com.app.examhusky.model.QuestionCategory;
import com.app.examhusky.repository.QuestionCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionCategoryService {

    private final QuestionCategoryRepository questionCategoryRepository;
    private final SortingAndPaginationService sortingAndPaginationService;

    public QuestionCategoryService(QuestionCategoryRepository questionCategoryRepository, SortingAndPaginationService sortingAndPaginationService) {
        this.questionCategoryRepository = questionCategoryRepository;
        this.sortingAndPaginationService = sortingAndPaginationService;
    }

    public QuestionCategory findById(Integer id) {
        return questionCategoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Question Category not found!"));
    }

    public List<QuestionCategory> findAllActive() {
        return questionCategoryRepository.findAllByDeletedFalseAndDisabledFalse();
    }

    public Page<QuestionCategory> sortAndPaginateList(HttpSession session,
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
        return questionCategoryRepository.findAllByDeletedFalse(pageable);
    }

    public void createOrUpdate(QuestionCategory questionCategory) {
        questionCategory.setDeleted(Boolean.FALSE);
        questionCategoryRepository.save(questionCategory);
    }

    public void disable(QuestionCategory questionCategory) {
        questionCategory.setDisabled(Boolean.TRUE);
        questionCategoryRepository.save(questionCategory);
    }

    public void enable(QuestionCategory questionCategory) {
        questionCategory.setDisabled(Boolean.FALSE);
        questionCategoryRepository.save(questionCategory);
    }

    public void softDelete(QuestionCategory questionCategory) {
        questionCategory.setDeleted(Boolean.TRUE);
        questionCategoryRepository.save(questionCategory);
    }
}