package com.gotbufffetleh.backend.repositories;


import com.gotbufffetleh.backend.dbTables.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

     Optional<User> findByEmail(String email);

}



