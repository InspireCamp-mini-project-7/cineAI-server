package com.amcamp.cineAI.domain.movie.domain;

import com.amcamp.cineAI.domain.common.model.BaseTimeEntity;
import com.amcamp.cineAI.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MoviePreference extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_preference_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private MovieLikedStatus liked;

    @Builder(access = AccessLevel.PRIVATE)
    private MoviePreference(Member member, Movie movie, MovieLikedStatus liked) {
        this.member = member;
        this.movie = movie;
        this.liked = liked;
    }

    public static MoviePreference createMoviePreference(
            Member member, Movie movie, MovieLikedStatus liked) {
        return MoviePreference.builder().member(member).movie(movie).liked(liked).build();
    }

    public void updateLikedStatus() {
        if (this.liked == MovieLikedStatus.LIKED) {
            this.liked = MovieLikedStatus.DISLIKED;
        } else {
            this.liked = MovieLikedStatus.LIKED;
        }
    }
}
