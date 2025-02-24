package com.amcamp.cineAI.domain.movie.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record NewMovieCreateRequest(
        @JsonProperty("title") String title,
        @JsonProperty("posterImageUrl") String posterImageUrl,
        @JsonProperty("directorName") List<String> directorName,
        @JsonProperty("castList") List<String> castList,
        @JsonProperty("nation") String nation,
        @JsonProperty("plot") String plot,
        @JsonProperty("genreList") List<String> genreList,
        @JsonProperty("releaseDate") String releaseDate) {

    public NewMovieCreateRequest withPosterImageUrl(String posterImageUrl) {
        return new NewMovieCreateRequest(
                this.title,
                posterImageUrl,
                this.directorName,
                this.castList,
                this.nation,
                this.plot,
                this.genreList,
                this.releaseDate);
    }
}
