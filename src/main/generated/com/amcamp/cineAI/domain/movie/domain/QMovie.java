package com.amcamp.cineAI.domain.movie.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMovie is a Querydsl query type for Movie
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMovie extends EntityPathBase<Movie> {

    private static final long serialVersionUID = 1903323993L;

    public static final QMovie movie = new QMovie("movie");

    public final com.amcamp.cineAI.domain.common.model.QBaseTimeEntity _super = new com.amcamp.cineAI.domain.common.model.QBaseTimeEntity(this);

    public final NumberPath<Long> accAudiences = createNumber("accAudiences", Long.class);

    public final ArrayPath<String[], String> castsList = createArray("castsList", String[].class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDt = _super.createdDt;

    public final StringPath directorName = createString("directorName");

    public final ArrayPath<String[], String> genreList = createArray("genreList", String[].class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nation = createString("nation");

    public final StringPath plot = createString("plot");

    public final StringPath posterImageUrl = createString("posterImageUrl");

    public final StringPath releaseYear = createString("releaseYear");

    public final EnumPath<MovieStatus> status = createEnum("status", MovieStatus.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDt = _super.updatedDt;

    public QMovie(String variable) {
        super(Movie.class, forVariable(variable));
    }

    public QMovie(Path<? extends Movie> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMovie(PathMetadata metadata) {
        super(Movie.class, metadata);
    }

}

