package tn.esprit.tic.timeforge.Service.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.tic.timeforge.Entity.Ennum.RoleName;
import tn.esprit.tic.timeforge.Entity.Role;
import tn.esprit.tic.timeforge.Entity.User;
import tn.esprit.tic.timeforge.Repository.RoleRepository;
import tn.esprit.tic.timeforge.Repository.UserRepository;

import java.util.Optional;
import java.util.Set;

@Service
public class ServiceUser {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ServiceUser(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Role createRoleIfNotExist(RoleName roleName) {
        return roleRepository.findByName(roleName).orElseGet(() -> {
            Role role = new Role(roleName);
            return roleRepository.save(role);
        });
    }

    public void createAdminIfNotExist() {
        userRepository.findByUsername("Admin@gmail.com").ifPresentOrElse(user -> {
            System.out.println("Admin user already exists");
        }, () -> {
            User admin = new User();
            admin.setUsername("Admin@gmail.com");
            admin.setName("Admin");
            admin.setPassword(passwordEncoder.encode("password"));
            Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Role ADMIN not found"));
            admin.setRoles(Set.of(adminRole));
            userRepository.save(admin);
            System.out.println("Admin user created successfully");
        });
    }


}
