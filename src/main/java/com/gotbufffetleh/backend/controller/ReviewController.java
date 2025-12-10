package com.gotbufffetleh.backend.controller;

import com.gotbufffetleh.backend.dbTables.Reviews;
import com.gotbufffetleh.backend.dto.ReviewRequest;
import com.gotbufffetleh.backend.processor.ReviewProcessor;
import com.gotbufffetleh.backend.repositories.ReviewRepository;
import com.gotbufffetleh.backend.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/review")
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
}
