package vn.edu.hcmuaf.fit.cakeshop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import vn.edu.hcmuaf.fit.cakeshop.entity.User;
import vn.edu.hcmuaf.fit.cakeshop.entity.UserRole;
import vn.edu.hcmuaf.fit.cakeshop.entity.UserStatus;
import vn.edu.hcmuaf.fit.cakeshop.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setEmail("admin@gmail.com");
            admin.setRole(UserRole.ADMIN);
            admin.setStatus(UserStatus.ACTIVE);

            userRepository.save(admin);

            System.out.println("[Important] Initialized root admin successfully!");
        }
    }
}
