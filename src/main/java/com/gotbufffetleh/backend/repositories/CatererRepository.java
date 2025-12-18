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


    @Query(value = "SELECT c FROM Caterers c LEFT JOIN c.reviewsList r " +
            "WHERE (:isHalal IS NULL OR c.isHalal = :isHalal) " +
            "GROUP BY c " +
            "HAVING (:minAvgRating IS NULL OR COALESCE(AVG(r.rating), 0) >= :minAvgRating) " +
            "AND (:isAmazingTaste IS NULL OR :isAmazingTaste = false OR " +
            " (COUNT(r) > 0 AND COALESCE(SUM(CASE WHEN r.amazingTaste = 1 THEN 1 ELSE 0 END), 0) > (COUNT(r) / 2.0))) " +
            "AND (:isValueForMoney IS NULL OR :isValueForMoney = false OR " +
            " (COUNT(r) > 0 AND COALESCE(SUM(CASE WHEN r.valueForMoney = 1 THEN 1 ELSE 0 END), 0) > (COUNT(r) / 2.0))) " +
            "ORDER BY COALESCE(AVG(r.rating), 0) DESC",


            countQuery = "SELECT COUNT(DISTINCT c.catererId) FROM Caterers c LEFT JOIN c.reviewsList r " +
                    "WHERE (:isHalal IS NULL OR c.isHalal = :isHalal) " +
                    "GROUP BY c.catererId " +
                    "HAVING (:minAvgRating IS NULL OR COALESCE(AVG(r.rating), 0) >= :minAvgRating) " +
                    "AND (:isAmazingTaste IS NULL OR :isAmazingTaste = false OR " +
                    " (COUNT(r) > 0 AND COALESCE(SUM(CASE WHEN r.amazingTaste = 1 THEN 1 ELSE 0 END), 0) > (COUNT(r) / 2.0))) " +
                    "AND (:isValueForMoney IS NULL OR :isValueForMoney = false OR " +
                    " (COUNT(r) > 0 AND COALESCE(SUM(CASE WHEN r.valueForMoney = 1 THEN 1 ELSE 0 END), 0) > (COUNT(r) / 2.0)))")
    Page<Caterers> findAllFilteredAndSorted(Pageable pageable,
                                            @Param("isHalal") Integer isHalal,
                                            @Param("minAvgRating") Double minAvgRating,
                                            @Param("isAmazingTaste") Boolean isAmazingTaste,
                                            @Param("isValueForMoney") Boolean isValueForMoney);


    @Query("SELECT DISTINCT c FROM Caterers c LEFT JOIN c.menuList m WHERE " +
        "LOWER(c.catererName) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
        "LOWER(m.menuName) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
        "LOWER(CAST(m.menuItems as String)) LIKE LOWER(CONCAT('%', :searchText, '%'))"
    )
    Page<Caterers> findCaterersBySearchText(Pageable pageable, @Param("searchText") String searchText);

}
