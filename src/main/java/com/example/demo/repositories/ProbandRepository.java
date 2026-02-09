package com.example.demo.repositories;

import com.example.demo.domain.Proband;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProbandRepository extends JpaRepository<Proband, Long> {
}
