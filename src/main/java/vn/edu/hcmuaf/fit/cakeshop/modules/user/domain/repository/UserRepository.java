package vn.edu.hcmuaf.fit.cakeshop.modules.user.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.cakeshop.modules.user.domain.entity.User;
import vn.edu.hcmuaf.fit.cakeshop.modules.user.domain.entity.enums.UserRole;
import vn.edu.hcmuaf.fit.cakeshop.modules.user.domain.entity.enums.UserStatus;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE " +
            "(:keyword IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "                  OR LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "                  OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND ((:status IS NOT NULL AND u.status = :status) OR (:status IS NULL AND u.status != 'INACTIVE')) " +
            "AND (:roleId IS NULL OR u.role = :roleId)")
    Page<User> searchAndFilterUsers(@Param("keyword") String keyword,
                                    @Param("status") UserStatus status,
                                    @Param("roleId") UserRole role,
                                    Pageable pageable);

    Optional<User> findByUsername(String username);

    long countByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
}
