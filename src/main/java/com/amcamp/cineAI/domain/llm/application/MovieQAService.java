package com.amcamp.cineAI.domain.llm.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieQAService {

    private final LLMService llmService;
    private final PromptService promptService;

    @Autowired
    public MovieQAService(LLMService llmService, PromptService promptService) {
        this.llmService = llmService;
        this.promptService = promptService;
    }

    public String getAnswer(String question) {
        String prompt = promptService.getMovieQAPrompt(question);
        return llmService.callLLM(prompt);
    }
}
