package com.lisa.lektion_6_uppgifter.repository;

import com.lisa.lektion_6_uppgifter.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomUserRepository extends JpaRepository<CustomUser,Long> {

    Optional<CustomUser> findByUsername(String username);
}
