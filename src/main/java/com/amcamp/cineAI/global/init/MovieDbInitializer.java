package com.amcamp.cineAI.global.init;

import com.amcamp.cineAI.domain.movie.application.MovieService;
import com.amcamp.cineAI.global.error.exception.CustomException;
import com.amcamp.cineAI.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MovieDbInitializer implements ApplicationRunner {

    private final MovieService movieService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            movieService.uploadCSV();
            log.debug("영화 DB 초기 데이터 세팅 완료");
        } catch (Exception e) {
            throw new CustomException(ErrorCode.CSV_NOT_UPLOAD);
        }
    }
}
