package com.studies.microservices.core.review.services;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.studies.api.core.review.Review;
import com.studies.microservices.core.review.persistence.ReviewEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mappings({
        @Mapping(target = "serviceAddress", ignore = true)
    })
    Review entityToApi(ReviewEntity entity);

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "version", ignore = true)
    })
    ReviewEntity apiToEntity(Review api);

    List<Review> entityListToApiList(List<ReviewEntity> entity);
    List<ReviewEntity> apiListToEntityList(List<Review> api);
}