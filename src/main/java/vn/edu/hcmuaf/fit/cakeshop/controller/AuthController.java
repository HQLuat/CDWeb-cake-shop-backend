package vn.edu.hcmuaf.fit.cakeshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.hcmuaf.fit.cakeshop.common.ApiResponse;
import vn.edu.hcmuaf.fit.cakeshop.dto.AuthRequestDTO;
import vn.edu.hcmuaf.fit.cakeshop.dto.AuthResponseDTO;
import vn.edu.hcmuaf.fit.cakeshop.security.CustomUserDetailsService;
import vn.edu.hcmuaf.fit.cakeshop.security.JwtService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@RequestBody AuthRequestDTO request) {
        // check username, password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // get user details
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUsername());

        // create token
        String token = jwtService.generateToken(userDetails);

        // prepare response
        AuthResponseDTO data = AuthResponseDTO.builder()
                .username(userDetails.getUsername())
                .token(token)
                .build();
        ApiResponse<AuthResponseDTO> response = ApiResponse.success("Đăng nhập thành công", data);

        return ResponseEntity.ok(response);
    }
}
