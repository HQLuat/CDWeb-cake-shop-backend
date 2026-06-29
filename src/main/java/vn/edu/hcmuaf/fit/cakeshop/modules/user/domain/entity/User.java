package vn.edu.hcmuaf.fit.cakeshop.modules.user.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import vn.edu.hcmuaf.fit.cakeshop.modules.user.domain.entity.enums.UserRole;
import vn.edu.hcmuaf.fit.cakeshop.modules.user.domain.entity.enums.UserStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String fullName;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Builder.Default
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
