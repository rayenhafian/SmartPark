package com.smartpark.userservice.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

/** Règle de la doc : un conducteur n'accède qu'à son propre profil (sub du JWT = keycloakId), sinon 403. */
@Component
public class AccessChecker {

    public String currentSub() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof Jwt jwt) {
            return jwt.getSubject();
        }
        throw new AccessDeniedException("Non authentifié");
    }

    public boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    public void checkOwnerOrAdmin(String keycloakId) {
        if (!isAdmin() && !currentSub().equals(keycloakId)) {
            throw new AccessDeniedException("Accès interdit");
        }
    }
}
