package com.smartpark.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VehicleRequest(
        @NotBlank @Size(max = 20) String plateNumber,
        @NotBlank @Size(max = 50) String brand,
        @NotBlank @Size(max = 50) String model,
        @Size(max = 30) String color) {
}
