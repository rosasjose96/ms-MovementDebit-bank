package com.bootcamp.msMovementDebit.repositories;

import com.bootcamp.msMovementDebit.models.entities.MovementDebit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MovementDebitRepository extends ReactiveMongoRepository<MovementDebit, String> {
}
