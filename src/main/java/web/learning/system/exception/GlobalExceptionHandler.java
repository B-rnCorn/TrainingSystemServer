package web.learning.system.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ExceptionStructure> handleException(GlobalException globalException, HttpServletRequest webRequest) {
        return ResponseEntity
                .status(globalException.getHttpStatus())
                .body(ExceptionStructure.createException(globalException, webRequest.getRequestURI()));
    }
}
