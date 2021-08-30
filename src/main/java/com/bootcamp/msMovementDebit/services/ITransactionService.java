package com.bootcamp.msMovementDebit.services;

import com.bootcamp.msMovementDebit.models.dto.Transaction;
import reactor.core.publisher.Mono;

/**
 * The interface Transaction dto service.
 */
public interface ITransactionService {
    /**
     * Save transaction mono.
     *
     * @param transaction the transaction
     * @return the mono
     */
    Mono<Transaction> saveTransaction(Transaction transaction);
}
