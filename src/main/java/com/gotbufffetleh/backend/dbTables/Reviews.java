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
    private Long reviewId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "menu_id", nullable = false)
    private Long menuId;

    @Column(name = "caterer_id", nullable = false)
    private Long catererId;

    @Column(name = "descript")
    private String description;

    @Column(name = "rating")
    private int rating;

    @Column(name = "is_amazing_taste")
    private int amazingTaste;

    @Column(name = "is_value_for_money")
    private int valueForMoney;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private Caterers caterer;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private Menu menu;


}
