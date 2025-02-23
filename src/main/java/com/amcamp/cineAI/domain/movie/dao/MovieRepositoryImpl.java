package com.amcamp.cineAI.domain.movie.dao;

import static com.amcamp.cineAI.domain.movie.domain.QMovie.movie;

import com.amcamp.cineAI.domain.movie.domain.MovieStatus;
import com.amcamp.cineAI.domain.movie.dto.response.BasicMovieInfoResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
}
