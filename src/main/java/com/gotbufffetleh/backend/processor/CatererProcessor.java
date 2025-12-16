package com.gotbufffetleh.backend.processor;


import com.gotbufffetleh.backend.dbTables.Caterers;
import com.gotbufffetleh.backend.dto.CatererDTO;
import com.gotbufffetleh.backend.dto.PaginatedCatererDTO;
import com.gotbufffetleh.backend.dto.TopCatererDTO;
import com.gotbufffetleh.backend.repositories.CatererRepository;
import com.gotbufffetleh.backend.repositories.ReviewRepository;
import com.gotbufffetleh.backend.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

    //Get Top 3 Caterers
    public List<TopCatererDTO> findTop3CatererByAvgRating(){
        Pageable top3Pageable = PageRequest.of(0, 3,Sort.unsorted());
        Page<Caterers> topCatererPage = catererRepository.findAllCaterersByAvgRating(top3Pageable);
        List<TopCatererDTO> dtoList = new ArrayList<>();
        for  (Caterers caterers : topCatererPage.getContent()) {
            dtoList.add(this.mapToTopCatererDTO(caterers));

        }
        return dtoList;
    }

    // helper method to map Entity to Top 3 PaginatedCatererDTO

    private TopCatererDTO mapToTopCatererDTO(Caterers caterers){
        Long catererId = caterers.getCatererId();
        TopCatererDTO dto = new TopCatererDTO();
        dto.setCatererId(catererId);
        dto.setCatererName(caterers.getCatererName());
        dto.setIsTopRated(isTopRated(catererId));
        dto.setIsAmazingTaste(isAmazingTaste(catererId));
        dto.setIsValueForMoney(isValueMoney(catererId));
        dto.setNumOfReview(numOfReviews(catererId));
        dto.setImageUrl(caterers.getImageUrl());
        dto.setAvgRating(avgRating(catererId));
        return dto;
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
        dto.setWebsite(catererEntity.getWebsite());
        dto.setReviews(this.reviewProcessor.getReviewsFromCatererId(catererEntity.getCatererId()));
        dto.setMenus(this.menuProcessor.findMenusByCatererId(catererEntity.getCatererId()));

        return Optional.of(dto);
    }

        //https://medium.com/@ayoubtaouam/mastering-pagination-and-sorting-in-spring-boot-c2b64fd23467

        //helper method to map the Entity to PaginatedCatererDTO
        private PaginatedCatererDTO mapToPaginatedCatererDTO(Caterers caterers) {
            Long catererId = caterers.getCatererId();
            PaginatedCatererDTO dto = new PaginatedCatererDTO();
            dto.setCatererId(catererId);
            dto.setCatererName(caterers.getCatererName());
            dto.setIsTopRated(isTopRated(catererId));
            dto.setIsAmazingTaste(isAmazingTaste(catererId));
            dto.setIsValueForMoney(isValueMoney(catererId));
            dto.setIsHalal(caterers.getIsHalal());
            dto.setImageUrl(caterers.getImageUrl());
            dto.setAvgRating(avgRating(catererId));
            dto.setDeliveryOffer(caterers.getDeliveryOffer());
            dto.setMenus(this.menuProcessor.getMenusForPaginated(catererId));
            dto.setNumOfReview(numOfReviews(catererId));
            dto.setContactNo(caterers.getContactNo());


            return dto;

        }

        public Page<PaginatedCatererDTO> getAllCaterers(Pageable pageable) {
            Page<Caterers> caterersPage;
            boolean isSortingByAvgRating = false;

            if(pageable.getSort() != null){
                for (Sort.Order order : pageable.getSort()) {
                    if(order.getProperty().equals("avgRating")){
                        isSortingByAvgRating = true;
                        break;
                    }
                }
            }

            if (isSortingByAvgRating){

                Pageable unsortedPageable = removeSort(pageable);
                caterersPage = catererRepository.findAllCaterersByAvgRating(unsortedPageable);}
            else {
                caterersPage = catererRepository.findAll(pageable);
            }
            return  caterersPage.map(this::mapToPaginatedCatererDTO);

        }

        //method to remove sort parameters
        private Pageable removeSort(Pageable pageable) {
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.unsorted());
        }





}
