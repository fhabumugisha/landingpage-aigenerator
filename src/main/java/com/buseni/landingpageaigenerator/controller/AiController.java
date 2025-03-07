package com.buseni.landingpageaigenerator.controller;

import com.buseni.landingpageaigenerator.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping(value = "/generate", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> generateContent(@RequestBody String prompt) {
        try {
            String generatedContent = aiService.generateContent(prompt);
            return ResponseEntity.ok(generatedContent);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Error generating content: " + e.getMessage());
        }
    }

    @PostMapping("/generate-landing-page")
    public ResponseEntity<String> generateLandingPage(@RequestParam String prompt) {
        try {
            // Validate input
            if (prompt == null || prompt.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .contentType(MediaType.TEXT_HTML)
                    .body("<div class='text-red-500'>Please provide a valid prompt</div>");
            }

            String generatedContent = aiService.generateLandingPageHtml(prompt);
            
            return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(generatedContent);
                
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .contentType(MediaType.TEXT_HTML)
                .body("<div class='text-red-500'>Error generating landing page: " + e.getMessage() + "</div>");
        }
    }

    public record GenerateRequest(String prompt) {}
} 