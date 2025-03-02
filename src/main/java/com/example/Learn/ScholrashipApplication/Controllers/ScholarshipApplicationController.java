package com.example.Learn.ScholrashipApplication.Controllers;

import com.example.Learn.ScholrashipApplication.Entity.Scholarship;
import com.example.Learn.ScholrashipApplication.Entity.ScholarshipApplication;
import com.example.Learn.ScholrashipApplication.Services.ScholarshipApplicationService;
import com.example.Learn.ScholrashipApplication.Services.ScholarshipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/scholarship")
public class ScholarshipApplicationController {

    private static final Logger logger = LoggerFactory.getLogger(ScholarshipApplicationController.class);

    private final ScholarshipApplicationService applicationService;
    private final ScholarshipService scholarshipService;

    public ScholarshipApplicationController(ScholarshipApplicationService applicationService, ScholarshipService scholarshipService) {
        this.applicationService = applicationService;
        this.scholarshipService = scholarshipService;
    }

    // Show Scholarship Application Form for a specific scholarship
    @GetMapping("/apply/{scholarshipId}")
    public String showApplicationForm(@PathVariable Long scholarshipId, Model model) {
        Optional<Scholarship> scholarship = scholarshipService.getScholarshipById(scholarshipId);

        if (scholarship.isPresent()) {
            model.addAttribute("scholarship", scholarship.get());
            model.addAttribute("scholarshipApplication", new ScholarshipApplication()); // Add this line
            return "scholarship"; // Ensure this matches the template name
        } else {
            logger.error("Scholarship ID not found: {}", scholarshipId);
            return "redirect:/dashboard";
        }
    }
    // Process Scholarship Application Form Submission
    @PostMapping("/apply/{scholarshipId}")
    public String submitApplication(
            @PathVariable Long scholarshipId,
            @ModelAttribute ScholarshipApplication application,
            RedirectAttributes redirectAttributes) {

        logger.info("Application data before saving: {}", application.toString());

        applicationService.saveApplication(application, scholarshipId);

        logger.info("Application data: {}", application);

        // Store in session instead of flash attributes
        redirectAttributes.addAttribute("id", application.getId());

        return "redirect:/scholarship/success";
    }

    @GetMapping("/success")
    public String showSuccessPage(@RequestParam("id") Long id, Model model) {
        Optional<ScholarshipApplication> application = applicationService.getApplicationById(id);

        if (application.isPresent()) {
            model.addAttribute("applicants", application.get());
            return "application_success";
        } else {
            logger.error("Application not found for ID: {}", id);
            return "redirect:/dashboard";
        }




    }



    // Admin View: List All Applications
    @GetMapping("/admin/applications")
    public String viewAllApplications(Model model) {
        List<ScholarshipApplication> applications = applicationService.getPendingApplications();
        model.addAttribute("applications", applications);
        return "admin_applications";
    }
}