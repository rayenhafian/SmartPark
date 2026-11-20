package com.smartpark.userservice.dto;

import jakarta.validation.constraints.NotNull;

public record StatusRequest(@NotNull Boolean active) {
}
