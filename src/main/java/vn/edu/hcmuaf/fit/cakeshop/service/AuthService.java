package vn.edu.hcmuaf.fit.cakeshop.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import vn.edu.hcmuaf.fit.cakeshop.common.ApiResponse;
import vn.edu.hcmuaf.fit.cakeshop.dto.GoogleAuthRequestDTO;
import vn.edu.hcmuaf.fit.cakeshop.dto.LoginRequestDTO;
import vn.edu.hcmuaf.fit.cakeshop.dto.LoginResponseDTO;
import vn.edu.hcmuaf.fit.cakeshop.dto.RegisterRequestDTO;

public interface AuthService {
    LoginResponseDTO login(@RequestBody LoginRequestDTO request);
    void register(@RequestBody RegisterRequestDTO request);
    LoginResponseDTO googleLogin(@RequestBody GoogleAuthRequestDTO request);
}
