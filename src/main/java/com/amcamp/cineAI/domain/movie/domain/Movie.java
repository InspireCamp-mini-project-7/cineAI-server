package com.amcamp.cineAI.domain.movie.domain;

import com.amcamp.cineAI.domain.common.model.BaseTimeEntity;
import com.amcamp.cineAI.domain.movie.dto.request.NewMovieCreateRequest;
import jakarta.persistence.*;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long id;

    private String title;
    private String posterImageUrl;
    private List<String> directorName;
    private List<String> castsList;
    private String nation;

    @Column(length = 5000)
    private String plot;

    private List<String> genreList;
    private String releaseDate;

    @Enumerated(EnumType.STRING)
    private MovieStatus status;

    @Builder(access = AccessLevel.PRIVATE)
    private Movie(
            String title,
            String posterImageUrl,
            List<String> directorName,
            String nation,
            List<String> castList,
            String plot,
            List<String> genreList,
            String releaseDate,
            MovieStatus status) {
        this.title = title;
        this.posterImageUrl = posterImageUrl;
        this.directorName = directorName;
        this.castsList = castList;
        this.nation = nation;
        this.plot = plot;
        this.genreList = genreList;
        this.releaseDate = releaseDate;
        this.status = status;
    }

    public static Movie createMovie(NewMovieCreateRequest request) {
        return Movie.builder()
                .title(request.title())
                .posterImageUrl(request.posterImageUrl())
                .directorName(request.directorName())
                .castList(request.castList())
                .nation(request.nation())
                .plot(request.plot())
                .genreList(request.genreList())
                .releaseDate(request.releaseDate())
                .status(MovieStatus.CREATED)
                .build();
    }

    public static Movie createMovie(String title) {
        return Movie.builder()
                .title(title)
                .posterImageUrl("example.com")
                .directorName(Collections.singletonList("example name"))
                .nation("KR")
                .plot("Plot")
                .status(MovieStatus.CREATED)
                .build();
    }

    public static Movie createMovie(
            String title,
            String posterImageUrl,
            List<String> directorName,
            List<String> castsList,
            String nation,
            String plot,
            List<String> genreList,
            String releaseDate) {
        return Movie.builder()
                .title(title)
                .posterImageUrl(posterImageUrl)
                .directorName(directorName)
                .castList(castsList)
                .nation(nation)
                .plot(plot)
                .genreList(genreList)
                .releaseDate(releaseDate)
                .status(MovieStatus.NORMAL)
                .build();
    }
}
