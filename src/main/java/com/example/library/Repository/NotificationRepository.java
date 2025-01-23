package com.example.library.Repository;

import com.example.library.model.AppUser;
import com.example.library.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUser(AppUser user);
}

