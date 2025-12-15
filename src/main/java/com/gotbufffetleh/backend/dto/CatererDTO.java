package com.gotbufffetleh.backend.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CatererDTO {
        private Long catererId;
        private String catererName;
        private int isHalal;
        private int isTopRated;
        private int isAmazingTaste;
        private int isValueForMoney;
        private String contactNo;
        private String email;
        private String website;
        private String address;
        private double avgRating;
        private String imageUrl;
        private BigDecimal deliveryOffer;
        private int advanceOrder;
        private int numOfReviews;
        private BigDecimal deliveryFee;

        private List<GetReviewDTO> reviews;
        private List<GetMenuDTO> menus;





}
