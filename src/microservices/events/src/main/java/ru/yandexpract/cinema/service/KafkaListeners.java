package ru.yandexpract.cinema.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaListeners {

    public static final String USER_LISTENER = "eventReader";

    @KafkaListener(
            topics = {"${kafka.topics.users}"},
            groupId = USER_LISTENER
    )
    public void listenUser(String data) {
        log.info("---- USER EVENT: {}",  data);
    }

    @KafkaListener(
            topics = {"${kafka.topics.payments}"},
            groupId = USER_LISTENER
    )
    public void listenPayments(String data) {
        log.info("---- PAYMENT EVENT: {}",  data);
    }

    @KafkaListener(
            topics = {"${kafka.topics.movies}"},
            groupId = USER_LISTENER
    )
    public void listenMovies(String data) {
        log.info("---- MOVIE EVENT: {}",  data);
    }
}
