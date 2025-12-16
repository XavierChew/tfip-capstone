package com.gotbufffetleh.backend.dto;

import lombok.Data;

@Data
public class TopCatererDTO {
    private Long catererId;
    private String catererName;
    private double avgRating;
    private int numOfReview;
    private int isTopRated;
    private int isValueForMoney;
    private int isAmazingTaste;
    private String imageUrl;

}
