package com.bootcamp.msMovementDebit.config;

import com.bootcamp.msMovementDebit.handler.MovementDebitHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    /**
     * Rutas router function.
     *
     * @param movementDebitHandler the handler
     * @return the router function
     */
    @Bean
    public RouterFunction<ServerResponse> rutas(MovementDebitHandler movementDebitHandler){
        return route(GET("/api/movement"), movementDebitHandler::findAll)
                .andRoute(GET("/api/movement/{id}"), movementDebitHandler::findMovement)
                .andRoute(DELETE("/api/movement/{id}"), movementDebitHandler::deleteMovement)
                .andRoute(PUT("/api/movement/{id}"), movementDebitHandler::updateMovement)
                .andRoute(POST("/api/movement"), movementDebitHandler::createMovement);
    }
}
