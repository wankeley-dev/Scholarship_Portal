package com.example.Learn.ScholrashipApplication.Repository;

import com.example.Learn.ScholrashipApplication.Entity.ApplicationStatus;
import com.example.Learn.ScholrashipApplication.Entity.ScholarshipApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScholarshipApplicationRepository extends JpaRepository<ScholarshipApplication, Long> {

    // Fetch all applications for a specific scholarship
    List<ScholarshipApplication> findByScholarshipId(Long scholarshipId);

    // Fetch applications by status (useful for admin filtering)
    List<ScholarshipApplication> findByStatus(String status);

    List<ScholarshipApplication> findByStatus(ApplicationStatus status);

}
