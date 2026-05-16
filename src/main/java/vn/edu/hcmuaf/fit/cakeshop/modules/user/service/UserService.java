package vn.edu.hcmuaf.fit.cakeshop.modules.user.service;

import org.springframework.data.domain.Page;
import vn.edu.hcmuaf.fit.cakeshop.modules.user.domain.entity.enums.UserRole;
import vn.edu.hcmuaf.fit.cakeshop.modules.user.domain.entity.enums.UserStatus;
import vn.edu.hcmuaf.fit.cakeshop.modules.user.dto.UserResponseDTO;

public interface UserService {
    Page<UserResponseDTO> getAllUsers(String keyword, UserStatus status, UserRole role,
                                      int page, int size, String sortBy, String sortDir);
    UserResponseDTO getUserById(Long id);
}
