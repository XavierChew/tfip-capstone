package com.gotbufffetleh.backend.controller;

import com.gotbufffetleh.backend.dbTables.Reviews;
import com.gotbufffetleh.backend.dto.ReviewRequest;
import com.gotbufffetleh.backend.processor.ReviewProcessor;
import com.gotbufffetleh.backend.repositories.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
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
    public ResponseEntity<?> deleteReview(@RequestParam("reviewId") Long reviewId,
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

    // Example of JSON RequestBody reviewId = 2, userId = 3
//    {
//        "reviewId": 2,
//        "userId": 3,
//        "description" : "nubbad",
//        "rating": 5,
//        "amazingTaste": 1,
//        "valueForMoney": 1
//
//    }

    @PutMapping("/edit/{reviewId}")
    public ResponseEntity<?> editReview(@PathVariable(value = "reviewId") Long reviewId, @RequestParam("userId") Long currentUserId, @RequestBody Reviews review){
        Optional<Reviews> reviewsOpt =  reviewRepository.findById(reviewId);

        if(reviewsOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Reviews reviewToEdit = reviewsOpt.get();
        if(reviewToEdit.getUser().getUserId() != currentUserId) {
            return ResponseEntity.badRequest().build();
        }

        reviewToEdit.setDescription(review.getDescription());
        reviewToEdit.setAmazingTaste(review.getAmazingTaste());
        reviewToEdit.setValueForMoney(review.getValueForMoney());
        reviewToEdit.setRating(review.getRating());
        reviewToEdit.setReviewDate(LocalDateTime.now());

        try {
            Reviews savedReview = reviewRepository.save(reviewToEdit);
            return ResponseEntity.ok(savedReview);
        } catch (Exception e){
            System.err.println("Error editing review ID " + reviewId + ": " + e.getMessage());
            return new ResponseEntity<>("Failed to edit review due to server error.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
