package org.javalearning.apiclient;

import org.javalearning.util.CommonUtil;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoviesClientTest {
    WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080/movies").build();

    MoviesClient moviesClient = new MoviesClient(webClient);

    @RepeatedTest(10)
    void retrieveMovie(){
        CommonUtil.stopWatchReset();
        CommonUtil.startTimer();
        //given
        var movieInfoId = 1L;
        var movie = moviesClient.retrieveMovie(movieInfoId);

        assert movie != null;
        assertEquals("Batman Begins", movie.getMovieInfo().getName());
        assert movie.getReviewList().size() == 1;
        CommonUtil.timeTaken();
    }

    @RepeatedTest(10)
    void retrieveMovieCf(){
        CommonUtil.stopWatchReset();
        CommonUtil.startTimer();
        //given
        var movieInfoId = 1L;
        var movie = moviesClient.retrieveMovieCf(movieInfoId).join();

        assert movie != null;
        assertEquals("Batman Begins", movie.getMovieInfo().getName());
        assert movie.getReviewList().size() == 1;
        CommonUtil.timeTaken();
    }

    @RepeatedTest(10)
    void retrieveMovieList(){
        CommonUtil.stopWatchReset();
        CommonUtil.startTimer();
        //given
        var movieInfoId = List.of(1L,2L,3L,4L,5L,6L,7L);
        var movies = moviesClient.retrieveMovieList(movieInfoId);

        assert movies != null;
        assert movies.size() == 7;
        CommonUtil.timeTaken();
    }

    @RepeatedTest(10)
    void retrieveMovieListCf(){
        CommonUtil.stopWatchReset();
        CommonUtil.startTimer();
        //given
        var movieInfoId = List.of(1L,2L,3L,4L,5L,6L,7L);
        var movies = moviesClient.retrieveMovieListCf(movieInfoId);

        assert movies != null;
        assert movies.size() == 7;
        CommonUtil.timeTaken();
    }

    @RepeatedTest(10)
    void retrieveMovieListCfAllOf(){
        CommonUtil.stopWatchReset();
        CommonUtil.startTimer();
        //given
        var movieInfoId = List.of(1L,2L,3L,4L,5L,6L,7L);
        var movies = moviesClient.retrieveMovieListCf_AllOf(movieInfoId);

        assert movies != null;
        assert movies.size() == 7;
        CommonUtil.timeTaken();
    }
}