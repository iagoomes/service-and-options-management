package br.com.iagoomes.serviceandoptionsmanagement.infra.advice;

import br.com.iagoomes.serviceandoptionsmanagement.infra.exceptions.ServiceException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private final DefaultError error = new DefaultError();

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DefaultError> entityNotFound(Exception exception, HttpServletRequest request) {
        log.error("INTERNAL_SERVER_ERROR", exception);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        error.setTimeStamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Contact the administrator");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(this.error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<DefaultError> entityNotFound(EntityNotFoundException exception, HttpServletRequest request) {
        log.error("Entity not found", exception);
        HttpStatus status = HttpStatus.NOT_FOUND;
        error.setTimeStamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Resource not found");
        error.setMessage("id not found");
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(this.error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FormError> validation(MethodArgumentNotValidException exception, HttpServletRequest request) {
        log.error("Constraint Violeted", exception);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        FormError formError = new FormError();
        formError.setTimeStamp(Instant.now());
        formError.setStatus(status.value());
        formError.setError("Validation error");
        formError.setMessage("Constraint Violeted");
        formError.setPath(request.getRequestURI());

        for (FieldError field : exception.getBindingResult().getFieldErrors()) {
            formError.addMenssagens(field.getField(), field.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(formError);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<DefaultError> entityNotFound(ServiceException exception, HttpServletRequest request) {
        log.error("Service Option canceled or not exist", exception);
        HttpStatus status = HttpStatus.NOT_FOUND;
        error.setTimeStamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Resource error");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(this.error);
    }
}
