package com.gotbufffetleh.backend.dto;

import lombok.Data;

@Data
public class TopCatererDTO {
    private Long catererId;
    private String catererName;
    private double avgRating;
    private int numOfReview;
    private boolean isTopRated;
    private boolean isValueForMoney;
    private boolean isAmazingTaste;
    private String imageUrl;
    private int isHalal;
}
