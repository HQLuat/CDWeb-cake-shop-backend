package vn.edu.hcmuaf.fit.cakeshop.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import vn.edu.hcmuaf.fit.cakeshop.modules.user.domain.entity.enums.UserRole;
import vn.edu.hcmuaf.fit.cakeshop.modules.user.domain.entity.enums.UserStatus;

public record UserRequestDTO(
        @NotBlank(message = "Username không được để trống")
        @Size(min = 4, max = 20, message = "Username phải từ 4 đến 20 ký tự")
        String username,

        @NotBlank(message = "Mật khẩu không được để trống")
        @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
        String password,

        @NotBlank(message = "Họ tên không được để trống")
        String fullName,

        @NotBlank(message = "Email không được để trống")
        @Email(message = "Email không đúng định dạng")
        String email,

        String phone,
        UserRole role,
        UserStatus status
) {}
