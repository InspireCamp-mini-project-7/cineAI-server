package com.amcamp.cineAI.domain.movie.dto.request;

import java.util.List;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MovieCreateForm {
    private String title;
    private List<String> directorName;
    private List<String> castList;
    private String nation;
    private String plot;
    private List<String> genreList;
    private String releaseDate;
    private MultipartFile posterImage;
}
