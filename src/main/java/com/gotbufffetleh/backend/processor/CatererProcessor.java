package com.gotbufffetleh.backend.processor;


import com.gotbufffetleh.backend.dbTables.Caterers;
import com.gotbufffetleh.backend.dto.CatererDTO;
import com.gotbufffetleh.backend.dto.PaginatedCatererDTO;
import com.gotbufffetleh.backend.dto.TopCatererDTO;
import com.gotbufffetleh.backend.repositories.CatererRepository;
import com.gotbufffetleh.backend.repositories.ReviewRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;


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
    public boolean isAmazingTaste(Long catererId) {
         int threshold = reviewRepository.countTotalReviews(catererId)/2;
        return reviewRepository.countNumOfAmazingTaste(catererId) > threshold;

    }

    //    helper method to get isValueForMoney
    public boolean isValueMoney(Long catererId) {
         int threshold = reviewRepository.countTotalReviews(catererId)/2;

        return reviewRepository.countNumOfValueMoney(catererId) > threshold;

    }

    //helper method to get is isTopRated
    public boolean isTopRated(Long catererId) {
         double threshold = 4.5;
        return avgRating(catererId) >= threshold;

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
        dto.setTopRated(isTopRated(catererId));
        dto.setAmazingTaste(isAmazingTaste(catererId));
        dto.setValueForMoney(isValueMoney(catererId));
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
        dto.setTopRated(isTopRated(catererId));
        dto.setAmazingTaste(isAmazingTaste(catererId));
        dto.setValueForMoney(isValueMoney(catererId));
        dto.setNumOfReviews(numOfReviews(catererId));
        dto.setAvgRating(avgRating(catererId));
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
            dto.setTopRated(isTopRated(catererId));
            dto.setAmazingTaste(isAmazingTaste(catererId));
            dto.setValueForMoney(isValueMoney(catererId));
            dto.setIsHalal(caterers.getIsHalal());
            dto.setImageUrl(caterers.getImageUrl());
            dto.setAvgRating(avgRating(catererId));
            dto.setDeliveryOffer(caterers.getDeliveryOffer());
            dto.setMenus(this.menuProcessor.getMenusForPaginated(catererId));
            dto.setNumOfReview(numOfReviews(catererId));
            dto.setContactNo(caterers.getContactNo());
            dto.setDeliveryFee(caterers.getDeliveryFee());


            return dto;

        }

        public Page<PaginatedCatererDTO> getAllCaterers(Pageable pageable, Integer isHalal, Boolean isValueForMoney,
                                                        Boolean isAmazingTaste, Double minAvgRating) {

            //Determine query type and Pageable Settings
            boolean isSortingByAvgRating = false;

            if(pageable.getSort() != null){
                for (Sort.Order order : pageable.getSort()) {
                    if(order.getProperty().equals("avgRating")){
                        isSortingByAvgRating = true;
                        break;
                    }
                }
            }

            boolean useComplexQuery = isSortingByAvgRating || isHalal != null || minAvgRating != null;
            Pageable effectivePageable = isSortingByAvgRating ? removeSort(pageable) : pageable;

            Page<Caterers> caterersPage;

            if (useComplexQuery){

                caterersPage = catererRepository.findAllFilteredAndSorted(effectivePageable, isHalal,  minAvgRating);}
            else {
                caterersPage = catererRepository.findAll(pageable);
            }

            List<Caterers> filteredContent = new ArrayList<>();

            for (Caterers caterers : caterersPage.getContent()) {
                Long catererId =  caterers.getCatererId();

                boolean passesAmazingTaste = true;
                boolean passesValueForMoney = true;

                if(isAmazingTaste != null && isAmazingTaste ){
                    passesAmazingTaste = isAmazingTaste(catererId);
                }

                if (isValueForMoney != null && isValueForMoney) {
                    passesValueForMoney = isValueMoney(catererId);
                }

                if (passesAmazingTaste && passesValueForMoney) {
                    filteredContent.add(caterers);
                }
            }

            List<PaginatedCatererDTO> dtoList = new ArrayList<>();
            for(Caterers caterers : filteredContent){
                dtoList.add(mapToPaginatedCatererDTO(caterers));
            }

            //https://www.baeldung.com/spring-data-jpa-convert-list-page
            return new PageImpl<>(dtoList,pageable,caterersPage.getTotalElements());

        }

        //method to remove sort parameters
        private Pageable removeSort(Pageable pageable) {
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.unsorted());
        }





}
