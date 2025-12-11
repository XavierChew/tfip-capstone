package com.gotbufffetleh.backend.processor;

import com.gotbufffetleh.backend.dbTables.Caterers;
import com.gotbufffetleh.backend.dbTables.Reviews;
import com.gotbufffetleh.backend.dbTables.User;
import com.gotbufffetleh.backend.dto.ReviewRequest;
import com.gotbufffetleh.backend.repositories.CatererRepository;
import com.gotbufffetleh.backend.repositories.ReviewRepository;
import com.gotbufffetleh.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewProcessor {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final CatererRepository catererRepository;

    public ReviewProcessor(ReviewRepository reviewRepository,  UserRepository userRepository, CatererRepository catererRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.catererRepository = catererRepository;
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
            dto.setCreatedAt(review.getCreatedAt());
            dto.setUpdatedAt(review.getUpdatedAt());
            dto.setUserId(review.getUser().getUserId());
            dto.setCatererId(review.getCaterer().getCatererId());
            dto.setDisplayName(review.getUser().getDisplayName());
            dto.setCatererName(review.getCaterer().getCatererName());
            dto.setMenuName(review.getMenu().getMenuName());


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
            dto.setCreatedAt(review.getCreatedAt());
            dto.setUpdatedAt(review.getUpdatedAt());
            dto.setUserId(review.getUser().getUserId());
            dto.setCatererId(review.getCaterer().getCatererId());
            dto.setDisplayName(review.getUser().getDisplayName());
            dto.setCatererName(review.getCaterer().getCatererName());
            dto.setMenuName(review.getMenu().getMenuName());


            dtoList.add(dto);
        }
        return dtoList;

    }

    public Optional<Reviews> addReview(Reviews newReview) {

        //validate user and caterer
        Long userId = newReview.getUser().getUserId();
        Long catererId = newReview.getCaterer().getCatererId();


        Optional<User> userOpt = this.userRepository.findById(userId);
        Optional<Caterers> catererOpt = this.catererRepository.findById(catererId);

        if(userOpt.isEmpty() || catererOpt.isEmpty()) {
            return Optional.empty();
        }

        newReview.setUser(userOpt.get());
        newReview.setCaterer(catererOpt.get());

        if(newReview.getCreatedAt() == null) {
            newReview.setCreatedAt(LocalDateTime.now());
        }
        if(newReview.getUpdatedAt() == null) {
            newReview.setUpdatedAt(LocalDateTime.now());
        }

        Reviews savedReview = this.reviewRepository.save(newReview);
        return Optional.of(savedReview);

    }

    public int deleteReviewById(Long reviewId, Long currentUserId ) {
        Optional<Reviews> review = this.reviewRepository.findById(reviewId);

        if ( review.isEmpty()) {
            return -1;
        }

        Reviews optReview = review.get();

        if(optReview.getUser().getUserId() != currentUserId) {
            return -2;
        }

        try {
            reviewRepository.deleteById(reviewId);
            return 0;
        }  catch (Exception e) {
            System.err.println("Database error deleting review ID " + reviewId + ": " + e.getMessage());
            throw new RuntimeException("Error during review deletion process.", e);
        }


    }

}
