package com.amcamp.cineAI.global.error;

import static org.junit.jupiter.api.Assertions.*;

import com.amcamp.cineAI.global.common.response.CommonResponse;
import com.amcamp.cineAI.global.error.exception.CustomException;
import com.amcamp.cineAI.global.error.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class ErrorControllerAdviceTest {

    @Autowired private ErrorControllerAdvice errorControllerAdvice;

    @Test
    @DisplayName("CustomException 발생")
    void testHandleCustomException() {
        CustomException customException = new CustomException(ErrorCode.TEST_ERROR);

        ResponseEntity<CommonResponse> response =
                errorControllerAdvice.handleCustomException(customException);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody().getData();
        assertEquals(ErrorCode.TEST_ERROR.name(), errorResponse.getErrorClassName());
        assertEquals(ErrorCode.TEST_ERROR.getMessage(), errorResponse.getMessage());
    }
}
