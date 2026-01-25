package ru.yandexpract.cinema.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandexpract.cinema.dtos.EventResponse;
import ru.yandexpract.cinema.dtos.MovieEventDto;
import ru.yandexpract.cinema.dtos.PaymentEventDto;
import ru.yandexpract.cinema.dtos.UserEventDto;
import ru.yandexpract.cinema.service.EventsService;

import java.util.Map;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventsResource {

    private final EventsService eventsService;

    @PostMapping("/movie")
    public ResponseEntity<EventResponse> createMovieEvent(@RequestBody MovieEventDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(eventsService.createMovieEvent(dto));
    }

    @PostMapping("/user")
    public ResponseEntity<EventResponse> createUserEvent(@RequestBody UserEventDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(eventsService.createUserEvent(dto));
    }

    @PostMapping("/payment")
    public ResponseEntity<EventResponse> createPaymentEvent(@RequestBody PaymentEventDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(eventsService.createPaymentEvent(dto));
    }

    @GetMapping("/health")
    public ResponseEntity health() {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", true));
    }


}
