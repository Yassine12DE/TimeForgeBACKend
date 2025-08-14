package tn.esprit.tic.timeforge.Controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.timeforge.Entity.Ennum.RoleName;
import tn.esprit.tic.timeforge.Entity.Role;
import tn.esprit.tic.timeforge.Entity.User;
import tn.esprit.tic.timeforge.Repository.RoleRepository;
import tn.esprit.tic.timeforge.Repository.UserRepository;
import tn.esprit.tic.timeforge.Service.security.EmailService;
import tn.esprit.tic.timeforge.Service.security.JwtUtil;
import tn.esprit.tic.timeforge.dto.LoginRequest;
import tn.esprit.tic.timeforge.dto.RegisterRequest;
import tn.esprit.tic.timeforge.dto.RoleDto;

import java.util.*;

@RestController
@RequestMapping("/auth")
@EnableAsync
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                          UserRepository userRepository, RoleRepository roleRepository,
                          EmailService emailService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();
            System.out.println(username+ password);
            User user = userRepository.findByUsername(username).orElse(null);
            if (user == null ) {
                return ResponseEntity.badRequest().body("Votre compte  n'existe pas.");
            }
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            String token = jwtUtil.generateToken(username);
            String refreshToken = jwtUtil.generateRefreshToken(username);
            String role = user.getRoles().stream()
                    .map(Role::getName)
                    .map(Enum::name)
                    .findFirst()
                    .orElse("ROLE_EMPLOYEE");

            return ResponseEntity.ok(Map.of(
                    "accessToken", token,
                    "refreshToken", refreshToken,
                    "role", role
            ));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Email is already taken");
        }

        Set<Role> userRoles = new HashSet<>();

        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            for (RoleDto roleDto : request.getRoles()) {
                RoleName roleName = RoleName.valueOf(roleDto.getName());
                Role role = roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleDto.getName()));
                userRoles.add(role);
            }
        } else {
            Role defaultRole = roleRepository.findByName(RoleName.ROLE_EMPLOYEE)
                    .orElseThrow(() -> new RuntimeException("Default role not found"));
            userRoles.add(defaultRole);
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCin(request.getCin());
        user.setName(request.getName());
        user.setRoles(userRoles);
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully. ");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> sendForgotPasswordEmail(@RequestBody Map<String, String> request) {
        String username = request.get("email");
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = optionalUser.get();
        String resetToken = UUID.randomUUID().toString();
        user.setResetpasswordtoken(resetToken);
        userRepository.save(user);
        String resetLink = "http://localhost:8089/auth/reset-password?token=" + resetToken;
        emailService.sendHtmlEmail(user.getUsername(), "Réinitialisation de votre mot de passe", "src/main/resources/templates/reset-password.html",
                Map.of("username", user.getName(), "resetPasswordLink", resetLink));


        return ResponseEntity.ok("Password reset email sent successfully");
    }
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestBody Map<String, String> request) {
        Optional<User> optionalUser = userRepository.findByResetpasswordtoken(token);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid reset token");
        }

        User user = optionalUser.get();
        String newPassword = request.get("newPassword");
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetpasswordtoken(null);
        userRepository.save(user);

        return ResponseEntity.ok("Password reset successfully");
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        try {
            String username = jwtUtil.extractUsername(refreshToken);

            if (jwtUtil.validateToken(refreshToken, username)) {
                String newAccessToken = jwtUtil.generateToken(username);
                return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
            } else {
                return ResponseEntity.status(401).body("Invalid refresh token");
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid refresh token");
        }
    }


}
