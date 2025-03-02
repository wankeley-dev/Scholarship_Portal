package com.example.Learn.ScholrashipApplication.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "scholarship_applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScholarshipApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Step 1: Personal Information
    private String fullName;
    private String email;
    private String phone;
    private String gender;
    private String dob;
    private String address;

    // Step 2: Academic Information
    private String educationLevel;
    private String schoolName;
    private double gpa;
    private String major;
    private String achievements;

    // Step 3: Other Details
    @Column(length = 2000)
    private String essay;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.PENDING; // Default status

    // Link application to a specific scholarship
    @ManyToOne
    @JoinColumn(name = "scholarship_id", nullable = false)
    private Scholarship scholarship;

    @Override
    public String toString() {
        return "ScholarshipApplication{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", dob='" + dob + '\'' +
                ", address='" + address + '\'' +
                ", educationLevel='" + educationLevel + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", gpa=" + gpa +
                ", major='" + major + '\'' +
                ", achievements='" + achievements + '\'' +
                ", essay='" + (essay != null ? essay.substring(0, Math.min(essay.length(), 50)) + "..." : "N/A") + '\'' +
                ", status=" + status +
                ", scholarship=" + (scholarship != null ? scholarship.getId() : "No Scholarship") +
                '}';
    }


}
