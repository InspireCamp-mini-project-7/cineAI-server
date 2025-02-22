package com.amcamp.cineAI.domain.llm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieAnswerResponse {
    private String answer;
}
