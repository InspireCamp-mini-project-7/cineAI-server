package com.amcamp.cineAI.domain.movie.dao;

import static com.amcamp.cineAI.domain.movie.domain.QMovie.movie;
import static com.amcamp.cineAI.domain.movie.domain.QMoviePreference.moviePreference;

import com.amcamp.cineAI.domain.movie.domain.MovieLikedStatus;
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
public class MoviePreferenceRepositoryImpl implements MoviePreferenceRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<BasicMovieInfoResponse> findMemberLikedMovie(
            Long lastMovieId, int pageSize, Long memberId) {
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
                        .from(moviePreference)
                        .join(moviePreference.movie, movie)
                        .where(
                                moviePreference.member.id.eq(memberId),
                                moviePreference.liked.eq(MovieLikedStatus.LIKED))
                        .limit(pageSize + 1) // 페이지 사이즈 + 1개 더 가져와서 마지막 페이지 확인
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
