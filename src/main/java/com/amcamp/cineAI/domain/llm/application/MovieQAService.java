package com.amcamp.cineAI.domain.llm.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieQAService {

    private final LLMService llmService;
    private final PromptService promptService;

    public String getAnswer(String question) {
        String prompt = promptService.getMovieQAPrompt(question);
        return llmService.callLLM(prompt);
    }
}
