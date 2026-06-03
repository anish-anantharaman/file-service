package com.anish.fileservice.handler;

import com.anish.fileservice.dto.ApiResponseDto;
import com.anish.fileservice.exception.MetadataStorageException;
import com.anish.fileservice.exception.S3StorageProviderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException e) {
        log.error("Validation error: {}", e.getMessage(), e);
        StringBuilder errors = new StringBuilder();
        e.getBindingResult().getFieldErrors().forEach(error -> {
           errors.append(error.getField())
                   .append(" : ")
                   .append(error.getDefaultMessage())
                   .append(";");
        });

        ApiResponseDto response = new ApiResponseDto(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                String.valueOf(errors),
                Boolean.FALSE
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MetadataStorageException.class)
    public ResponseEntity<Object> handleMetadataStorageException(MetadataStorageException e) {
        log.error("Metadata storage error: {}", e.getMessage(), e);
        ApiResponseDto response = new ApiResponseDto(
                HttpStatus.BAD_GATEWAY.value(),
                HttpStatus.BAD_GATEWAY.getReasonPhrase(),
                "A metadata storage error occurred. Please try again later.",
                Boolean.FALSE
        );
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(response);
    }

    @ExceptionHandler(S3StorageProviderException.class)
    public ResponseEntity<Object> handleS3StorageProviderException(S3StorageProviderException e) {
        log.error("Storage provider error: {}", e.getMessage(), e);
        ApiResponseDto response = new ApiResponseDto(
                HttpStatus.BAD_GATEWAY.value(),
                HttpStatus.BAD_GATEWAY.getReasonPhrase(),
                "A file storage error occurred. Please try again later.",
                Boolean.FALSE
        );
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleServerError(Exception e) {
        log.error("Server error: {}", e.getMessage(), e);
        ApiResponseDto response = new ApiResponseDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "An internal error occurred. Please try again later.",
                Boolean.FALSE
        );
        return ResponseEntity.internalServerError().body(response);
    }
}
