package com.gotbufffetleh.backend.dbTables;

import com.gotbufffetleh.backend.dbTables.idClasses.MenuId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "menus")
@IdClass(MenuId.class)
public class Menu {

    @Id
    @Column(name = "m_name")
    private String menuName;

    @Id
    @Column(name = "caterer_id")
    private int caterer_id;

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


}
