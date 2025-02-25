package com.amcamp.cineAI.domain.movie.dao;

import static com.amcamp.cineAI.domain.movie.domain.QMovie.movie;

import com.amcamp.cineAI.domain.movie.domain.Movie;
import com.amcamp.cineAI.domain.movie.domain.MovieStatus;
import com.amcamp.cineAI.domain.movie.dto.response.BasicMovieInfoResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MovieRepositoryImpl implements MovieRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @PersistenceContext private EntityManager entityManager;

    @Override
    public Slice<BasicMovieInfoResponse> findAllNewMovie(Long lastMovieId, int pageSize) {
        List<BasicMovieInfoResponse> results =
                jpaQueryFactory
                        .select(
                                Projections.constructor(
                                        BasicMovieInfoResponse.class,
                                        movie.id,
                                        movie.title,
                                        movie.posterImageUrl,
                                        movie.genreList,
                                        movie.releaseDate))
                        .from(movie)
                        .where(movie.status.eq(MovieStatus.CREATED))
                        .orderBy(movie.createdDt.desc())
                        .limit(pageSize + 1)
                        .fetch();
        return checkLastPage(pageSize, results);
    }

    private BooleanExpression lastMemberId(Long movieId) {
        if (movieId == null) {
            return null;
        }

        return movie.id.lt(movieId);
    }

    private Slice<BasicMovieInfoResponse> checkLastPage(
            int pageSize, List<BasicMovieInfoResponse> results) {
        boolean hasNext = false;

        if (results.size() > pageSize) {
            hasNext = true;
            results.remove(pageSize);
        }

        return new SliceImpl<>(results, PageRequest.of(0, pageSize), hasNext);
    }

    @Override
    public List<Movie> searchMoviesByKeywords(
            List<String> keywords, Long lastMovieId, int pageSize) {
        if (keywords == null || keywords.isEmpty()) {
            return List.of();
        }

        StringBuilder whereBuilder = new StringBuilder();
        int index = 0;
        for (String keyword : keywords) {
            if (index > 0) {
                whereBuilder.append(" OR ");
            }
            whereBuilder
                    .append("(")
                    .append("LOWER(CAST(GENRE_LIST AS VARCHAR)) LIKE LOWER(CONCAT('%', :kw")
                    .append(index)
                    .append(", '%')) ")
                    .append("OR LOWER(CAST(DIRECTOR_NAME AS VARCHAR)) LIKE LOWER(CONCAT('%', :kw")
                    .append(index)
                    .append(", '%')) ")
                    .append("OR LOWER(CAST(CASTS_LIST AS VARCHAR)) LIKE LOWER(CONCAT('%', :kw")
                    .append(index)
                    .append(", '%'))")
                    .append(")");
            index++;
        }

        String sql = "SELECT * FROM MOVIE WHERE (" + whereBuilder.toString() + ")";

        if (lastMovieId != null) {
            sql += " AND movie_id < :lastMovieId";
        }
        sql += " ORDER BY CREATED_DT DESC LIMIT :limit";

        var query = entityManager.createNativeQuery(sql, Movie.class);
        for (int i = 0; i < keywords.size(); i++) {
            query.setParameter("kw" + i, keywords.get(i));
        }
        if (lastMovieId != null) {
            query.setParameter("lastMovieId", lastMovieId);
        }
        query.setParameter("limit", pageSize + 1);

        @SuppressWarnings("unchecked")
        List<Movie> movies = query.getResultList();
        return new ArrayList<>(new HashSet<>(movies)); // 중복 제거
    }
}
