package com.smartpark.userservice.service;

import com.smartpark.userservice.dto.*;
import com.smartpark.userservice.entity.UserProfile;
import com.smartpark.userservice.exception.ConflictException;
import com.smartpark.userservice.exception.ResourceNotFoundException;
import com.smartpark.userservice.repository.UserProfileRepository;
import com.smartpark.userservice.security.AccessChecker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserProfileRepository repository;
    private final AccessChecker access;

    public UserService(UserProfileRepository repository, AccessChecker access) {
        this.repository = repository;
        this.access = access;
    }

    /** Crée le profil métier après l'inscription Keycloak : le keycloakId vient du sub du JWT. */
    public UserProfileResponse create(UserProfileRequest req) {
        String keycloakId = access.currentSub();
        if (repository.existsByKeycloakId(keycloakId)) {
            throw new ConflictException("Un profil existe déjà pour cet utilisateur");
        }
        if (repository.existsByEmail(req.email())) {
            throw new ConflictException("E-mail déjà utilisé");
        }
        UserProfile u = new UserProfile();
        u.setKeycloakId(keycloakId);
        apply(u, req);
        return UserProfileResponse.from(repository.save(u));
    }

    @Transactional(readOnly = true)
    public Page<UserProfileResponse> list(Pageable pageable) {
        return repository.findAll(pageable).map(UserProfileResponse::from);
    }

    @Transactional(readOnly = true)
    public UserProfileResponse get(Long id) {
        UserProfile u = findById(id);
        access.checkOwnerOrAdmin(u.getKeycloakId());
        return UserProfileResponse.from(u);
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getByKeycloakId(String keycloakId) {
        access.checkOwnerOrAdmin(keycloakId);
        return UserProfileResponse.from(findByKeycloakId(keycloakId));
    }

    public UserProfileResponse update(Long id, UserProfileRequest req) {
        UserProfile u = findById(id);
        access.checkOwnerOrAdmin(u.getKeycloakId());
        if (repository.existsByEmailAndIdNot(req.email(), id)) {
            throw new ConflictException("E-mail déjà utilisé");
        }
        apply(u, req);
        return UserProfileResponse.from(repository.save(u));
    }

    /** Activation / désactivation (US14). Un profil n'est jamais supprimé. */
    public UserProfileResponse updateStatus(Long id, boolean active) {
        UserProfile u = findById(id);
        u.setActive(active);
        return UserProfileResponse.from(repository.save(u));
    }

    /** Appelé par MS4 : valid = profil existant ET active = true. 404 si le profil n'existe pas. */
    @Transactional(readOnly = true)
    public DriverValidationResponse validateDriver(String keycloakId) {
        access.checkOwnerOrAdmin(keycloakId);
        UserProfile u = findByKeycloakId(keycloakId);
        return new DriverValidationResponse(u.getId(), u.getKeycloakId(), u.isActive(), u.isActive());
    }

    public UserProfile findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profil introuvable"));
    }

    private UserProfile findByKeycloakId(String keycloakId) {
        return repository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new ResourceNotFoundException("Profil introuvable"));
    }

    private void apply(UserProfile u, UserProfileRequest req) {
        u.setFirstName(req.firstName());
        u.setLastName(req.lastName());
        u.setEmail(req.email());
        u.setPhone(req.phone());
        u.setPreferences(req.preferences());
    }
}
