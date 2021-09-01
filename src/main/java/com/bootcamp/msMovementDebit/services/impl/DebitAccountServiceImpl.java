package com.bootcamp.msMovementDebit.services.impl;

import com.bootcamp.msMovementDebit.models.dto.DebitAccount;
import com.bootcamp.msMovementDebit.models.entities.MovementDebit;
import com.bootcamp.msMovementDebit.services.IDebitAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Debit account dto service.
 */
@Service
public class DebitAccountServiceImpl implements IDebitAccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IDebitAccountService.class);

    @Autowired
    private WebClient.Builder webClientBuilder;


    @Override
    public Mono<DebitAccount> updateDebit(String typeofAccount, DebitAccount debitAccountDTO) {
        LOGGER.info("initializing Debit Update");
        LOGGER.info("El tipo de debito es: " + typeofAccount);
        LOGGER.info("El id del débito es: " + debitAccountDTO.getId());
        if(typeofAccount.equals("SAVING_ACCOUNT")) {
            return webClientBuilder.baseUrl("http://SAVINGACCOUNT-SERVICE/api/savingAccount")
                    .build()
                    .put()
                    .uri("/{id}", Collections.singletonMap("id", debitAccountDTO.getId()))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(debitAccountDTO)
                    .retrieve()
                    .bodyToMono(DebitAccount.class);
        }else if(typeofAccount.equals("CURRENT_ACCOUNT")) {
            return webClientBuilder.baseUrl("http://CURRENTACCOUNT-SERVICE/api/currentAccount")
                    .build()
                    .put()
                    .uri("/{id}", Collections.singletonMap("id", debitAccountDTO.getId()))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(debitAccountDTO)
                    .retrieve()
                    .bodyToMono(DebitAccount.class);
        }else if(typeofAccount.equals("FIXEDTERM_ACCOUNT")) {
            return webClientBuilder.baseUrl("http://FIXEDTERMACCOUNT-SERVICE/api/fixedTermAccount")
                    .build()
                    .put()
                    .uri("/{id}", Collections.singletonMap("id", debitAccountDTO.getId()))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(debitAccountDTO)
                    .retrieve()
                    .bodyToMono(DebitAccount.class);
        }else return Mono.empty();
    }



    @Override
    public Mono<DebitAccount> findByAccountNumber(String pan, double amount, String password) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pan",pan);
        params.put("amount",amount);
        params.put("password",password);
            return webClientBuilder.baseUrl("http://DEBITCARD-SERVICE/api/debitcard")
                    .build()
                    .get()
                    .uri("/debitUses/{pan}/{amount}/{password}",params)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchangeToMono(clientResponse -> clientResponse.bodyToMono(DebitAccount.class))
                    .doOnNext(c -> LOGGER.info("Account Response: Account Amounth={}", c.getId()));
        }

   }
