package vn.edu.hcmuaf.fit.cakeshop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthResponseDTO {
    private String username;
    private String token;
}
