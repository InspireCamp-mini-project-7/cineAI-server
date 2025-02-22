package com.amcamp.cineAI.domain.movie.dto.response;

import com.amcamp.cineAI.domain.movie.domain.Movie;

public record MovieInfoResponse(
        Long id,
        String title,
        String posterImageUrl,
        String directorName,
        String[] castsList,
        String nation,
        String plot,
        String[] genreList,
        Long accAudiences,
        String releaseYear) {
    public static MovieInfoResponse of(Movie movie) {
        return new MovieInfoResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getPosterImageUrl(),
                movie.getDirectorName(),
                movie.getCastsList(),
                movie.getNation(),
                movie.getPlot(),
                movie.getGenreList(),
                movie.getAccAudiences(),
                movie.getReleaseYear());
    }
}
