package com.gotbufffetleh.backend.repositories;

import com.gotbufffetleh.backend.dbTables.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
