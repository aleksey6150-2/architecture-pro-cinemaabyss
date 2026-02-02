package ru.yandexpract.cinema.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserEventDto {

    @JsonProperty("user_id")
    private int userId;

    private String username;

    private String email;

    private String action;

    private String timestamp;

    /**
     * {
     *   "user_id": 1,
     *   "username": "john_doe",
     *   "email": "john.doe@example.com",
     *   "action": "registered",
     *   "timestamp": "2023-01-15T14:30:00Z"
     * }
     */
}
