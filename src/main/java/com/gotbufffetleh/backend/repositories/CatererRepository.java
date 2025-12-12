package com.gotbufffetleh.backend.repositories;

import com.gotbufffetleh.backend.dbTables.Caterers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CatererRepository extends JpaRepository<Caterers, Long> {


    @Query("SELECT AVG(r.rating) FROM Reviews r WHERE r.catererId = :catererId")
    double getAvgRating(@Param("catererId") long catererId);




}
