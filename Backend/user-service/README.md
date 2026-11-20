# SmartPark — MS1 User Service

Spring Boot 3.3 / Java 17 / Spring Data JPA / MySQL. Implémente la section 5.1 de la documentation SmartPark.

## Lancer dans VS Code
1. Installer le « Extension Pack for Java » et « Spring Boot Extension Pack » (proposés automatiquement à l'ouverture).
2. MySQL : démarrer MySQL dans XAMPP (port 3306). Par défaut le service se connecte avec `root` sans mot de passe et crée `user_db` tout seul. Autre mot de passe : variables `DB_USER` / `DB_PASSWORD`. (Docker : `docker compose up -d`.)
3. Keycloak : un realm `smartpark` avec un rôle `ADMIN` (variable `KEYCLOAK_ISSUER_URI` si l'URL diffère).
4. F5 (configuration « UserServiceApplication ») ou `./mvnw spring-boot:run` / `mvn spring-boot:run`.

Le service écoute sur http://localhost:8081. Eureka et Config Server sont désactivés par défaut
(`EUREKA_ENABLED=true` / `CONFIG_ENABLED=true` pour les activer). Voir `requests.http` pour tester l'API.

## Structure
- `entity/` UserProfile, Vehicle (1—N)
- `repository/` Spring Data JPA
- `service/` règles métier (409 sur e-mail/immatriculation, validation conducteur)
- `controller/` endpoints de la section 5.1.3
- `security/` JWT Keycloak (rôles realm_access → ROLE_ADMIN) + règle « sub = keycloakId », sinon 403
- `exception/` format d'erreur JSON de la doc
