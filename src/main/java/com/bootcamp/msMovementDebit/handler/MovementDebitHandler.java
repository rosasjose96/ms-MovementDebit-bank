package com.bootcamp.msMovementDebit.handler;

import com.bootcamp.msMovementDebit.models.dto.Transaction;
import com.bootcamp.msMovementDebit.models.entities.MovementDebit;
import com.bootcamp.msMovementDebit.services.IDebitAccountService;
import com.bootcamp.msMovementDebit.services.IMovementDebitService;
import com.bootcamp.msMovementDebit.services.ITransactionService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


/**
 * The type Retire handler.
 */
@Component
@Slf4j(topic = "MOVEMENTDEBIT_HANDLER")
public class MovementDebitHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovementDebitHandler.class);

    @Autowired
    private IDebitAccountService accountService;

    @Autowired
    private IMovementDebitService service;

    @Autowired
    private ITransactionService transactionService;

    /**
     * Find all mono.
     *
     * @param request the request
     * @return the mono
     */
    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll(), MovementDebit.class);
    }

    /**
     * Find debit mono.
     *
     * @param request the request
     * @return the mono
     */
    public Mono<ServerResponse> findMovement(ServerRequest request) {
        String id = request.pathVariable("id");
        return service.findById(id).flatMap((c -> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(c))
                .switchIfEmpty(ServerResponse.notFound().build()))
        );
    }

    /**
     * Create deposit mono.
     *
     * @param request the request
     * @return the mono
     */
    public Mono<ServerResponse> createMovement(ServerRequest request){

        Mono<MovementDebit> movementDebitMono = request.bodyToMono(MovementDebit.class);


        return movementDebitMono.flatMap( movementRequest -> accountService
                        .findByAccountNumber(movementRequest.getPan()
                                , movementRequest.getAmount(), movementRequest.getPassword())
                .flatMap(account -> {
                    LOGGER.info("El id del débito es: " + account.getId());
                    account.setAmount(account.getAmount()- movementRequest.getAmount());
                    return accountService.updateDebit(account.getTypeOfAccount(),account);
                })
                .flatMap(ope -> {
                    transactionService.saveTransaction(Transaction.builder()
                            .typeOfAccount(ope.getTypeOfAccount())
                            .identityNumber(movementRequest.getPan())
                            .transactionAmount(movementRequest.getAmount())
                            .typeoftransaction("CONSUMPTION")
                            .accountUsed(ope.getAccountNumber())
                            .customerIdentityNumber(ope.getCustomerIdentityNumber())
                            .dateOperation(LocalDateTime.now())
                            .transactionDescription(movementRequest.getDescription()).build()).subscribe();
                    return transactionService.saveTransaction(Transaction.builder()
                            .typeOfAccount(ope.getTypeOfAccount())
                            .identityNumber(ope.getAccountNumber())
                            .transactionAmount(movementRequest.getAmount())
                            .typeoftransaction("DEBIT CONSUMPTION")
                            .customerIdentityNumber(ope.getCustomerIdentityNumber())
                            .dateOperation(LocalDateTime.now())
                            .transactionDescription(movementRequest.getDescription()).build());
                })
                .flatMap(movement ->  service.create(movementRequest)))
                .flatMap( c -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(c)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    /**
     * Delete debit mono.
     *
     * @param request the request
     * @return the mono
     */
    public Mono<ServerResponse> deleteMovement(ServerRequest request){

        String id = request.pathVariable("id");

        Mono<MovementDebit> movementDebitMono = service.findById(id);

        return movementDebitMono
                .doOnNext(c -> LOGGER.info("delete Paymencard: PaymentCardId={}", c.getId()))
                .flatMap(c -> service.delete(c).then(ServerResponse.noContent().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    /**
     * Update debit mono.
     *
     * @param request the request
     * @return the mono
     */
    public Mono<ServerResponse> updateMovement(ServerRequest request){
        Mono<MovementDebit> movementDebitMono = request.bodyToMono(MovementDebit.class);
        String id = request.pathVariable("id");

        return service.findById(id).zipWith(movementDebitMono, (db,req) -> {
            db.setAmount(req.getAmount());
            return db;
        }).flatMap( c -> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.create(c),MovementDebit.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
