package com.gotbufffetleh.backend.repositories;

import com.gotbufffetleh.backend.dbTables.Menu;
import com.gotbufffetleh.backend.dbTables.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {


    @Query("SELECT m FROM Menu m WHERE m.caterer.catererId = :catererId")
    List<Menu> findMenuByCatererId(@Param("catererId") long catererId);
}
