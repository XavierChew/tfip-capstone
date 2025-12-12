package com.gotbufffetleh.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddReviewDTO {
    private Long userId;
    private Long catererId;
    private Long menuId;
    private int rating;
    private int amazingTaste;
    private int valueForMoney;
    private String description;

}
