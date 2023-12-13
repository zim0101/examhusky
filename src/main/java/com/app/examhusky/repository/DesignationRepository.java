package com.app.examhusky.repository;

import com.app.examhusky.model.Designation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DesignationRepository extends JpaRepository<Designation, Integer> {
    List<Designation> findAllByDeletedFalse();
}
