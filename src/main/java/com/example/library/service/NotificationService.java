package com.example.library.service;

import com.example.library.Repository.BookLoanRepository;
import com.example.library.Repository.NotificationRepository;
import com.example.library.model.AppUser;
import com.example.library.model.BookLoan;
import com.example.library.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private BookLoanRepository bookLoanRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private AppUserService appUserService;

    public void createNotification(String username, String message) {
        Notification notification = new Notification();
        notification.setUser(appUserService.findAppUserByUsername(username));
        notification.setMessage(message);
        notification.setCreatedAt(LocalDate.now());
        notificationRepository.save(notification);
    }

    public List<Notification> getNotifications(AppUser user) {
        return notificationRepository.findByUser(user);
    }

    @Transactional
    public void checkLoanDueDates() {
        Instant tomorrow = Instant.now().plus(1, ChronoUnit.DAYS);

        List<BookLoan> dueTomorrow = bookLoanRepository.findBookLoansByStatusAndDueDateBefore(
                "BORROWED",
                tomorrow
        );

        for (BookLoan loan : dueTomorrow) {
            String message = String.format("Your loan for '%s' expires tomorrow!",
                    loan.getBook().getTitle());
            createNotification(loan.getUser().getUsername(), message);
        }

    }

    @Transactional
    public void updateOverdueLoans() {
        Instant today = Instant.now();
        List<BookLoan> overdueLoans = bookLoanRepository.findBookLoansByStatusAndDueDateBefore("BORROWED", today);

        overdueLoans.forEach(loan -> {
            loan.setStatus("OVERDUE");
            bookLoanRepository.save(loan);
            createNotification(loan.getUser().getUsername(), "OVERDUE: Book '" + loan.getBook().getTitle() + "' is overdue");
        });
    }


}

