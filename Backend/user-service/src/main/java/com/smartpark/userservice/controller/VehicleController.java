package com.smartpark.userservice.controller;

import com.smartpark.userservice.dto.VehicleRequest;
import com.smartpark.userservice.dto.VehicleResponse;
import com.smartpark.userservice.service.VehicleService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class VehicleController {

    private final VehicleService service;

    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @PostMapping("/users/{userId}/vehicles")
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleResponse add(@PathVariable Long userId, @Valid @RequestBody VehicleRequest req) {
        return service.add(userId, req);
    }

    @GetMapping("/users/{userId}/vehicles")
    public List<VehicleResponse> listByUser(@PathVariable Long userId) {
        return service.listByUser(userId);
    }

    @GetMapping("/vehicles/{vehicleId}")
    public VehicleResponse get(@PathVariable Long vehicleId) {
        return service.get(vehicleId);
    }

    @PutMapping("/vehicles/{vehicleId}")
    public VehicleResponse update(@PathVariable Long vehicleId, @Valid @RequestBody VehicleRequest req) {
        return service.update(vehicleId, req);
    }

    @DeleteMapping("/vehicles/{vehicleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long vehicleId) {
        service.delete(vehicleId);
    }
}
