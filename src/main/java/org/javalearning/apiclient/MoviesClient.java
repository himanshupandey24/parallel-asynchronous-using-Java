package org.javalearning.apiclient;

import org.javalearning.domain.movie.Movie;
import org.javalearning.domain.movie.MovieInfo;
import org.javalearning.domain.movie.Review;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MoviesClient {
    private final WebClient webClient;

    public MoviesClient(WebClient webClient) {
        this.webClient = webClient;
    }
    public Movie retrieveMovie(Long movieInfoId){

        MovieInfo movieInfo = invokeMovieInfoService(movieInfoId);
        List<Review> reviews = invokeReviewInfoService(movieInfoId);

        return new Movie(movieInfo, reviews);
    }

    public CompletableFuture<Movie> retrieveMovieCf(Long movieInfoId){

        CompletableFuture<MovieInfo> movieInfo = CompletableFuture.supplyAsync(() -> invokeMovieInfoService(movieInfoId));
        CompletableFuture<List<Review>> reviews = CompletableFuture.supplyAsync(() -> invokeReviewInfoService(movieInfoId));

        return movieInfo.thenCombine(reviews, Movie::new);
    }

    public List<Movie> retrieveMovieList(List<Long> movieInfoId){
        return movieInfoId
                .stream()
                .map(this::retrieveMovie)
                .toList();
    }

    public List<Movie> retrieveMovieListCf(List<Long> movieInfoId){

        var movieCf = movieInfoId
                .stream()
                .parallel()
                .map(this::retrieveMovieCf)
                .toList();

        return movieCf
                .stream()
                .map(CompletableFuture::join)
                .toList();
    }

    public List<Movie> retrieveMovieListCf_AllOf(List<Long> movieInfoId){

        var movieCf = movieInfoId
                .stream()
                .parallel()
                .map(this::retrieveMovieCf)
                .toList();

        var cfAllOf = CompletableFuture.allOf(movieCf.toArray(new CompletableFuture[movieCf.size()]));

        return cfAllOf
                .thenApply(v -> movieCf
                        .stream()
                        .map(CompletableFuture::join)
                        .toList())
                .join();
    }

    private List<Review> invokeReviewInfoService(Long movieInfoId) {

        String reviewInfoUrlPath = UriComponentsBuilder
                .fromUriString("/v1/reviews")
                .queryParam("movieInfoId", movieInfoId)
                .buildAndExpand()
                .toUriString();

        return webClient
                .get()
                .uri(reviewInfoUrlPath)
                .retrieve()
                .bodyToFlux(Review.class)
                .collectList()
                .block();

    }

    private MovieInfo invokeMovieInfoService(Long movieInfoId) {
        var moviesInfoUrlPath = "/v1/movie_infos/{movieInfoId}";

        return webClient
                .get()
                .uri(moviesInfoUrlPath, movieInfoId)
                .retrieve()
                .bodyToMono(MovieInfo.class)
                .block();

    }
}
