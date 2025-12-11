package com.gotbufffetleh.backend.repositories;

import com.gotbufffetleh.backend.dbTables.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Reviews, Long> {

    @Query("SELECT r FROM Reviews r WHERE r.user.userId = :userId")
    List<Reviews> findByUserId(@Param("userId") long userId);

    @Query("SELECT r FROM Reviews r WHERE r.caterer.catererId = :catererId")
    List<Reviews> findByCatererId(@Param("catererId") long catererId);

    @Query("SELECT COUNT(r) FROM Reviews r WHERE r.catererId = :catererId")
    int countReviewByCatererId(@Param("catererId") long catererId);

    void deleteByReviewId(long reviewId);
}
