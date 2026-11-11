
package backend.backend.repository;

import backend.backend.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository
        extends JpaRepository<User, Long> {

    User findUserById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByUsernameAndIdNot(String username, Long id);

    @Query("SELECT u FROM User u WHERE " +
            "(:searchText IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :searchText, '%'))) " +
            "AND (:role IS NULL OR u.role = :role) " +
            "AND (:emailVerified IS NULL OR u.emailVerified = :emailVerified) " +
            "AND (:provider IS NULL OR u.provider = :provider)")
    List<User> SearchUser(String searchText, String role, boolean emailVerified, String provider);
}
