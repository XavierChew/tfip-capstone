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



    //autowire
    public ReviewController(ReviewProcessor reviewProcessor) {
        this.reviewProcessor = reviewProcessor;
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

        int result = this.reviewProcessor.deleteReviewById(reviewId,currentUserId);

        switch (result){
            case 0 :
                return ResponseEntity.noContent().build();
            case -1 :
                return ResponseEntity.notFound().build();

            case -2 :
                return ResponseEntity.badRequest().build();

            default:
                return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


    @PostMapping("/add")
    public ResponseEntity<?> addReview(@RequestBody Reviews review) {

        if(review.getUser() == null || review.getCaterer() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Reviews> savedReview = this.reviewProcessor.addReview(review);
        if(savedReview.isPresent()) {
            return ResponseEntity.ok(savedReview.get());
        }
            return ResponseEntity.badRequest().build();

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

//    @PutMapping("/edit/{reviewId}")
//    public ResponseEntity<?> editReview(@PathVariable(value = "reviewId") Long reviewId, @RequestParam("userId") Long currentUserId, @RequestBody Reviews review){
//        Optional<Reviews> reviewsOpt =  reviewRepository.findById(reviewId);
//
//        if(reviewsOpt.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        Reviews reviewToEdit = reviewsOpt.get();
//        if(reviewToEdit.getUser().getUserId() != currentUserId) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        reviewToEdit.setDescription(review.getDescription());
//        reviewToEdit.setAmazingTaste(review.getAmazingTaste());
//        reviewToEdit.setValueForMoney(review.getValueForMoney());
//        reviewToEdit.setRating(review.getRating());
//        reviewToEdit.setUpdatedAt(LocalDateTime.now());
//
//        try {
//            Reviews savedReview = reviewRepository.save(reviewToEdit);
//            return ResponseEntity.ok(savedReview);
//        } catch (Exception e){
//            System.err.println("Error editing review ID " + reviewId + ": " + e.getMessage());
//            return new ResponseEntity<>("Failed to edit review due to server error.",
//                    HttpStatus.INTERNAL_SERVER_ERROR);
//        }

    }


}
