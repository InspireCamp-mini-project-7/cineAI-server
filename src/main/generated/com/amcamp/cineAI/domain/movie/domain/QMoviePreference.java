package com.amcamp.cineAI.domain.movie.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMoviePreference is a Querydsl query type for MoviePreference
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMoviePreference extends EntityPathBase<MoviePreference> {

    private static final long serialVersionUID = 1157196052L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMoviePreference moviePreference = new QMoviePreference("moviePreference");

    public final com.amcamp.cineAI.domain.common.model.QBaseTimeEntity _super = new com.amcamp.cineAI.domain.common.model.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDt = _super.createdDt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<MovieLikedStatus> liked = createEnum("liked", MovieLikedStatus.class);

    public final com.amcamp.cineAI.domain.member.domain.QMember member;

    public final QMovie movie;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDt = _super.updatedDt;

    public QMoviePreference(String variable) {
        this(MoviePreference.class, forVariable(variable), INITS);
    }

    public QMoviePreference(Path<? extends MoviePreference> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMoviePreference(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMoviePreference(PathMetadata metadata, PathInits inits) {
        this(MoviePreference.class, metadata, inits);
    }

    public QMoviePreference(Class<? extends MoviePreference> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.amcamp.cineAI.domain.member.domain.QMember(forProperty("member")) : null;
        this.movie = inits.isInitialized("movie") ? new QMovie(forProperty("movie")) : null;
    }

}

