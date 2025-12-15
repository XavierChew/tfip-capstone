package com.gotbufffetleh.backend.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class GetMenuDTO {

    private Long menuId;
    private String menuName;
    private int minimumPax;
    private int numOfCourses;
    private BigDecimal costPerPax;
    private String menuItems;



}
