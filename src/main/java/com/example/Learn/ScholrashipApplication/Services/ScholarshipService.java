package com.example.Learn.ScholrashipApplication.Services;

import com.example.Learn.ScholrashipApplication.Entity.Scholarship;
import com.example.Learn.ScholrashipApplication.Repository.ScholarshipRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScholarshipService {

    private final ScholarshipRepository scholarshipRepository;

    public ScholarshipService(ScholarshipRepository scholarshipRepository) {
        this.scholarshipRepository = scholarshipRepository;
    }

    // Get all scholarships
    public List<Scholarship> getAllScholarships() {
        return scholarshipRepository.findAll();
    }

    // Find a scholarship by ID (used in ScholarshipApplicationController)
    public Optional<Scholarship> getScholarshipById(Long id) {
        return scholarshipRepository.findById(id);
    }

    public Scholarship findById(Long id) {
        return scholarshipRepository.findById(id).orElse(null); // Avoid NoSuchElementException
    }

}
