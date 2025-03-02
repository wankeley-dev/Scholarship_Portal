package com.example.Learn.ScholrashipApplication.Services;

import com.example.Learn.ScholrashipApplication.Entity.ApplicationStatus;
import com.example.Learn.ScholrashipApplication.Entity.Scholarship;
import com.example.Learn.ScholrashipApplication.Entity.ScholarshipApplication;
import com.example.Learn.ScholrashipApplication.Repository.ScholarshipApplicationRepository;
import com.example.Learn.ScholrashipApplication.Repository.ScholarshipRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ScholarshipApplicationService {

    public static List<ScholarshipApplication> getAllScholarshpApplication;
    private final ScholarshipApplicationRepository scholarshipApplicationRepository;
    private final ScholarshipRepository scholarshipRepository;

    public ScholarshipApplicationService(ScholarshipApplicationRepository scholarshipApplicationRepository, ScholarshipRepository scholarshipRepository) {
        this.scholarshipApplicationRepository = scholarshipApplicationRepository;
        this.scholarshipRepository = scholarshipRepository;
    }

    // Save application with scholarship association
    public ScholarshipApplication saveApplication(ScholarshipApplication application, Long scholarshipId) {
        Scholarship scholarship = scholarshipRepository.findById(scholarshipId)
                .orElseThrow(() -> new RuntimeException("Scholarship not found"));

        application.setScholarship(scholarship);
        application.setStatus(ApplicationStatus.PENDING); // Default status
        return scholarshipApplicationRepository.save(application);
    }

    // Get all applications (for Admin)
    public List<ScholarshipApplication> getAllScholarshpApplications() {
        return scholarshipApplicationRepository.findAll();
    }

    // Retrieve a specific application by ID
    public Optional<ScholarshipApplication> getApplicationById(Long id) {
        return scholarshipApplicationRepository.findById(id);
    }

    // Fetch all pending applications for admin approval
    public List<ScholarshipApplication> getPendingApplications() {
        return scholarshipApplicationRepository.findByStatus(ApplicationStatus.PENDING);
    }

    // Fetch applications by scholarship ID
    public List<ScholarshipApplication> getApplicationsByScholarshipId(Long scholarshipId) {
        return scholarshipApplicationRepository.findByScholarshipId(scholarshipId);
    }

    // Approve an application
    public ScholarshipApplication approveApplication(Long applicationId) {
        ScholarshipApplication application = scholarshipApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        application.setStatus(ApplicationStatus.APPROVED);
        scholarshipApplicationRepository.save(application);
        return application;
    }

    // Reject an application
    public void rejectApplication(Long applicationId) {
        ScholarshipApplication application = scholarshipApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        application.setStatus(ApplicationStatus.REJECTED);
        scholarshipApplicationRepository.save(application);
    }
}
