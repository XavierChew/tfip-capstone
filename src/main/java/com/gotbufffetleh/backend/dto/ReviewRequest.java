package com.gotbufffetleh.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class ReviewRequest {

    private Long reviewId;
    private String descript;
    private int rating;
    private int amazingTaste;
    private int valueForMoney;
    private Long userId;
    private String displayName;
    private Long catererId;
    private String catererName;
    private String menuName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}