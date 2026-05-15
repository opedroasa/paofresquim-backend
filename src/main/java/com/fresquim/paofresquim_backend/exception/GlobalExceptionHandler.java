package com.fresquim.paofresquim_backend.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleConstraint(
            DataIntegrityViolationException ex
    ) {

        String mensagem = ex.getMostSpecificCause().getMessage();

        if (mensagem.contains("cpf")) {
            return ResponseEntity
                    .badRequest()
                    .body("CPF já cadastrado");
        }

        if (mensagem.contains("email")) {
            return ResponseEntity
                    .badRequest()
                    .body("E-mail já cadastrado");
        }

        return ResponseEntity
                .badRequest()
                .body("Erro de integridade nos dados");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleValidation(
            IllegalArgumentException ex
    ) {

        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(
            Exception ex
    ) {

        return ResponseEntity
                .internalServerError()
                .body("Erro interno do servidor");
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatus(
            ResponseStatusException ex
    ) {

        return ResponseEntity
                .status(ex.getStatusCode())
                .body(ex.getReason());
    }
}