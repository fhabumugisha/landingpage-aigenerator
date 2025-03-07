package com.buseni.landingpageaigenerator.controller;

import com.buseni.landingpageaigenerator.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;


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

    @PostMapping(value = "/generate-landing-page", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> generateLandingPage(@RequestParam String prompt) {
        try {
            if (prompt == null || prompt.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body("<div class='text-red-500'>Please provide a valid prompt</div>");
            }

            String generatedContent = aiService.generateLandingPageHtml(prompt);
            return ResponseEntity.ok(generatedContent);
                
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("<div class='text-red-500'>Error generating landing page: " + e.getMessage() + "</div>");
        }
    }

    @PostMapping(value = "/generate-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> generateContentStream(@RequestBody String prompt) {
        return aiService.generateContentStream(prompt);
    }

    @GetMapping(value = "/generate-landing-page-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> generateLandingPageStream(@RequestParam String prompt) {
        return Mono.just(prompt)
            .filter(p -> p != null && !p.trim().isEmpty())
            .switchIfEmpty(Mono.error(new IllegalArgumentException("Please provide a valid prompt")))
            .flatMapMany(validPrompt -> aiService.generateLandingPageHtmlStream(validPrompt)
                .map(content -> {
                    try {
                        return String.format("data: {\"content\": \"%s\"}%n%n",
                            content.replace("\"", "\\\"")
                                  .replace("\n", "\\n")
                                  .replace("\r", "\\r")
                                  .replace("\t", "\\t"));
                    } catch (Exception e) {
                        return String.format("data: {\"error\": \"Error formatting content: %s\"}%n%n",
                            e.getMessage().replace("\"", "\\\""));
                    }
                })
                .onErrorResume(e -> Flux.just(
                    String.format("data: {\"error\": \"%s\"}%n%n",
                        e.getMessage().replace("\"", "\\\""))
                ))
            );
    }

    public record GenerateRequest(String prompt) {}
} 