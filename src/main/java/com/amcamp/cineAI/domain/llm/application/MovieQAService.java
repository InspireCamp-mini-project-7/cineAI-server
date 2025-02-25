package com.amcamp.cineAI.domain.llm.application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieQAService {

    private final LLMService llmService;
    private final PromptService promptService;

    public String getAnswer(String question) {
        String prompt = promptService.getMovieQAPrompt(question);
        String llmResponse = llmService.callLLM(prompt);
        return extractContent(llmResponse);
    }

    private String extractContent(String llmResponse) {
        try {
            Pattern pattern = Pattern.compile("content=([^}]+)");
            Matcher matcher = pattern.matcher(llmResponse);
            if (matcher.find()) {
                return matcher.group(1).trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return llmResponse;
    }
}
