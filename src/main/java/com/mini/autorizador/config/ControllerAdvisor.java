package com.mini.autorizador.config;
import com.mini.autorizador.exception.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Configuration
public class ControllerAdvisor{

    @ExceptionHandler(value={ValidationException.class, CartaoException.class, ClienteException.class})
    protected ResponseEntity<Object> handleValidationException(Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", ex.getMessage());
        return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value={CartaoExistsException.class, SenhaIncorretaException.class, SaldoInsuficienteException.class})
    protected ResponseEntity<Object> handleCartaoExistsException(Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", ex.getMessage());
        return new ResponseEntity<Object>(body, HttpStatus.UNPROCESSABLE_ENTITY);
    }


}
