package ru.yandexpract.cinema.dtos;

import lombok.Data;

@Data
public class EventResponse {

    private String status;

    private int partition;

    private long offset;

    private Event event;

    @Data
    public static class Event {

        private String id;

        private String type;

        private String timestamp;

        private Object payload;
    }
}
