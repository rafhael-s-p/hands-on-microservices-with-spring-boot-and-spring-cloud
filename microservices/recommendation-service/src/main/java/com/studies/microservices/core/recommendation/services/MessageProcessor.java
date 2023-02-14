package com.studies.microservices.core.recommendation.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import com.studies.api.core.recommendation.Recommendation;
import com.studies.api.core.recommendation.RecommendationResource;
import com.studies.api.event.Event;
import com.studies.util.exceptions.EventProcessingException;

@EnableBinding(Sink.class)
public class MessageProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(MessageProcessor.class);

    private final RecommendationResource recommendationResource;

    @Autowired
    public MessageProcessor(RecommendationResource recommendationResource) {
        this.recommendationResource = recommendationResource;
    }

    @StreamListener(target = Sink.INPUT)
    public void process(Event<Integer, Recommendation> event) {

        LOG.info("Process message created at {}...", event.getEventCreatedAt());

        switch (event.getEventType()) {

        case CREATE:
            Recommendation recommendation = event.getData();
            LOG.info("Create recommendation with ID: {}/{}", recommendation.getProductId(), recommendation.getRecommendationId());
            recommendationResource.createRecommendation(recommendation);
            break;

        case DELETE:
            int productId = event.getKey();
            LOG.info("Delete recommendations with ProductID: {}", productId);
            recommendationResource.deleteRecommendations(productId);
            break;

        default:
            String errorMessage = "Incorrect event type: " + event.getEventType() + ", expected a CREATE or DELETE event";
            LOG.warn(errorMessage);
            throw new EventProcessingException(errorMessage);
        }

        LOG.info("Message processing done!");
    }
}
