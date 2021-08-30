package com.bootcamp.msMovementDebit.models.dto;

import lombok.*;

/**
 * The type Debit account dto.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DebitAccount {

    private String id;
    private double amount;
    private String customerIdentityNumber;
    private String typeOfAccount;
    private String accountNumber;
}
