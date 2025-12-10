package com.gotbufffetleh.backend.controller;

import com.gotbufffetleh.backend.dbTables.Reviews;
import com.gotbufffetleh.backend.dto.ReviewRequest;
import com.gotbufffetleh.backend.processor.ReviewProcessor;
import com.gotbufffetleh.backend.repositories.ReviewRepository;
import com.gotbufffetleh.backend.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/review")
@CrossOrigin(origins = "http://localhost:5173")
public class ReviewController {
    private final ReviewProcessor reviewProcessor;
    private final ReviewRepository reviewRepository;


    //autowire
    public ReviewController(ReviewProcessor reviewProcessor, ReviewRepository reviewRepository) {
        this.reviewProcessor = reviewProcessor;
        this.reviewRepository = reviewRepository;
    }

    @GetMapping("/reviewsByUser")
    public List<ReviewRequest> getReviewsByUser(@RequestParam("userId") long userId){
        List<ReviewRequest> reviews = this.reviewProcessor.getReviewsFromUserId(userId);
        return reviews;
    }

    @GetMapping("/reviewsByCaterer")
    public List<ReviewRequest> getReviewsByCaterer(@RequestParam("catererId") long catererId){
        List<ReviewRequest> reviews = this.reviewProcessor.getReviewsFromCatererId(catererId);
        return reviews;
    }

    @DeleteMapping("/delete")
    ResponseEntity<?> deleteReview(@RequestParam("reviewId") Long reviewId,
                                   @RequestParam("userId") Long currentUserId) {

        Optional<Reviews> reviewOpt = reviewRepository.findById(reviewId);
        if(reviewOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Reviews reviewToDelete = reviewOpt.get();

        if(reviewToDelete.getUser().getUserId() != currentUserId) {
            return ResponseEntity.badRequest().build();
        }

        try{
            reviewRepository.deleteById(reviewId);

            return ResponseEntity.ok().build();
        } catch (Exception e){
            System.err.println("Error deleting review ID " + reviewId + ": " + e.getMessage());
            return new ResponseEntity<>("Failed to delete review due to server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
