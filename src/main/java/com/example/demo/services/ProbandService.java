package com.example.demo.services;

import com.example.demo.domain.Proband;
import com.example.demo.repositories.ProbandRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ProbandService {

    private final ProbandRepository probandRepository;

    public ProbandService(ProbandRepository probandRepository) {
        this.probandRepository = probandRepository;
    }

    /**
     * Register a new proband. Throws IllegalStateException if the email is already
     * taken.
     */
    public Proband registerProband(Proband proband) {
        try {
            return probandRepository.save(proband);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Účastník s tímto e-mailem je již zaregistrován.");
        }
    }
}
