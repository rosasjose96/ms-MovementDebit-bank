package com.bootcamp.msMovementDebit.models.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovementDebit {

    @Id
    private String id;

    @Field(name = "amount")
    private Double amount;

    @NotNull
    private String pan;

    @NotNull
    @Size(min = 4, max = 4)
    private String password;

    private String description;

    @Field(name = "retireDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime retireDate = LocalDateTime.now();
}
