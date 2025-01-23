package com.example.library.Repository;

import com.example.library.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, String> {

    AppUser findAppUserByUsername(String username);
}