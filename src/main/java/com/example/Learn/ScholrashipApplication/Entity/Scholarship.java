package com.example.Learn.ScholrashipApplication.Entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Scholarship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String deadline;

    @OneToMany(mappedBy = "scholarship", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScholarshipApplication> applications; // Link to applications

    // Constructors
    public Scholarship() {}

    public Scholarship(String name, String description, String deadline) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDeadline() { return deadline; }
    public void setDeadline(String deadline) { this.deadline = deadline; }

    public List<ScholarshipApplication> getApplications() { return applications; }
    public void setApplications(List<ScholarshipApplication> applications) { this.applications = applications; }
}
