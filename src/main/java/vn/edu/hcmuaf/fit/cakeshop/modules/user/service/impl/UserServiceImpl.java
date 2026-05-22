package vn.edu.hcmuaf.fit.cakeshop.modules.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.cakeshop.modules.user.domain.entity.User;
import vn.edu.hcmuaf.fit.cakeshop.modules.user.domain.entity.enums.UserRole;
import vn.edu.hcmuaf.fit.cakeshop.modules.user.domain.entity.enums.UserStatus;
import vn.edu.hcmuaf.fit.cakeshop.modules.user.domain.repository.UserRepository;
import vn.edu.hcmuaf.fit.cakeshop.modules.user.dto.UserRequestDTO;
import vn.edu.hcmuaf.fit.cakeshop.modules.user.dto.UserResponseDTO;
import vn.edu.hcmuaf.fit.cakeshop.modules.user.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<UserResponseDTO> getAllUsers(String keyword, UserStatus status, UserRole role,
                                             int page, int size, String sortBy, String sortDir) {
        // configure sort (ASC or DESC)
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<User> usersPage = userRepository.searchAndFilterUsers(keyword, status, role, pageable);

        return usersPage.map(UserResponseDTO::new);
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản với ID: " + id));
        return new UserResponseDTO(user);
    }
}
