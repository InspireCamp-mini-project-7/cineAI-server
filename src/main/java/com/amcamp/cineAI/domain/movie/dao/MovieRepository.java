package com.amcamp.cineAI.domain.movie.dao;

import com.amcamp.cineAI.domain.movie.domain.Movie;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovieRepository extends JpaRepository<Movie, Long>, MovieRepositoryCustom {
    Movie findByTitle(String title);

    @Query(
            value =
                    "SELECT m.MOVIE_ID, m.TITLE, m.POSTER_IMAGE_URL, m.GENRE_LIST, m.RELEASE_DATE "
                            + "FROM MOVIE m "
                            + "WHERE LOWER(m.TITLE) LIKE LOWER(CONCAT('%', :keyword, '%')) "
                            + "OR LOWER(CAST(m.GENRE_LIST AS VARCHAR)) LIKE LOWER(CONCAT('%', :keyword, '%')) "
                            + "OR LOWER(CAST(m.CASTS_LIST AS VARCHAR)) LIKE LOWER(CONCAT('%', :keyword, '%')) "
                            + "OR LOWER(CAST(m.DIRECTOR_NAME AS VARCHAR)) LIKE LOWER(CONCAT('%', :keyword, '%')) "
                            + "ORDER BY m.CREATED_DT DESC "
                            + "LIMIT :limit OFFSET :offset",
            nativeQuery = true)
    List<Object[]> searchMovies(
            @Param("keyword") String keyword,
            @Param("limit") int limit,
            @Param("offset") int offset);

    @Query(
            value =
                    "SELECT COUNT(*) "
                            + "FROM MOVIE m "
                            + "WHERE LOWER(m.TITLE) LIKE LOWER(CONCAT('%', :keyword, '%')) "
                            + "OR LOWER(CAST(m.GENRE_LIST AS VARCHAR)) LIKE LOWER(CONCAT('%', :keyword, '%')) "
                            + "OR LOWER(CAST(m.CASTS_LIST AS VARCHAR)) LIKE LOWER(CONCAT('%', :keyword, '%')) "
                            + "OR LOWER(CAST(m.DIRECTOR_NAME AS VARCHAR)) LIKE LOWER(CONCAT('%', :keyword, '%')) ",
            nativeQuery = true)
    int searchMoviesTotalCnt(String keyword);
}
