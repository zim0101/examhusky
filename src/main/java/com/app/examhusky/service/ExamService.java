package com.app.examhusky.service;

import com.app.examhusky.repository.ExamRepository;
import org.springframework.stereotype.Service;

@Service
public class ExamService {
    private final ExamRepository examRepository;

    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }
}