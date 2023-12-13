package com.app.examhusky.service;


import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SortingAndPaginationService {

    private static final int pagination = 5;

    public SortingAndPaginationService() { }

    public int getPageFromSession(HttpSession session, Optional<Integer> page) {
        if (page.isPresent()) {
            session.setAttribute("page", page.get());
            return page.get();
        } else {
            return session.getAttribute("page") == null ? 1 :
                    (int) session.getAttribute("page");
        }
    }

    public String getSortingFieldFromSession(HttpSession session, Optional<String> sortField) {
        if (sortField.isPresent()) {
            session.setAttribute("sortField", sortField.get());
            return sortField.get();
        } else {
            return (String) session.getAttribute("sortField");
        }
    }

    public String getOrderByFromSession(HttpSession session, Optional<String> orderBy) {
        if (orderBy.isPresent()) {
            session.setAttribute("orderBy", orderBy.get());
            return orderBy.get();
        } else {
            return (String) session.getAttribute("orderBy");
        }
    }

    public void removePageAndSortingDataFromSessionOnReload(HttpSession session,
                                                            Optional<Integer> page,
                                                            Optional<Integer> size,
                                                            Optional<String> sortField,
                                                            Optional<String> orderBy) {

        boolean newPageReload = page.isEmpty() && size.isEmpty()
                && sortField.isEmpty() && orderBy.isEmpty();
        if (newPageReload) {
            flushSessionForPaginationAndSorting(session);
        }
    }

    public void flushSessionForPaginationAndSorting(HttpSession session) {
        session.removeAttribute("page");
        session.removeAttribute("size");
        session.removeAttribute("sortField");
        session.removeAttribute("orderBy");
    }

    public Pageable buildPageable(Integer page, Integer size, String sortField, String orderBy) {
        Pageable pageable;

        int currentPage = page - 1;
        int currentSize = size == null ? pagination : size;

        pageable = (sortField != null && orderBy != null) ?
                PageRequest.of(currentPage, currentSize, generateSorting(sortField, orderBy)) :
                PageRequest.of(currentPage, currentSize);

        return pageable;
    }

    public Sort generateSorting(String sortField, String orderBy) {
        return Sort.Direction.ASC.name().equalsIgnoreCase(orderBy) ?
                Sort.by(Sort.Order.asc(sortField)) :
                Sort.by(Sort.Order.desc(sortField));
    }

    /**
     * @param sortFieldList multiple fields to sort
     * @param orderByList order_by for sorting fields
     * @return Sort
     *
     * Use this method if combination of multiple field sorting is needed.
     */
    public Sort generateMultipleSorting(List<String> sortFieldList, List<String> orderByList) {
        List<Sort.Order> orderList = new ArrayList<>();

        sortFieldList.forEach(field -> {
            String orderBy = orderByList.get(sortFieldList.indexOf(field));
            if (!orderBy.isEmpty()) {
                Sort.Order order = Sort.Direction.ASC.name().equalsIgnoreCase(orderBy) ?
                        Sort.Order.asc(field) : Sort.Order.desc(field);
                orderList.add(order);
            }
        });

        return Sort.by(orderList);
    }
}

