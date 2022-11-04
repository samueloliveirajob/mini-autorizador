package com.mini.autorizador.model;

import com.mini.autorizador.dto.CartaoDTO;
import com.mini.autorizador.exception.SaldoInsuficienteException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Cartao {

    @Id
    @Column(unique = true)
    private String numeroCartao;
    private String senha;
    private Double saldo;

    public Cartao(CartaoDTO cartaoDTO) {
        this.numeroCartao = cartaoDTO.getNumeroCartao();
        this.senha = cartaoDTO.getSenha();
        this.saldo = 500.00;
    }

    public void validarAtribuirNovoSaldo(Double valor) throws Exception {
        if(valor > this.saldo){
            throw new SaldoInsuficienteException("SALDO_INSUFICIENTE");
        }
        this.saldo = this.saldo - valor;
    }

    public Cartao() {
    }


}
