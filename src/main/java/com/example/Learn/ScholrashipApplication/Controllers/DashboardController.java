package com.example.Learn.ScholrashipApplication.Controllers;

import com.example.Learn.ScholrashipApplication.Entity.Scholarship;
import com.example.Learn.ScholrashipApplication.Services.ScholarshipService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    private final ScholarshipService scholarshipService;

    public DashboardController(ScholarshipService scholarshipService) {
        this.scholarshipService = scholarshipService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Authentication authentication) {
        String username = authentication.getName(); // Get logged-in user
        model.addAttribute("username", username);

        // Fetch available scholarships
        List<Scholarship> scholarships = scholarshipService.getAllScholarships();
        model.addAttribute("scholarships", scholarships);

        return "dashboard"; // Ensure the correct template name is used
    }
}
