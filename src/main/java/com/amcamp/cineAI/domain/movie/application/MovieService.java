package com.amcamp.cineAI.domain.movie.application;

import com.amcamp.cineAI.domain.llm.application.LLMService;
import com.amcamp.cineAI.domain.llm.application.PromptService;
import com.amcamp.cineAI.domain.member.domain.Member;
import com.amcamp.cineAI.domain.movie.MovieConstants;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final MoviePreferenceRepository moviePreferenceRepository;
    private final MemberUtil memberUtil;
    private final LLMService llmService;
    private final PromptService promptService;

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

    @Transactional(readOnly = true)
    public List<BasicMovieInfoResponse> getMovieInitInfo(int size) {
        List<String> movieTitleList = MovieConstants.getRandomMovies(size);
        List<Movie> movieList = getMoviesByTitles(movieTitleList);
        List<BasicMovieInfoResponse> result = new ArrayList<>();
        for (Movie movie : movieList) {
            result.add(BasicMovieInfoResponse.of(movie));
        }
        return result;
    }

    public void updateMovieLikedStatus(Long movieId) {
        Movie movie =
                movieRepository
                        .findById(movieId)
                        .orElseThrow(() -> new CustomException(ErrorCode.MOVIE_NOT_FOUND));
        Member member = memberUtil.getCurrentMember();

        MoviePreference moviePreference =
                moviePreferenceRepository.findByMovieIdAndMemberId(movie.getId(), member.getId());

        if (moviePreference != null) {
            moviePreference.updateLikedStatus();
        } else {
            moviePreferenceRepository.save(
                    MoviePreference.createMoviePreference(member, movie, MovieLikedStatus.LIKED));
        }
    }

    private List<Movie> getMoviesByTitles(List<String> movieTitles) {
        Set<String> seenTitles = new HashSet<>();
        List<Movie> movies = new ArrayList<>();
        for (String title : movieTitles) {
            if (seenTitles.contains(title)) {
                continue;
            }
            Movie movie = movieRepository.findByTitle(title);
            movies.add(movie);
            seenTitles.add(title);
        }
        return movies;
    }

    public void uploadCSV() throws IOException {
        List<Movie> movies = parseCSV();

        movieRepository.saveAll(movies);
    }

    private List<Movie> parseCSV() throws IOException {
        Resource resource = new ClassPathResource("kmdb/kmdb_data.csv");

        try (InputStream inputStream = resource.getInputStream();
                InputStreamReader reader =
                        new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                CSVParser csvParser =
                        new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            List<Movie> movies = new ArrayList<>();

            for (CSVRecord record : csvParser) {
                String title = record.get("title");
                String posterImageUrl = record.get("posterUrl");
                String directorNames = record.get("directorNm");
                String casts = record.get("actors");
                String nation = record.get("nation");
                String plot = record.get("plotText");
                String genres = record.get("genre");
                String releaseDate = record.get("releaseDate");

                // List로 변환
                List<String> directorNameList = parseCsvList(directorNames);
                List<String> castsList = parseCsvList(casts);
                List<String> genreList = parseCsvList(genres);

                // Movie 객체 생성
                Movie movie =
                        Movie.createMovie(
                                title,
                                posterImageUrl,
                                directorNameList,
                                castsList,
                                nation,
                                plot,
                                genreList,
                                releaseDate);
                movies.add(movie);
            }
            return movies;
        }
    }

    private List<String> parseCsvList(String csvValue) {
        if (csvValue == null || csvValue.isEmpty()) {
            return new ArrayList<>();
        }
        String[] values = csvValue.split(","); // 쉼표로 분리
        List<String> list = new ArrayList<>();
        for (String value : values) {
            list.add(value.trim()); // 공백 제거 후 추가
        }
        return list;
    }

    @Transactional(readOnly = true)
    public Slice<BasicMovieInfoResponse> getTodaysMoviePreferences(Long lastMovieId, int pageSize) {
        Member currentMember = memberUtil.getCurrentMember();
        List<MoviePreference> preferences =
                moviePreferenceRepository.findTop10ByMemberIdOrderByCreatedDtDesc(
                        currentMember.getId());
        List<Movie> likedMovies =
                preferences.stream()
                        .map(
                                pref ->
                                        movieRepository
                                                .findById(pref.getMovie().getId())
                                                .orElseThrow(
                                                        () ->
                                                                new CustomException(
                                                                        ErrorCode.MOVIE_NOT_FOUND)))
                        .collect(Collectors.toList());

        String preferenceData = buildPreferenceData(likedMovies);
        String prompt = promptService.getMovieSearchPrompt(preferenceData);
        String llmResponse = llmService.callLLM(prompt);

        List<String> keywords = parseKeywords(llmResponse);
        System.out.println("Generated Keywords: " + keywords);

        if (keywords.isEmpty()) {
            throw new CustomException(ErrorCode.SEARCH_KEYWORD_NOT_FOUND);
        }

        List<Movie> searchResults =
                movieRepository.searchMoviesByKeywords(keywords, lastMovieId, pageSize);

        List<BasicMovieInfoResponse> responses =
                searchResults.stream()
                        .map(
                                m ->
                                        new BasicMovieInfoResponse(
                                                m.getId(),
                                                m.getTitle(),
                                                m.getPosterImageUrl(),
                                                m.getGenreList(),
                                                m.getReleaseDate()))
                        .collect(Collectors.toList());

        boolean hasNext = responses.size() > pageSize;
        if (hasNext) {
            responses = responses.subList(0, pageSize);
        }
        return new SliceImpl<>(responses, PageRequest.of(0, pageSize), hasNext);
    }

    private String buildPreferenceData(List<Movie> movies) {
        StringBuilder genres = new StringBuilder();
        StringBuilder directors = new StringBuilder();
        StringBuilder actors = new StringBuilder();

        for (Movie movie : movies) {
            if (movie.getGenreList() != null) {
                genres.append(String.join(",", movie.getGenreList())).append(",");
            }
            if (movie.getDirectorName() != null) {
                directors.append(String.join(",", movie.getDirectorName())).append(",");
            }
            if (movie.getCastsList() != null) {
                actors.append(String.join(",", movie.getCastsList())).append(",");
            }
        }
        String genreStr = !genres.isEmpty() ? genres.substring(0, genres.length() - 1) : "";
        String directorStr =
                !directors.isEmpty() ? directors.substring(0, directors.length() - 1) : "";
        String actorStr = !actors.isEmpty() ? actors.substring(0, actors.length() - 1) : "";

        return "장르: " + genreStr + "\n감독: " + directorStr + "\n배우: " + actorStr;
    }

    private List<String> parseKeywords(String llmResponse) {
        if (llmResponse == null || llmResponse.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(llmResponse.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .limit(3)
                .collect(Collectors.toList());
    }
}
