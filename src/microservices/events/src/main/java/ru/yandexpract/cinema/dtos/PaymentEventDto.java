package ru.yandexpract.cinema.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PaymentEventDto {

    @JsonProperty("payment_id")
    private int paymentId;

    @JsonProperty("user_id")
    private int userId;

    private Double amount;

    private String status;

    private String timestamp;

    @JsonProperty("method_type")
    private int methodType;

    /**
     * {
     *   "payment_id": 1,
     *   "user_id": 1,
     *   "amount": 9.99,
     *   "status": "completed",
     *   "timestamp": "2023-01-15T14:30:00Z",
     *   "method_type": "credit_card"
     * }
     */
}
