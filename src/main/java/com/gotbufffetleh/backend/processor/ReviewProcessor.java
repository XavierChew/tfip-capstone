package com.gotbufffetleh.backend.processor;

import com.gotbufffetleh.backend.dbTables.Caterers;
import com.gotbufffetleh.backend.dbTables.Menu;
import com.gotbufffetleh.backend.dbTables.Reviews;
import com.gotbufffetleh.backend.dbTables.User;
import com.gotbufffetleh.backend.dto.AddReviewDTO;
import com.gotbufffetleh.backend.dto.GetReviewDTO;
import com.gotbufffetleh.backend.repositories.CatererRepository;
import com.gotbufffetleh.backend.repositories.MenuRepository;
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
    private final MenuRepository menuRepository;

    public ReviewProcessor(ReviewRepository reviewRepository,  UserRepository userRepository,
                           CatererRepository catererRepository, MenuRepository menuRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.catererRepository = catererRepository;
        this.menuRepository = menuRepository;
    }

    public List<GetReviewDTO>getReviewsFromUserId(long userId) {
        List<Reviews> reviews = this.reviewRepository.findByUserId(userId);
        return getReviewRequests(reviews);

    }

    public List<GetReviewDTO>getReviewsFromCatererId(long catererId) {
        List<Reviews> reviews = this.reviewRepository.findByCatererId(catererId);
        return getReviewRequests(reviews);

    }

    //helper method to get reviewList
    private List<GetReviewDTO> getReviewRequests(List<Reviews> reviews) {
        List<GetReviewDTO> dtoList = new ArrayList<>();
        for (Reviews review : reviews) {

            GetReviewDTO dto = new GetReviewDTO();

            dto.setReviewId(review.getReviewId());
            dto.setDescription(review.getDescription());
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



    public Optional<GetReviewDTO> addReview(AddReviewDTO newReviewDTO) {

        //validate user and caterer
        Optional<User> userOpt = this.userRepository.findById(newReviewDTO.getUserId());
        Optional<Caterers> catererOpt = this.catererRepository.findById(newReviewDTO.getCatererId());
        Optional<Menu> menuOpt = this.menuRepository.findById(newReviewDTO.getMenuId());

        if(userOpt.isEmpty() || catererOpt.isEmpty() ||  menuOpt.isEmpty()) {
            return Optional.empty();
        }

        //Mapping the contents
        Reviews  newReview = new Reviews();

        newReview.setDescription(newReviewDTO.getDescription());
        newReview.setRating(newReviewDTO.getRating());
        newReview.setValueForMoney(newReviewDTO.getValueForMoney());
        newReview.setAmazingTaste(newReviewDTO.getAmazingTaste());

        newReview.setUserId(newReviewDTO.getUserId());
        newReview.setCatererId(newReviewDTO.getCatererId());
        newReview.setMenuId(newReviewDTO.getMenuId());


        newReview.setUser(userOpt.get());
        newReview.setCaterer(catererOpt.get());
        newReview.setMenu(menuOpt.get());

        newReview.setCreatedAt(LocalDateTime.now());
        newReview.setUpdatedAt(LocalDateTime.now());

        Reviews savedReview = this.reviewRepository.save(newReview);

        return Optional.of(mapToResponseDTO(savedReview));

    }

    // helper mapping method
    private GetReviewDTO mapToResponseDTO(Reviews review) {

        GetReviewDTO dto = new GetReviewDTO();

        dto.setReviewId(review.getReviewId());
        dto.setDescription(review.getDescription());
        dto.setRating(review.getRating());
        dto.setAmazingTaste(review.getAmazingTaste());
        dto.setValueForMoney(review.getValueForMoney());
        dto.setCreatedAt(review.getCreatedAt());
        dto.setUpdatedAt(review.getUpdatedAt());


        if (review.getUser() != null) {
            dto.setUserId(review.getUser().getUserId());
            dto.setDisplayName(review.getUser().getDisplayName());
        }

        if (review.getCaterer() != null) {
            dto.setCatererId(review.getCaterer().getCatererId());
            dto.setCatererName(review.getCaterer().getCatererName());
        }

        if (review.getMenu() != null) {
            dto.setMenuId(review.getMenu().getMenuId());
            dto.setMenuName(review.getMenu().getMenuName());
        }

        return dto;
    }

    public int deleteReviewById(Long reviewId, Long currentUserId ) {
        Optional<Reviews> review = this.reviewRepository.findById(reviewId);

        if ( review.isEmpty()) {
            return -1;
        }

        Reviews optReview = review.get();

        if(!optReview.getUser().getUserId().equals(currentUserId)) {
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

    public Optional<GetReviewDTO> updateReview(Long reviewId, Long currentUserId, AddReviewDTO updatedDetails) {
        Optional<Reviews> reviewOpt = this.reviewRepository.findById(reviewId);
        if ( reviewOpt.isEmpty()) {
            return Optional.empty();
        }
        Reviews reviewToEdit = reviewOpt.get();
        if(!reviewToEdit.getUser().getUserId().equals(currentUserId)) {
          return Optional.empty();
        }

        Long newMenuId = updatedDetails.getMenuId();

        //validate if there is any change in menu
        if(!reviewToEdit.getMenu().getMenuId().equals(newMenuId)) {
            Optional<Menu> newMenuOpt = this.menuRepository.findById(newMenuId);
            if (newMenuOpt.isEmpty()) {
                //invalid new menu
                return Optional.empty();
            }
            reviewToEdit.setMenu(newMenuOpt.get());
            reviewToEdit.setMenuId(newMenuId);
        }

            reviewToEdit.setDescription(updatedDetails.getDescription());
            reviewToEdit.setRating(updatedDetails.getRating());
            reviewToEdit.setUpdatedAt(LocalDateTime.now());
            reviewToEdit.setAmazingTaste(updatedDetails.getAmazingTaste());
            reviewToEdit.setValueForMoney(updatedDetails.getValueForMoney());

            Reviews editedReview = this.reviewRepository.save(reviewToEdit);

            return Optional.of(mapToResponseDTO(editedReview));

    }





}
