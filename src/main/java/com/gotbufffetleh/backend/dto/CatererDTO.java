package com.gotbufffetleh.backend.dto;


import lombok.Data;

import java.time.LocalDateTime;

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
        private String address;
        private double avgRating;
        private String imageUrl;
        private int deliveryOffer;
        private int advanceOrder;
        private int numOfReviews;





}
