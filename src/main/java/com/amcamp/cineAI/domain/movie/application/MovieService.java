package com.amcamp.cineAI.domain.movie.application;

import com.amcamp.cineAI.domain.member.domain.Member;
import com.amcamp.cineAI.domain.movie.dao.MoviePreferenceRepository;
import com.amcamp.cineAI.domain.movie.dao.MovieRepository;
import com.amcamp.cineAI.domain.movie.domain.Movie;
import com.amcamp.cineAI.domain.movie.domain.MovieLikedStatus;
import com.amcamp.cineAI.domain.movie.domain.MoviePreference;
import com.amcamp.cineAI.domain.movie.dto.request.NewMovieCreateRequest;
import com.amcamp.cineAI.domain.movie.dto.response.BasicMovieInfoResponse;
import com.amcamp.cineAI.domain.movie.dto.response.MovieInfoResponse;
import com.amcamp.cineAI.global.error.exception.CustomException;
import com.amcamp.cineAI.global.error.exception.ErrorCode;
import com.amcamp.cineAI.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final MoviePreferenceRepository moviePreferenceRepository;
    private final MemberUtil memberUtil;

    public void createMovie(NewMovieCreateRequest request) {
        Movie newMovie = Movie.createMovie(request);
        movieRepository.save(newMovie);
    }

    @Transactional(readOnly = true)
    public Slice<BasicMovieInfoResponse> getNewMovieList(Long lastMovieId, int pageSize) {
        return movieRepository.findAllNewMovie(lastMovieId, pageSize);
    }

    @Transactional(readOnly = true)
    public MovieInfoResponse getMovieInfo(Long movieId) {
        Movie movie =
                movieRepository
                        .findById(movieId)
                        .orElseThrow(() -> new CustomException(ErrorCode.MOVIE_NOT_FOUND)); // 예외 던짐
        return MovieInfoResponse.of(movie);
    }

    //    @Transactional(readOnly = true)
    //    public List<BasicMovieInfoResponse> getMovieInitInfo() {
    //        // 영화 제목 넣어놓고 돌려가면서 movieRepository에서 조회
    //        // BasicMovieInfoResposne 엮은 리스트로 만들어서 반환
    //    }
    public void updateMovieLikedStatus(Long movieId) {
        Movie movie =
                movieRepository
                        .findById(movieId)
                        .orElseThrow(() -> new CustomException(ErrorCode.MOVIE_NOT_FOUND));
        Member member = memberUtil.getCurrentMember();

        MoviePreference moviePreference =
                moviePreferenceRepository.findByMovieAndMember(movie.getId(), member.getId());

        if (moviePreference != null) {
            moviePreference.updateLikedStatus();
        } else {
            moviePreferenceRepository.save(
                    MoviePreference.createMoviePreference(member, movie, MovieLikedStatus.LIKED));
        }
    }
}
