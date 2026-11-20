package com.smartpark.userservice.repository;

import com.smartpark.userservice.entity.UserProfile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByKeycloakId(String keycloakId);
    boolean existsByKeycloakId(String keycloakId);
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);
}
