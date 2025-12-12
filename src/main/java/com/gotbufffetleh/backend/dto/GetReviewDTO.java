package com.gotbufffetleh.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

// For Getting
@Data
public class GetReviewDTO {

    private Long reviewId;
    private String description;
    private int rating;
    private int amazingTaste;
    private int valueForMoney;
    private Long userId;
    private String displayName;
    private Long catererId;
    private String catererName;
    private Long menuId;
    private String menuName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}