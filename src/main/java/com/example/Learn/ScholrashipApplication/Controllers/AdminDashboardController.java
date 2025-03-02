package com.example.Learn.ScholrashipApplication.Controllers;

import com.example.Learn.ScholrashipApplication.EmailService;
import com.example.Learn.ScholrashipApplication.Entity.Scholarship;
import com.example.Learn.ScholrashipApplication.Entity.ScholarshipApplication;
import com.example.Learn.ScholrashipApplication.Services.ScholarshipApplicationService;
import com.example.Learn.ScholrashipApplication.Services.ScholarshipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminDashboardController {

    private final ScholarshipService scholarshipService;
    private final ScholarshipApplicationService scholarshipApplicationService;
    private final EmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(AdminDashboardController.class);

    public AdminDashboardController(ScholarshipService scholarshipService, ScholarshipApplicationService scholarshipApplicationService, EmailService emailService) {
        this.scholarshipService = scholarshipService;
        this.scholarshipApplicationService = scholarshipApplicationService;
        this.emailService = emailService;
    }

    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("username", username);

        // Fetch available scholarships for the admin
        List<Scholarship> scholarships = scholarshipService.getAllScholarships();
        model.addAttribute("scholarships", scholarships);

        return "admin-dashboard";
    }


    @GetMapping("/admin/scholarship/{id}/applicants")
    public String viewApplicants(@PathVariable Long id,
                                 @RequestParam(required = false) String name,
                                 @RequestParam(required = false) Double gpa,
                                 @RequestParam(required = false) String status,
                                 Model model) {
        Scholarship scholarship = scholarshipService.findById(id);
        if (scholarship == null) {
            throw new RuntimeException("Scholarship not found for ID: " + id);
        }

        // Fetch applicants and filter based on criteria
        List<ScholarshipApplication> applications = scholarshipApplicationService.getApplicationsByScholarshipId(id);

        if (name != null && !name.isEmpty()) {
            applications = applications.stream()
                    .filter(a -> a.getFullName().toLowerCase().contains(name.toLowerCase()))
                    .toList();
        }
        if (gpa != null) {
            double epsilon = 0.001; // Tolerance for floating-point comparison
            applications = applications.stream()
                    .filter(a -> Math.abs(a.getGpa() - gpa) < epsilon).toList();
        }

        if (status != null && !status.isEmpty()) {
            applications = applications.stream()
                    .filter(a -> a.getStatus().toString().equalsIgnoreCase(status))
                    .toList();
        }

        model.addAttribute("scholarship", scholarship);
        model.addAttribute("applications", applications);
        return "applicants-view";
    }


    @PostMapping("/admin/approve/{id}")
    public String approveApplication(@PathVariable Long id) {
        ScholarshipApplication application = scholarshipApplicationService.approveApplication(id);

        if (application != null) {
            emailService.sendApprovalEmail(application.getEmail(), application.getFullName());
        }

        return "redirect:/admin/scholarship/" + application.getScholarship().getId() + "/applicants";
    }

    @PostMapping("/admin/reject/{id}")
    public String rejectApplication(@PathVariable Long id) {
        ScholarshipApplication application = scholarshipApplicationService.approveApplication(id);
        if (application != null) {
            emailService.sendRejectionEmail(application.getEmail(), application.getFullName());
        }
        return "redirect:/admin/scholarship/" + application.getScholarship().getId() + "/applicants";
    }
}
