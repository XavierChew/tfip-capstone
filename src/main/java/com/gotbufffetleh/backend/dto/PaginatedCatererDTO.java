package com.gotbufffetleh.backend.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PaginatedCatererDTO {
    private Long catererId;
    private String catererName;
    private boolean isTopRated;
    private boolean isAmazingTaste;
    private boolean isValueForMoney;
    private int isHalal;
    private String contactNo;
    private String imageUrl;
    private double avgRating;
    private int numOfReview;
    private BigDecimal deliveryOffer;
    private BigDecimal deliveryFee;
    private List<GetMenuDTO> menus;
}
