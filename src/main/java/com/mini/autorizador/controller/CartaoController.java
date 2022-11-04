package com.mini.autorizador.controller;

import com.mini.autorizador.dto.CartaoDTO;
import com.mini.autorizador.dto.TransacaoDTO;
import com.mini.autorizador.service.CartaoService;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@Log4j2
@RestController
@RequestMapping
@Api(value = "Cartao")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @PostMapping("/cartoes")
    public ResponseEntity<Object> criar(@RequestBody @Valid CartaoDTO cartaoDTO) throws Exception {
        return cartaoService.criar(cartaoDTO);
    }

    @GetMapping("/cartoes/{numeroCartao}")
    public ResponseEntity<Double> obterSaldo(@PathVariable(required = true) String numeroCartao) throws Exception {
        Optional<Double> saldo = cartaoService.obterSaldo(numeroCartao);

        return saldo.isPresent() ? new ResponseEntity<Double>(saldo.get(), HttpStatus.OK) : ResponseEntity.notFound().build();
    }

    @PostMapping("/transacoes")
    public ResponseEntity<String> realizarTransacao(@RequestBody @Valid TransacaoDTO transacao) throws Exception {
      return new ResponseEntity<String>(cartaoService.realizarTransacao(transacao), HttpStatus.CREATED);
    }

}
