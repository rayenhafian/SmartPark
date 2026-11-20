package com.smartpark.userservice.repository;

import com.smartpark.userservice.entity.Vehicle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByUserProfileId(Long userProfileId);
    boolean existsByPlateNumber(String plateNumber);
    boolean existsByPlateNumberAndIdNot(String plateNumber, Long id);
}
