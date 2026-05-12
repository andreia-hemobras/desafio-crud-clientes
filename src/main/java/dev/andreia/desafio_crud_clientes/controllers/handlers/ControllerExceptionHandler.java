package dev.andreia.desafio_crud_clientes.controllers.handlers;

import dev.andreia.desafio_crud_clientes.dto.CustomError;
import dev.andreia.desafio_crud_clientes.dto.ValidationError;
import dev.andreia.desafio_crud_clientes.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException ex, HttpServletRequest request){
        CustomError err = new CustomError(Instant.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> methodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request){
        ValidationError err = new ValidationError(Instant.now(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Dados inválidos", request.getRequestURI());

        for(FieldError error : ex.getBindingResult().getFieldErrors()){
            err.addError(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
    }
}
