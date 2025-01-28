package org.zan.demo.data.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Builder(toBuilder = true)
public class MidtransRequestDto {

    @JsonProperty("transaction_details")
    private TransactionDetails transactionDetails;

    @JsonProperty("credit_card")
    private CreditCard creditCard;

    @Setter
    @Getter
    @Builder(toBuilder = true)
    public static class TransactionDetails{
        @JsonProperty("order_id")
        private String orderId;

        @JsonProperty("gross_amount")
        private BigDecimal grossAmount;
    }

    @Setter
    @Getter
    @Builder(toBuilder = true)
    public static class CreditCard{
        @Builder.Default
        private Boolean secure = Boolean.TRUE;
    }


}
