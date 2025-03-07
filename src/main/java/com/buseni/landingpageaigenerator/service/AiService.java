package com.buseni.landingpageaigenerator.service;

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
} 