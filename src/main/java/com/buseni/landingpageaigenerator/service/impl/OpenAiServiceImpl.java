package com.buseni.landingpageaigenerator.service.impl;

import com.buseni.landingpageaigenerator.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenAiServiceImpl implements AiService {

    private final ChatClient chatClient;

    @Override
    public String generateContent(String prompt) {
        return chatClient.call(new Prompt(prompt)).getResult().getOutput().getContent();
    }

    @Override
    public String generateLandingPageHtml(String requirements) {
        String prompt = String.format("""
            Generate a modern, beautiful, professional, responsive landing page HTML using Tailwind CSS.
            The page should be mobile-friendly and follow best practices.
            Requirements: %s
            
            Please provide only the HTML code without any explanations.
            Use Tailwind CSS classes for styling.
            Include proper meta tags and responsive design.
            For images, use placeholder URLs from https://picsum.photos with appropriate dimensions.
            Make sure the placeholder images match the theme and purpose of the landing page.
            """, requirements);
            
        return generateContent(prompt);
    }
} 