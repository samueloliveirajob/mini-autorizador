package com.mini.autorizador.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaldoInsuficienteException extends Exception{
    private String message;
}