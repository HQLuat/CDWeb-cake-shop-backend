package vn.edu.hcmuaf.fit.cakeshop.modules.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.edu.hcmuaf.fit.cakeshop.modules.user.domain.entity.enums.UserRole;

@Getter
@Setter
@Builder
public class LoginResponseDTO {
    private String username;
    private String token;
    private UserRole role;
}
