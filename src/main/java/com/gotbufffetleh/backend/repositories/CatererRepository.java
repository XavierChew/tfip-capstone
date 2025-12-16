package com.gotbufffetleh.backend.repositories;

import com.gotbufffetleh.backend.dbTables.Caterers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface CatererRepository extends JpaRepository<Caterers, Long> {


    @Query(value = "SELECT c FROM Caterers c LEFT JOIN c.reviewsList r GROUP BY c  ORDER BY coalesce( AVG(r.rating),0) DESC",
    countQuery = "SELECT COUNT(DISTINCT c) FROM Caterers c LEFT JOIN c.reviewsList r")
    Page<Caterers> findAllCaterersByAvgRating(Pageable pageable);


    @Query(value = "SELECT c FROM Caterers c LEFT JOIN c.reviewsList r WHERE " +
            "(:isHalal IS NULL OR c.isHalal = :isHalal) GROUP BY c  " +
            "HAVING (:minAvgRating IS NULL OR COALESCE(AVG(r.rating), 0) >= :minAvgRating) " +
            "ORDER BY coalesce( AVG(r.rating),0) DESC ",
    countQuery = "SELECT COUNT(DISTINCT c) FROM Caterers c LEFT JOIN c.reviewsList r " +
            "WHERE (:isHalal IS NULL OR c.isHalal = :isHalal) " +
            "GROUP BY c " +
            "HAVING (:minAvgRating IS NULL OR COALESCE(AVG(r.rating), 0) >= :minAvgRating)")
    Page<Caterers> findAllFilteredAndSorted(Pageable pageable, @Param("isHalal") Integer isHalal, @Param("minAvgRating") Double  minAvgRating);



}
