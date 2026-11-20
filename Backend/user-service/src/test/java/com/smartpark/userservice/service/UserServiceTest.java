package com.smartpark.userservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.smartpark.userservice.dto.DriverValidationResponse;
import com.smartpark.userservice.entity.UserProfile;
import com.smartpark.userservice.exception.ResourceNotFoundException;
import com.smartpark.userservice.repository.UserProfileRepository;
import com.smartpark.userservice.security.AccessChecker;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class UserServiceTest {

    private final UserProfileRepository repository = mock(UserProfileRepository.class);
    private final AccessChecker access = mock(AccessChecker.class);
    private final UserService service = new UserService(repository, access);

    @Test
    void activeDriverIsValid() {
        UserProfile u = new UserProfile();
        u.setKeycloakId("kc-1");
        when(repository.findByKeycloakId("kc-1")).thenReturn(Optional.of(u));

        DriverValidationResponse r = service.validateDriver("kc-1");

        assertTrue(r.valid());
        assertTrue(r.active());
    }

    @Test
    void deactivatedDriverIsNotValid() {
        UserProfile u = new UserProfile();
        u.setKeycloakId("kc-2");
        u.setActive(false);
        when(repository.findByKeycloakId("kc-2")).thenReturn(Optional.of(u));

        assertFalse(service.validateDriver("kc-2").valid());
    }

    @Test
    void unknownDriverThrowsNotFound() {
        when(repository.findByKeycloakId("nope")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.validateDriver("nope"));
    }
}
