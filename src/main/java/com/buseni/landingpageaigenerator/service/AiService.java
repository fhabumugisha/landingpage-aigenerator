package com.buseni.landingpageaigenerator.service;

import reactor.core.publisher.Flux;

/**
 * Service interface for AI operations.
 */
public interface AiService {
    
    /**
     * Generates content using AI based on the provided prompt.
     *
     * @param prompt The input prompt for content generation
     * @return The generated content
     */
    String generateContent(String prompt);
    
    /**
     * Generates HTML content specifically for landing pages.
     *
     * @param requirements The requirements for the landing page
     * @return The generated HTML content
     */
    String generateLandingPageHtml(String requirements);

    /**
     * Generates content using AI based on the provided prompt with streaming support.
     *
     * @param prompt The input prompt for content generation
     * @return A Flux of generated content chunks
     */
    Flux<String> generateContentStream(String prompt);
    
    /**
     * Generates HTML content specifically for landing pages with streaming support.
     *
     * @param requirements The requirements for the landing page
     * @return A Flux of generated HTML content chunks
     */
    Flux<String> generateLandingPageHtmlStream(String requirements);
} 