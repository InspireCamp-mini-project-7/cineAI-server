package com.amcamp.cineAI.domain.movie.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieInfoResponseList {
    private List<BasicMovieInfoResponse> list;
    private int limit;
    private int offset;
    private int totalCnt;
}
