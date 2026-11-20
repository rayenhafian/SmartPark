package com.smartpark.userservice.dto;

import com.smartpark.userservice.entity.Vehicle;

public record VehicleResponse(
        Long id, String plateNumber, String brand, String model, String color, Long userProfileId) {

    public static VehicleResponse from(Vehicle v) {
        return new VehicleResponse(v.getId(), v.getPlateNumber(), v.getBrand(), v.getModel(),
                v.getColor(), v.getUserProfile().getId());
    }
}
