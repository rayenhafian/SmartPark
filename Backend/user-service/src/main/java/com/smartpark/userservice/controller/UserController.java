package com.smartpark.userservice.controller;

import com.smartpark.userservice.dto.*;
import com.smartpark.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserProfileResponse create(@Valid @RequestBody UserProfileRequest req) {
        return service.create(req);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserProfileResponse> list(Pageable pageable) {
        return service.list(pageable);
    }

    @GetMapping("/{id}")
    public UserProfileResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/keycloak/{keycloakId}")
    public UserProfileResponse getByKeycloakId(@PathVariable String keycloakId) {
        return service.getByKeycloakId(keycloakId);
    }

    @PutMapping("/{id}")
    public UserProfileResponse update(@PathVariable Long id, @Valid @RequestBody UserProfileRequest req) {
        return service.update(id, req);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public UserProfileResponse updateStatus(@PathVariable Long id, @Valid @RequestBody StatusRequest req) {
        return service.updateStatus(id, req.active());
    }

    /** Endpoint appelé par MS4 (OpenFeign) avant de créer une réservation. */
    @GetMapping("/keycloak/{keycloakId}/validation")
    public DriverValidationResponse validate(@PathVariable String keycloakId) {
        return service.validateDriver(keycloakId);
    }
}
