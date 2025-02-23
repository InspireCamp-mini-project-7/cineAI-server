package com.amcamp.cineAI.domain.movie.domain;

import com.amcamp.cineAI.domain.common.model.BaseTimeEntity;
import com.amcamp.cineAI.domain.movie.dto.request.NewMovieCreateRequest;
import jakarta.persistence.*;
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
    private String directorName;
    private String[] castsList;
    private String nation;
    private String plot;
    private String[] genreList;
    private Long accAudiences;
    private String releaseYear;

    @Enumerated(EnumType.STRING)
    private MovieStatus status;

    @Builder(access = AccessLevel.PRIVATE)
    private Movie(
            String title,
            String posterImageUrl,
            String directorName,
            String nation,
            String[] castList,
            String plot,
            String[] genreList,
            Long accAudiences,
            String releaseYear,
            MovieStatus status) {
        this.title = title;
        this.posterImageUrl = posterImageUrl;
        this.directorName = directorName;
        this.castsList = castList;
        this.nation = nation;
        this.plot = plot;
        this.genreList = genreList;
        this.accAudiences = accAudiences;
        this.releaseYear = releaseYear;
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
                .accAudiences(request.accAudiences())
                .releaseYear(request.releaseYear())
                .status(MovieStatus.CREATED)
                .build();
    }

    public static Movie createMovie(String title) {
        return Movie.builder()
                .title(title)
                .posterImageUrl("example.com")
                .directorName("example name")
                .nation("KR")
                .plot("Plot")
                .status(MovieStatus.CREATED)
                .build();
    }
}
