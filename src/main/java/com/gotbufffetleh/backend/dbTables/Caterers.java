package com.gotbufffetleh.backend.dbTables;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name= "caterers")
@Getter
@Setter
public class Caterers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "caterer_id")
    private int catererId;

    @Column(name = "caterer_name")
    private String catererName;

    @Column(name = "contact_no")
    private int contactNo;

    @Column(name = "email")
    private String email;

    @Column(name = "website")
    private String website;

    @Column(name = "advance_order")
    private int advanceOrder;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "address")
    private String address;

    @Column(name = "is_halal")
    private int isHalal;

    @Column(name = "delivery_offer")
    private BigDecimal deliveryOffer;

    @OneToMany(mappedBy = "caterer")
    private List<Reviews> reviewsList;

    @OneToMany(mappedBy = "caterer")
    private List<Menu> menuList;

}
