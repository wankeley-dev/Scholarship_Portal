package com.example.Learn.ScholrashipApplication;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendApprovalEmail(String to, String name) {
        String subject = "Scholarship Approval Notification";
        String content = "Dear " + name + ",<br><br>"
                + "Congratulations! You have been granted a scholarship.<br>"
                + "Please check your portal for more details.<br><br>"
                + "Best regards,<br>Scholarship Team";

        sendEmail(to, subject, content);
    }

    public void sendRejectionEmail(String to, String name) {
        String subject = "Scholarship Rejection Notification";
        String content = "Dear " + name + ",<br><br>"
                + "Sorry! You have been denied a scholarship.<br>"
                + "Please check your portal for more details.<br><br>"
                + "Best regards,<br>Scholarship Team";

        sendEmail(to, subject, content);
    }

    public void sendEmail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Set the sender's email address
            helper.setFrom("cindyalenar@gmail.com"); // Replace with your email address

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // true for HTML content

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}