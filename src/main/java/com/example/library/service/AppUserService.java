package com.example.library.service;

import com.example.library.Repository.AppUserRepository;
import com.example.library.Repository.RoleRepository;
import com.example.library.dto.RegistrationDto;
import com.example.library.model.AppUser;
import com.example.library.model.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository appUserRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;

        this.passwordEncoder = passwordEncoder;
    }
    public void saveAppUser(RegistrationDto appUser) {
        AppUser user = new AppUser();
        user.setUsername(appUser.getUsername());
        user.setPassword(passwordEncoder.encode(appUser.getPassword()));
        Role role = roleRepository.findRoleByRoleName("USER");
        user.setRoles(Set.of(role));
        appUserRepository.save(user);
    }

    public AppUser findAppUserByUsername(String username) {
        return appUserRepository.findAppUserByUsername(username);
    }
}
