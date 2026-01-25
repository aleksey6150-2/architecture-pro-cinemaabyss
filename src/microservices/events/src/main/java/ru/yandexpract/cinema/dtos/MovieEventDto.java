package ru.yandexpract.cinema.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MovieEventDto {

    @JsonProperty("movie_id")
    private int movieId;

    private String title;

    private String action;

    @JsonProperty("user_id")
    private int userId;

    private float rating;

    private List<String> genres;

    private String description;

    /**
     * {
     *   "movie_id": 1,
     *   "title": "Inception",
     *   "action": "viewed",
     *   "user_id": 1,
     *   "rating": 8.5,
     *   "genres": [
     *     "Sci-Fi",
     *     "Action"
     *   ],
     *   "description": "A mind-bending thriller"
     * }
     */
}
