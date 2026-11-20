package com.smartpark.userservice.dto;

public record DriverValidationResponse(Long userId, String keycloakId, boolean valid, boolean active) {
}
