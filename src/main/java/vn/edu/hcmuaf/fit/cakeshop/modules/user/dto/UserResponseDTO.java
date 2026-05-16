package vn.edu.hcmuaf.fit.cakeshop.modules.user.dto;

import vn.edu.hcmuaf.fit.cakeshop.modules.user.domain.entity.User;
import vn.edu.hcmuaf.fit.cakeshop.modules.user.domain.entity.enums.UserStatus;

public record UserResponseDTO(
        Long id,
        String username,
        String fullName,
        String roleName,
        String email,
        String phone,
        UserStatus status
) {
    public UserResponseDTO(User user) {
        this(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getRole() != null ? user.getRole().name() : null,
                user.getEmail(),
                user.getPhone(),
                user.getStatus()
        );
    }
}
