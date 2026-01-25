package ru.yandexpract.cinema.web.rest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandexpract.cinema.service.ProxyService;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ProxyController {

    private final ProxyService proxyService;

    @GetMapping("/api/movies")
    public ResponseEntity<String> getMovies(HttpServletRequest request) {
        return getProxy(request, ProxyService.ProxiedServiceEnum.MOVIES);
    }

    @GetMapping("/health")
    public ResponseEntity getMoviesHealth(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", true));
    }

    @PostMapping("/api/movies")
    public ResponseEntity<String> postMovies(HttpServletRequest request, @RequestBody String body) {
        return proxyService.post(request, request.getRequestURI(), body, ProxyService.ProxiedServiceEnum.MOVIES);
    }

    @GetMapping("/api/users")
    public ResponseEntity<String> getUsers(HttpServletRequest request) {
        return getProxy(request);
    }

    @PostMapping("/api/users")
    public ResponseEntity<String> postUsers(HttpServletRequest request, @RequestBody String body) {
        return proxyService.post(request, request.getRequestURI(), body);
    }

    @GetMapping("/api/payments")
    public ResponseEntity<String> getPayments(HttpServletRequest request) {
        return getProxy(request);
    }

    @PostMapping("/api/payments")
    public ResponseEntity<String> postPayments(HttpServletRequest request, @RequestBody String body) {
        return proxyService.post(request, request.getRequestURI(), body);
    }

    @GetMapping("/api/subscriptions")
    public ResponseEntity<String> getSubscriptions(HttpServletRequest request) {
        return getProxy(request);
    }

    @PostMapping("/api/subscriptions")
    public ResponseEntity<String> postSubscriptions(HttpServletRequest request, @RequestBody String body) {
        return proxyService.post(request, request.getRequestURI(), body);
    }

    private ResponseEntity<String> getProxy(HttpServletRequest request, ProxyService.ProxiedServiceEnum serviceEnum) {
        return proxyService.get(request.getRequestURI(), request.getQueryString(), serviceEnum);
    }

    private ResponseEntity<String> getProxy(HttpServletRequest request) {
        return proxyService.get(request.getRequestURI(), request.getQueryString());
    }
}
