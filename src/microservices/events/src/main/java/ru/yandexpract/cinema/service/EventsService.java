package ru.yandexpract.cinema.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.yandexpract.cinema.dtos.EventResponse;
import ru.yandexpract.cinema.dtos.MovieEventDto;
import ru.yandexpract.cinema.dtos.PaymentEventDto;
import ru.yandexpract.cinema.dtos.UserEventDto;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class EventsService {

    private final String usersTopic;
    private final String paymentsTopic;
    private final String moviesTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public EventsService(KafkaTemplate<String, String> kafkaTemplate,
                         @Value("${kafka.topics.users") String usersTopic,
                         @Value("${kafka.topics.payments") String paymentsTopic,
                         @Value("${kafka.topics.movies") String moviesTopic,
                         ObjectMapper objectMapper
                         ) {
        this.usersTopic = usersTopic;
        this.paymentsTopic = paymentsTopic;
        this.moviesTopic = moviesTopic;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public EventResponse createMovieEvent(MovieEventDto dto) {
        return sendToKafka(moviesTopic, dto, dto.getMovieId(), "movie");
    }

    public EventResponse createUserEvent(UserEventDto dto) {
        return sendToKafka(usersTopic, dto, dto.getUserId(), "user");
    }

    public EventResponse createPaymentEvent(PaymentEventDto dto) {
        return sendToKafka(paymentsTopic, dto, dto.getPaymentId(), "payment");
    }

    private EventResponse sendToKafka(String topic, Object dto, int id, String type) {
        try {
            String key = type + "id";
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, key, objectMapper.writeValueAsString(dto));
            SendResult<String, String> result = future.get();
            RecordMetadata metadata = result.getRecordMetadata();

            log.info("Movie event sent successfully to partition {} at offset {}",
                    metadata.partition(), metadata.offset());

            EventResponse response = new EventResponse();

            response.setStatus("success");
            response.setPartition(metadata.partition());
            response.setOffset(metadata.offset());

            EventResponse.Event event = new EventResponse.Event();
            response.setEvent(event);
            event.setId(key);
            event.setType(type);
            event.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

            return response;
        } catch (Exception e) {
            throw new RuntimeException("Fail to send to kafka event = " + dto);
        }
    }
}
