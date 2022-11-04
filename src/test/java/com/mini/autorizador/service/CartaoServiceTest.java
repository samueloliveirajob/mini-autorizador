package com.mini.autorizador.service;

import com.mini.autorizador.dto.CartaoDTO;
import com.mini.autorizador.dto.TransacaoDTO;
import com.mini.autorizador.exception.CartaoException;
import com.mini.autorizador.exception.CartaoExistsException;
import com.mini.autorizador.exception.SaldoInsuficienteException;
import com.mini.autorizador.exception.SenhaIncorretaException;
import com.mini.autorizador.model.Cartao;
import com.mini.autorizador.repository.CartaoRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CartaoServiceTest {

    @Autowired(required = true)
    private CartaoService serviceCartao;

    @Autowired
    private CartaoRepository cartaoRepository;

    private String numeroCartao = "5151209566702093";
    private String senha = "123456";

    private void InicializarCartao() throws Exception {
        serviceCartao.criar(new CartaoDTO(numeroCartao, senha));

        assertEquals(500.00, cartaoRepository.findById(numeroCartao).get().getSaldo());
    }

    @Test
    @Before
    public void deveRetornarSucessoAoCriarCartaoComSaldo500() throws Exception {
        InicializarCartao();
        assertEquals(500.00, cartaoRepository.findById(numeroCartao).get().getSaldo());
    }

    @Test
    public void fazerTrasacoesAteZerarOsaldo() throws Exception {
        Double valorTransacao = 100.00;
        TransacaoDTO transacao = new TransacaoDTO(numeroCartao, senha, valorTransacao);
        while(cartaoRepository.findById(numeroCartao).get().getSaldo() => valorTransacao){
            String resultado = serviceCartao.realizarTransacao(transacao);
            assertEquals("Ok", resultado);
        }
        assertTrue(cartaoRepository.findById(numeroCartao).get().getSaldo() <= valorTransacao);
    }

    @Test
    public void fazerTrasacoesComSenhaInvalida() throws Exception {
        Double valorTransacao = 100.00;
        TransacaoDTO transacao = new TransacaoDTO(numeroCartao, senha+"1", valorTransacao);
        try {
            String resultado = serviceCartao.realizarTransacao(transacao);
            fail();
        }catch (Exception e){
            assertSame(e.getClass(), SenhaIncorretaException.class);
        }

    }

    @Test
    public void fazerTrasacoesComCartaoInexistente() throws Exception {
        Double valorTransacao = 100.00;
        TransacaoDTO transacao = new TransacaoDTO(numeroCartao+"1", senha, valorTransacao);
        try {
            String resultado = serviceCartao.realizarTransacao(transacao);
            fail();
        }catch (Exception e){
            assertSame(e.getClass(), CartaoExistsException.class);
        }

    }


}
