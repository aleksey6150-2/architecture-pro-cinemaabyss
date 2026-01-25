package ru.yandexpract.cinema.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Random;

@Service
public class ProxyService {

    private final String monolithUrl;
    private final String moviesUrl;
    private final String eventsUrl;
    private final boolean gradualMigration;
    private final int migrationPercent;

    private final RestClient restClient;
    private final Random random = new Random();

    public ProxyService(@Value("${services.monolith.url}") String monolithUrl,
                        @Value("${services.movies-service.url}") String moviesUrl,
                        @Value("${services.events-service.url}") String eventsUrl,
                        @Value("${services.gradualMigration}") boolean gradualMigration,
                        @Value("${services.migrationPercent}") int migrationPercent) {
        this.monolithUrl = monolithUrl;
        this.moviesUrl = moviesUrl;
        this.eventsUrl = eventsUrl;
        this.migrationPercent = migrationPercent;
        this.gradualMigration = gradualMigration;
        this.restClient = RestClient.builder().build();
    }

    private String resolveMigrationRoute(String uri, ProxiedServiceEnum serviceEnum) {

        if (!this.gradualMigration || serviceEnum == null) {
            return uri;
        }

        return switch (serviceEnum) {
            case MOVIES -> random.nextInt(100) <= migrationPercent ?
                    this.moviesUrl : this.monolithUrl;
        };
    }

    public ResponseEntity<String> get(String uri, String queryString) {
        return get(uri, queryString, null);
    }

    public ResponseEntity<String> get(String uri, String queryString, ProxiedServiceEnum serviceEnum) {
        return restClient.get()
                .uri(resolveMigrationRoute(uri, serviceEnum) + (queryString.isEmpty() ? "" : "?" + queryString))
                .retrieve()
                .toEntity(String.class);
    }

    public ResponseEntity<String> post(HttpServletRequest request, String uri, String body) {
        return post(request, uri, body, null);
    }
    public ResponseEntity<String> post(HttpServletRequest request, String uri, String body, ProxiedServiceEnum serviceEnum) {
        return restClient.post()
                .uri(resolveMigrationRoute(uri, serviceEnum))
                .contentType(MediaType.APPLICATION_JSON)
                .body(body != null ? body : "")
                .retrieve()
                .toEntity(String.class);
    }

    public enum ProxiedServiceEnum {
        MOVIES,

        ;
    }

}
