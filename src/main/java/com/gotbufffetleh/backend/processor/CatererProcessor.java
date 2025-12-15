package com.gotbufffetleh.backend.processor;


import com.gotbufffetleh.backend.dbTables.Caterers;
import com.gotbufffetleh.backend.dto.CatererDTO;
import com.gotbufffetleh.backend.repositories.CatererRepository;
import com.gotbufffetleh.backend.repositories.ReviewRepository;
import com.gotbufffetleh.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CatererProcessor {
    private final CatererRepository catererRepository;
    private final ReviewRepository reviewRepository;
    private final MenuProcessor menuProcessor;
    private final ReviewProcessor reviewProcessor;

    public CatererProcessor(CatererRepository catererRepository, ReviewRepository reviewRepository,
                            MenuProcessor menuProcessor,  ReviewProcessor reviewProcessor) {
        this.catererRepository = catererRepository;
        this.reviewRepository = reviewRepository;
        this.menuProcessor = menuProcessor;
        this.reviewProcessor = reviewProcessor;
    }

    //helper method to get total reviews
    public int numOfReviews(Long catererId) {

        return this.reviewRepository.countReviewByCatererId(catererId);
    }

    // helper method to get avgRating
    public double avgRating(Long catererId) {
        return this.reviewRepository.getAvgRating(catererId);
    }
//    helper method to get isAmazingTaste
    public int isAmazingTaste(Long catererId) {
        final int threshold = reviewRepository.countNumOfAmazingTaste(catererId)/2;
        if(reviewRepository.countNumOfAmazingTaste(catererId) > threshold) {
            return 1;
        }
        return 0;
    }

    //    helper method to get isValueForMoney
    public int isValueMoney(Long catererId) {
        final int threshold = reviewRepository.countNumOfValueMoney(catererId)/2;
        if(reviewRepository.countNumOfValueMoney(catererId) > threshold) {
            return 1;
        }
        return 0;
    }

    //helper method to get is isTopRated
    public int isTopRated(Long catererId) {
        final double threshold = 4.5;
        if(avgRating(catererId) > threshold) {
            return 1;
        }
        return 0;
    }

    //Get Caterer profile
    public Optional<CatererDTO> findByCatererId(long catererId) {

        Optional<Caterers> catererOpt = this.catererRepository.findById(catererId);

        if(catererOpt.isEmpty()) {
            return Optional.empty();
        }

        Caterers catererEntity = catererOpt.get();
        
        CatererDTO dto = new CatererDTO();

        dto.setCatererId(catererEntity.getCatererId());
        dto.setCatererName(catererEntity.getCatererName());
        dto.setIsTopRated(isTopRated(catererEntity.getCatererId()));
        dto.setIsAmazingTaste(isAmazingTaste(catererEntity.getCatererId()));
        dto.setIsValueForMoney(isValueMoney(catererEntity.getCatererId()));
        dto.setNumOfReviews(numOfReviews(catererEntity.getCatererId()));
        dto.setAvgRating(avgRating(catererEntity.getCatererId()));
        dto.setAddress(catererEntity.getAddress());
        dto.setEmail(catererEntity.getEmail());
        dto.setAdvanceOrder(catererEntity.getAdvanceOrder());
        dto.setContactNo(catererEntity.getContactNo());
        dto.setImageUrl(catererEntity.getImageUrl());
        dto.setEmail(catererEntity.getEmail());
        dto.setIsHalal(catererEntity.getIsHalal());
        dto.setDeliveryOffer(catererEntity.getDeliveryOffer());
        dto.setDeliveryFee(catererEntity.getDeliveryFee());
        dto.setReviews(this.reviewProcessor.getReviewsFromCatererId(catererEntity.getCatererId()));
        dto.setMenus(this.menuProcessor.findMenusByCatererId(catererEntity.getCatererId()));

        return Optional.of(dto);
    }





}
