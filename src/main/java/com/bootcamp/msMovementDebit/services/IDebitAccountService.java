package com.bootcamp.msMovementDebit.services;

import com.bootcamp.msMovementDebit.models.dto.DebitAccount;
import reactor.core.publisher.Mono;

/**
 * The interface Debit account dto service.
 */
public interface IDebitAccountService {
    /**
     * Find by account number mono.
     *
     * @return the Mono value of an account to do the movement from the debitcard.
     */
    Mono<DebitAccount> findByAccountNumber(String pan, double amount, String password);

    /**
     * Update debit mono.
     *
     * @param typeofdebit the typeofdebit
     * @param account     the account
     * @return the mono
     */
    public Mono<DebitAccount> updateDebit(String typeofdebit, DebitAccount account);
}
