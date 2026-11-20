package com.smartpark.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserProfileRequest(
        @NotBlank @Size(max = 50) String firstName,
        @NotBlank @Size(max = 50) String lastName,
        @NotBlank @Email String email,
        @Pattern(regexp = "^\\+?[0-9]{6,15}$", message = "Numéro de téléphone invalide") String phone,
        String preferences) {
}
