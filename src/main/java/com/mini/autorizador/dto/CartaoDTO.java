package com.mini.autorizador.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class CartaoDTO {

    private String numeroCartao;
    private String senha;
    @JsonIgnore
    private Double saldo;

    public CartaoDTO(String numeroCartao, String senha) {
        this.numeroCartao = numeroCartao;
        this.senha = senha;
    }

    public CartaoDTO() {
    }
}
