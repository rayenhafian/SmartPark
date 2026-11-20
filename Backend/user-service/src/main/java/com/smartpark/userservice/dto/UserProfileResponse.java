package com.smartpark.userservice.dto;

import com.smartpark.userservice.entity.UserProfile;
import java.time.LocalDateTime;

public record UserProfileResponse(
        Long id, String keycloakId, String firstName, String lastName, String email,
        String phone, String preferences, boolean active,
        LocalDateTime createdAt, LocalDateTime updatedAt) {

    public static UserProfileResponse from(UserProfile u) {
        return new UserProfileResponse(u.getId(), u.getKeycloakId(), u.getFirstName(), u.getLastName(),
                u.getEmail(), u.getPhone(), u.getPreferences(), u.isActive(), u.getCreatedAt(), u.getUpdatedAt());
    }
}
