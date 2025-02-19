package com.amcamp.seven.global.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info =
                new Info()
                        .version("v0.0.1")
                        .title("Seven API") // 이름
                        .description("사용자 취향 맞춤형 영화 추천 프로젝트 API"); // 설명
        return new OpenAPI().info(info);
    }
}
