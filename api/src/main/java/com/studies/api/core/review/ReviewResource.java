package com.studies.api.core.review;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

public interface ReviewResource {

    Review createReview(@RequestBody Review body);

    /**
     * Sample usage: curl $HOST:$PORT/review?productId=1
     *
     * @param productId
     * @return
     */
    @GetMapping(
            value = "/review",
            produces = "application/json")
    Flux<Review> getReviews(@RequestParam(value = "productId") int productId);

    void deleteReviews(@RequestParam(value = "productId") int productId);
}