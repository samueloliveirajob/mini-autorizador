package com.mini.autorizador.service;

import com.mini.autorizador.dto.CartaoDTO;
import com.mini.autorizador.dto.TransacaoDTO;
import com.mini.autorizador.exception.*;
import com.mini.autorizador.model.Cartao;
import com.mini.autorizador.repository.CartaoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Log4j2
@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    public ResponseEntity<Object> criar(CartaoDTO cartaoDTO) throws Exception {
        validationCartao(cartaoDTO.getNumeroCartao());

        if (cartaoRepository.existsById(cartaoDTO.getNumeroCartao())){
            return new ResponseEntity<Object>(new CartaoDTO(cartaoDTO.getNumeroCartao(), cartaoDTO.getSenha()), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Cartao cartao = new Cartao(cartaoDTO);
        Object cartaoCriado = cartaoRepository.saveAndFlush(cartao);

        return new ResponseEntity<Object>(cartaoDTO, HttpStatus.CREATED);
    }

    public Optional<Double> obterSaldo(String numeroDeCartaoExistente) throws Exception {

        validationCartao(numeroDeCartaoExistente);

        Optional<Double> saldoDoCartao = cartaoRepository.obterSaldoDoCartao(numeroDeCartaoExistente);

        return saldoDoCartao;
    }


    @Transactional
    public String realizarTransacao(TransacaoDTO transacao) throws Exception {

        Optional<Cartao> cartaoOptional = cartaoRepository.findById(transacao.getNumeroCartao());

        if(!cartaoOptional.isPresent()){
            throw new CartaoExistsException("CARTAO_INEXISTENTE");
        }
        Cartao cartao = cartaoOptional.get();
        if(!cartao.getSenha().equals(transacao.getSenha())){
            throw new SenhaIncorretaException("SENHA_INVALIDA");
        }
        cartao.validarAtribuirNovoSaldo(transacao.getValor());

        return "Ok";
    }


    private void validationCartao(String numeroCartao) throws ValidationException {
        if(numeroCartao == null || numeroCartao.equals("")){
            throw new ValidationException("Campo cartao nulo ou vazio");
        }else{
            String numCartao = numeroCartao.replace(" ", "");
            if(numCartao.length() != 16){
                log.info("Cartao deve ter 16 caracteres: "+numCartao);
                throw new ValidationException("Campo cartao com menos de 16 caracteres");
            }
            try{
                long converte = Long.valueOf(numCartao);
            } catch (Exception e){
                log.error(e.getMessage());
                throw new ValidationException("Campo cartao tem caracteres diferentes de numeros");
            }
        }
    }

    private void validationCpf(String numeroCpf) throws ValidationException {
        if(numeroCpf == null || numeroCpf.equals("")){
            throw new ValidationException("Campo cpf nulo ou vazio");
        }else{
            String numCartao = numeroCpf.replace(".", "").replace(" ", "").replace("-", "");
            if(numCartao.length() != 11){
                throw new ValidationException("cpf com menos de 11 caracteres:"+numeroCpf);
            }
            try{
                long converte = Long.valueOf(numeroCpf);
            } catch (Exception e){
                log.error(e.getMessage());
                throw new ValidationException("Campo cpf tem caracteres diferentes de numeros");
            }
        }
    }
}
