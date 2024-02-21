package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.user.Admin;
import finalproject.finalproject.Entity.user.Role;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component

public class AdminUserInitializer {
    private final AdminServiceImpl adminService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminUserInitializer(AdminServiceImpl adminService, PasswordEncoder passwordEncoder) {
        this.adminService = adminService;
        this.passwordEncoder = passwordEncoder;
    }


    @PostConstruct
    public void createAdminWhenApplicationRuns() {
        if (!adminService.existsByUsername("admin")) {
            Admin adminUser = Admin.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("adminpassword"))
                    .role(Role.ROLE_ADMIN)
                    .enabled(true)
                    .isSuperAdmin(true)
                    .build();

            adminService.save(adminUser);

            System.out.println("Admin user created successfully.");
        } else {
            System.out.println("Admin user already exists.");
        }
    }
}
