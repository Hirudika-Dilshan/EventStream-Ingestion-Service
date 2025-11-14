package com.eventstream.ingestion_service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // <-- Application එකේ எல்லா errorsම handle කරන්න කියනවා
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * @Valid annotation එක fail වුනොත් (උදා: @NotBlank error)
     * මේ method එක call වෙනවා.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        // Error messages ටික ලස්සන 'Map' එකකට දානවා
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        // Validation fail වුනා කියලා 'WARN' log එකක් දානවා
        log.warn("Validation failed for request: {}", errors);

        // Client ට HTTP 400 Bad Request එක්ක error map එක return කරනවා
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Database down වුනොත් වගේ අනිත් unexpected errors
     * මේ method එකෙන් අල්ලගන්නවා.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex) {

        // Unexpected error එකක් නිසා 'ERROR' log එකක් දානවා
        log.error("An unexpected error occurred: {}", ex.getMessage(), ex);

        // Client ට HTTP 500 Internal Server Error response එකක් return කරනවා
        // Error details (ex.getMessage()) client ට යවන්නේ නෑ security reasons හින්දා.
        return new ResponseEntity<>("An internal server error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}