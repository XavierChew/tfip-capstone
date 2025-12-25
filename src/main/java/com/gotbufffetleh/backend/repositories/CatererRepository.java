package com.gotbufffetleh.backend.repositories;

import com.gotbufffetleh.backend.dbTables.Caterers;
import com.gotbufffetleh.backend.dto.CatererNameDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CatererRepository extends JpaRepository<Caterers, Long> {


    @Query(value = "SELECT c FROM Caterers c LEFT JOIN c.reviewsList r GROUP BY c  ORDER BY coalesce( AVG(r.rating),0) DESC",
    countQuery = "SELECT COUNT(DISTINCT c) FROM Caterers c LEFT JOIN c.reviewsList r")
    Page<Caterers> findAllCaterersByAvgRating(Pageable pageable);


//    @Query(value = "SELECT c FROM Caterers c LEFT JOIN c.reviewsList r " +
//            "WHERE (:isHalal IS NULL OR c.isHalal = :isHalal) " +
//            "GROUP BY c " +
//            "HAVING (:minAvgRating IS NULL OR COALESCE(AVG(r.rating), 0) >= :minAvgRating) " +
//            "AND (:isAmazingTaste IS NULL OR :isAmazingTaste = false OR " +
//            " (COUNT(r) > 0 AND COALESCE(SUM(CASE WHEN r.amazingTaste = 1 THEN 1 ELSE 0 END), 0) > (COUNT(r) / 2.0))) " +
//            "AND (:isValueForMoney IS NULL OR :isValueForMoney = false OR " +
//            " (COUNT(r) > 0 AND COALESCE(SUM(CASE WHEN r.valueForMoney = 1 THEN 1 ELSE 0 END), 0) > (COUNT(r) / 2.0))) " +
//            "ORDER BY COALESCE(AVG(r.rating), 0) DESC",
//
//
//            countQuery = "SELECT COUNT(DISTINCT c.catererId) FROM Caterers c LEFT JOIN c.reviewsList r " +
//                    "WHERE (:isHalal IS NULL OR c.isHalal = :isHalal) " +
//                    "GROUP BY c.catererId " +
//                    "HAVING (:minAvgRating IS NULL OR COALESCE(AVG(r.rating), 0) >= :minAvgRating) " +
//                    "AND (:isAmazingTaste IS NULL OR :isAmazingTaste = false OR " +
//                    " (COUNT(r) > 0 AND COALESCE(SUM(CASE WHEN r.amazingTaste = 1 THEN 1 ELSE 0 END), 0) > (COUNT(r) / 2.0))) " +
//                    "AND (:isValueForMoney IS NULL OR :isValueForMoney = false OR " +
//                    " (COUNT(r) > 0 AND COALESCE(SUM(CASE WHEN r.valueForMoney = 1 THEN 1 ELSE 0 END), 0) > (COUNT(r) / 2.0)))")
    @Query(value = "SELECT DISTINCT c FROM Caterers c " +
        "JOIN c.caterersFiltersVars fv " +
        "JOIN c.reviewsList r " +
        "JOIN c.menuList m " +
        "WHERE (:isHalal IS NULL OR c.isHalal = :isHalal) " + // 1 or 0 for isHalal -> Caterers

        "AND (:minAvgRating IS NULL OR fv.avgRating >= :minAvgRating) " + // average rating -> CatererFilterVars
        "AND (:isAmazingTaste IS NULL OR :isAmazingTaste = false OR" +
        "( (CASE WHEN fv.isAmazingTaste = 1 THEN true ELSE false END) = :isAmazingTaste) ) " + // true or false for amazing taste -> CatererFilterVars
        "AND (:isValueForMoney IS NULL OR :isValueForMoney = false OR" +
        "( (CASE WHEN fv.isValueForMoney = 1 THEN true ELSE false END) = :isValueForMoney) ) " + // true or false for value for $ -> CatererFilterVars

        // NEW PARAMS: MIN GROUP SIZE and COST PER PAX
        "AND (:noOfPax IS NULL OR m.minimumPax <= :noOfPax) " + // noOfPax -> Menu

        "ORDER BY fv.avgRating DESC"
    )
    Page<Caterers> findAllFilteredAndSorted(Pageable pageable,
                                            @Param("isHalal") Integer isHalal,
                                            @Param("minAvgRating") Double minAvgRating,
                                            @Param("isAmazingTaste") Boolean isAmazingTaste,
                                            @Param("isValueForMoney") Boolean isValueForMoney,
                                            @Param("noOfPax") Integer noOfPax)
                                            ;


    @Query("SELECT DISTINCT c FROM Caterers c LEFT JOIN c.menuList m WHERE " +
        "LOWER(c.catererName) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
        "LOWER(m.menuName) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
        "LOWER(CAST(m.menuItems as String)) LIKE LOWER(CONCAT('%', :searchText, '%'))"
    )
    Page<Caterers> findCaterersBySearchText(Pageable pageable, @Param("searchText") String searchText);

//    @Query("SELECT c.catererId, c.catererName FROM Caterers c")
//    List<CatererNameDTO> findCatererDropdownData();
}
