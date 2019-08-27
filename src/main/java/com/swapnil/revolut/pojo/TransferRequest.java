package com.swapnil.revolut.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class TransferRequest {
    @NotNull
    @Min(0)
    private Long sourceAccountId;
    @NotNull
    @Min(0)
    private Long destAccountId;
    @NotNull
    @Min(0)
    private BigDecimal amount;

    @JsonCreator
    public TransferRequest(@JsonProperty("sourceAccountId") Long sourceAccountId,
                           @JsonProperty("destAccountId") Long destAccountId,
                           @JsonProperty("amount") BigDecimal amount) {
        this.sourceAccountId = sourceAccountId;
        this.destAccountId = destAccountId;
        this.amount = amount;
    }
}