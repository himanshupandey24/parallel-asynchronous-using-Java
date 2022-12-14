package org.javalearning.service;

import org.javalearning.domain.Review;

import static org.javalearning.util.CommonUtil.delay;

public class ReviewService {
    public Review retrieveReviews(String productId){

        delay(1000);
        return new Review(200, 4.5);
    }
}
