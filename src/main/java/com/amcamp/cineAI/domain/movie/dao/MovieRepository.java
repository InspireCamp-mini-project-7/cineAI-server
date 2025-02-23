package com.amcamp.cineAI.domain.movie.dao;

import com.amcamp.cineAI.domain.movie.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long>, MovieRepositoryCustom {
    Movie findByTitle(String title);
}
