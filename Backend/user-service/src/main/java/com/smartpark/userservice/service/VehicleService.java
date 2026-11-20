package com.smartpark.userservice.service;

import com.smartpark.userservice.dto.VehicleRequest;
import com.smartpark.userservice.dto.VehicleResponse;
import com.smartpark.userservice.entity.UserProfile;
import com.smartpark.userservice.entity.Vehicle;
import com.smartpark.userservice.exception.ConflictException;
import com.smartpark.userservice.exception.ResourceNotFoundException;
import com.smartpark.userservice.repository.VehicleRepository;
import com.smartpark.userservice.security.AccessChecker;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VehicleService {

    private final VehicleRepository repository;
    private final UserService userService;
    private final AccessChecker access;

    public VehicleService(VehicleRepository repository, UserService userService, AccessChecker access) {
        this.repository = repository;
        this.userService = userService;
        this.access = access;
    }

    public VehicleResponse add(Long userId, VehicleRequest req) {
        UserProfile owner = userService.findById(userId);
        access.checkOwnerOrAdmin(owner.getKeycloakId());
        if (repository.existsByPlateNumber(req.plateNumber())) {
            throw new ConflictException("Immatriculation déjà utilisée");
        }
        Vehicle v = new Vehicle();
        v.setUserProfile(owner);
        apply(v, req);
        return VehicleResponse.from(repository.save(v));
    }

    @Transactional(readOnly = true)
    public List<VehicleResponse> listByUser(Long userId) {
        UserProfile owner = userService.findById(userId);
        access.checkOwnerOrAdmin(owner.getKeycloakId());
        return repository.findByUserProfileId(userId).stream().map(VehicleResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public VehicleResponse get(Long vehicleId) {
        Vehicle v = findById(vehicleId);
        access.checkOwnerOrAdmin(v.getUserProfile().getKeycloakId());
        return VehicleResponse.from(v);
    }

    public VehicleResponse update(Long vehicleId, VehicleRequest req) {
        Vehicle v = findById(vehicleId);
        access.checkOwnerOrAdmin(v.getUserProfile().getKeycloakId());
        if (repository.existsByPlateNumberAndIdNot(req.plateNumber(), vehicleId)) {
            throw new ConflictException("Immatriculation déjà utilisée");
        }
        apply(v, req);
        return VehicleResponse.from(repository.save(v));
    }

    public void delete(Long vehicleId) {
        Vehicle v = findById(vehicleId);
        access.checkOwnerOrAdmin(v.getUserProfile().getKeycloakId());
        repository.delete(v);
    }

    private Vehicle findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule introuvable"));
    }

    private void apply(Vehicle v, VehicleRequest req) {
        v.setPlateNumber(req.plateNumber());
        v.setBrand(req.brand());
        v.setModel(req.model());
        v.setColor(req.color());
    }
}
