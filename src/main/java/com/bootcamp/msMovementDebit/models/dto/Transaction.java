package com.bootcamp.msMovementDebit.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/**
 * The type Transaction dto.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    private String typeoftransaction;

    private String identityNumber;

    private double transactionAmount;

    private String typeOfAccount;

    private String transactionDescription;

    private String customerIdentityNumber;

    private String accountUsed;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOperation;
}
