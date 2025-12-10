package com.gotbufffetleh.backend.dto;

import lombok.Data;
import java.util.Date;

@Data
public class ReviewRequest {

    private Long reviewId;
    private String descript;
    private int rating;
    private int amazingTaste;
    private int valueForMoney;
    private Date reviewDate;
    private Long userId;
    private String displayName;
    private Long catererId;
    private String catererName;
}