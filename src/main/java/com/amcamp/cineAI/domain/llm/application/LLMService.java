package com.amcamp.cineAI.domain.llm.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LLMService {

    @Value("${groq.api.key}")
    private String apiKey;

    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";

    private final RestTemplate restTemplate = new RestTemplate();

    public String callLLM(String prompt) {
        Map<String, Object> messageObj = new HashMap<>();
        messageObj.put("role", "user");
        messageObj.put("content", prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "llama-3.3-70b-versatile");
        requestBody.put("messages", List.of(messageObj));
        requestBody.put("max_tokens", 1000);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        Map response = restTemplate.postForObject(API_URL, entity, Map.class);
        return response.toString();
    }
}
