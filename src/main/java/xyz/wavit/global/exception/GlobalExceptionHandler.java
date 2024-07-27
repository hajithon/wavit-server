package xyz.wavit.global.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        log.warn("CustomException: {}", ex.getMessage(), ex);
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(ErrorResponse.from(ex.getErrorCode()));
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<ErrorResponse> handleJWTVerificationException(JWTVerificationException ex) {
        log.warn("JWTVerificationException: {}", ex.getMessage(), ex);
        ErrorCode errorCode = ErrorCode.INVALID_JWT;
        return ResponseEntity.status(errorCode.getStatus()).body(ErrorResponse.of(errorCode, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("INTERNAL_SERVER_ERROR: {}", ex.getMessage(), ex);
        return ResponseEntity.status(ErrorCode.SERVER_ERROR.getStatus())
                .body(ErrorResponse.from(ErrorCode.SERVER_ERROR));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.warn("METHOD_ARGUMENT_NOT_VALID: {}", ex.getMessage(), ex);
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.status(status.value())
                .body(ErrorResponse.of(ErrorCode.METHOD_ARGUMENT_NOT_VALID, errorMessage));
    }
}
