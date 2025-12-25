package com.gotbufffetleh.backend.dbTables;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "caterers_filter_vars")
@Immutable
public class CaterersFilterVars {

  @Id
  @Column(name = "caterer_id")
  private Long catererId;

  @Column(name = "caterer_name")
  private String catererName;

  @Column(name = "avg_rating")
  private Double avgRating;

  @Column(name = "caterer_is_amazing_taste")
  private int isAmazingTaste;

  @Column(name = "caterer_is_value_for_money")
  private int isValueForMoney;
}
