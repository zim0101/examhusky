package com.app.examhusky.service;

import com.app.examhusky.model.Designation;
import com.app.examhusky.repository.DesignationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesignationService {
    private final DesignationRepository designationRepository;

    public DesignationService(DesignationRepository designationRepository) {
        this.designationRepository = designationRepository;
    }

    public List<Designation> findAllActive() {
        return designationRepository.findAllByDeletedFalse();
    }
}