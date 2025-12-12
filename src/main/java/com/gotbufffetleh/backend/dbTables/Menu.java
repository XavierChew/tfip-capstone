package com.gotbufffetleh.backend.dbTables;

import jakarta.persistence.*;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "menus")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="menu_id")
    private Long menuId;

    @Column(name = "m_name")
    private String menuName;

//    @Column(name = "caterer_id")
//    private long catererId;

    @Column(name = "minimum_pax", nullable = false)
    private int minimumPax;

    @Column(name = "no_of_courses", nullable = false)
    private int  noOfCourses;

    @Lob
    @Column(name = "menu_items")
    private String menuItems;

    @Column(name = "cost_per_pax", nullable = false)
    private BigDecimal costPerPax;

    @ManyToOne
    @JoinColumn(name = "caterer_id", nullable = false)
    private Caterers caterer;

    @OneToMany(mappedBy = "menu")
    private List<Reviews> reviewsList;

}
