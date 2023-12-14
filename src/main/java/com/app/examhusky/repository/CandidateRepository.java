package com.app.examhusky.repository;

import com.app.examhusky.model.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

    Page<Candidate> findAllByDeletedFalse(Pageable pageable);

}
