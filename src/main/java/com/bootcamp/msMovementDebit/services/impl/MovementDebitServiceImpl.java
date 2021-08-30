package com.bootcamp.msMovementDebit.services.impl;

import com.bootcamp.msMovementDebit.models.entities.MovementDebit;
import com.bootcamp.msMovementDebit.repositories.MovementDebitRepository;
import com.bootcamp.msMovementDebit.services.IMovementDebitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The type Retire service.
 */
@Service
public class MovementDebitServiceImpl implements IMovementDebitService {

    @Autowired
    private MovementDebitRepository repository;

    @Override
    public Mono<MovementDebit> create(MovementDebit o) {
        return repository.save(o);
    }

    @Override
    public Flux<MovementDebit> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<MovementDebit> findById(String s) {
        return repository.findById(s);
    }

    @Override
    public Mono<MovementDebit> update(MovementDebit o) {
        return repository.save(o);
    }

    @Override
    public Mono<Void> delete(MovementDebit o) {
        return repository.delete(o);
    }
}
