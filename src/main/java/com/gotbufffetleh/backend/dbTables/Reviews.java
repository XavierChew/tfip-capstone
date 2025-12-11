package com.gotbufffetleh.backend.dbTables;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "reviews")
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private long reviewId;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "menu_id", nullable = false)
    private long menuId;

    @Column(name = "caterer_id", nullable = false)
    private long catererId;

    @Column(name = "descript")
    private String description;

    @Column(name = "rating")
    private int rating;

    @Column(name = "is_amazing_taste")
    private int amazingTaste;

    @Column(name = "is_value_for_money")
    private int valueForMoney;

    @Column(name = "review_date")
    private LocalDateTime reviewDate;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false, updatable = false,  insertable = false)
    private User user;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "caterer_id", nullable = false,insertable = false, updatable = false)
    private Caterers caterer;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "menu_id",nullable = false,insertable = false,updatable = false)
    private Menu menu;


}
