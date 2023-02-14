package com.studies.microservices.core.review.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import com.studies.api.core.review.Review;
import com.studies.api.core.review.ReviewResource;
import com.studies.api.event.Event;
import com.studies.util.exceptions.EventProcessingException;

@EnableBinding(Sink.class)
public class MessageProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(MessageProcessor.class);

    private final ReviewResource reviewResource;

    @Autowired
    public MessageProcessor(ReviewResource reviewResource) {
        this.reviewResource = reviewResource;
    }

    @StreamListener(target = Sink.INPUT)
    public void process(Event<Integer, Review> event) {

        LOG.info("Process message created at {}...", event.getEventCreatedAt());

        switch (event.getEventType()) {

        case CREATE:
            Review review = event.getData();
            LOG.info("Create review with ID: {}/{}", review.getProductId(), review.getReviewId());
            reviewResource.createReview(review);
            break;

        case DELETE:
            int productId = event.getKey();
            LOG.info("Delete reviews with ProductID: {}", productId);
            reviewResource.deleteReviews(productId);
            break;

        default:
            String errorMessage = "Incorrect event type: " + event.getEventType() + ", expected a CREATE or DELETE event";
            LOG.warn(errorMessage);
            throw new EventProcessingException(errorMessage);
        }

        LOG.info("Message processing done!");
    }
}
