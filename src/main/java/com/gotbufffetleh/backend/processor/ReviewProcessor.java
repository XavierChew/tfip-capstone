package com.gotbufffetleh.backend.processor;

import com.gotbufffetleh.backend.dbTables.Reviews;
import com.gotbufffetleh.backend.dto.ReviewRequest;
import com.gotbufffetleh.backend.repositories.ReviewRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReviewProcessor {
    private final ReviewRepository reviewRepository;

    public ReviewProcessor(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<ReviewRequest>getReviewsFromUserId(long userId) {
        List<Reviews> reviews = this.reviewRepository.findByUserId(userId);
        List<ReviewRequest> dtoList = new ArrayList<>();
        for (Reviews review : reviews) {

            ReviewRequest dto = new ReviewRequest();

            dto.setReviewId(review.getReviewId());
            dto.setDescript(review.getDescription());
            dto.setRating(review.getRating());
            dto.setAmazingTaste(review.getAmazingTaste());
            dto.setValueForMoney(review.getValueForMoney());
            dto.setReviewDate(review.getReviewDate());
            dto.setUserId(review.getUser().getUserId());
            dto.setCatererId(review.getCaterer().getCatererId());
            dto.setDisplayName(review.getUser().getDisplayName());
            dto.setCatererName(review.getCaterer().getCatererName());


            dtoList.add(dto);
        }
        return dtoList;


    }

    public List<ReviewRequest>getReviewsFromCatererId(long catererId) {
        List<Reviews> reviews = this.reviewRepository.findByCatererId(catererId);
        List<ReviewRequest> dtoList = new ArrayList<>();
        for (Reviews review : reviews) {

            ReviewRequest dto = new ReviewRequest();

            dto.setReviewId(review.getReviewId());
            dto.setDescript(review.getDescription());
            dto.setRating(review.getRating());
            dto.setAmazingTaste(review.getAmazingTaste());
            dto.setValueForMoney(review.getValueForMoney());
            dto.setReviewDate(review.getReviewDate());
            dto.setUserId(review.getUser().getUserId());
            dto.setCatererId(review.getCaterer().getCatererId());
            dto.setDisplayName(review.getUser().getDisplayName());
            dto.setCatererName(review.getCaterer().getCatererName());


            dtoList.add(dto);
        }
        return dtoList;


    }





}
