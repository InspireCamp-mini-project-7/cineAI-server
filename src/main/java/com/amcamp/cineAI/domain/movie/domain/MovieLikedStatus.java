package com.amcamp.cineAI.domain.movie.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MovieLikedStatus {
    LIKED("MOVIE_LIKED"),
    DISLIKED("MOVIE_DISLIKED"),
    ;
    private final String liked;
}
