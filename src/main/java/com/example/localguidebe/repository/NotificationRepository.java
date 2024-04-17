package com.example.localguidebe.repository;

import com.example.localguidebe.entity.Notification;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
  Page<Notification> getNotificationsByReceiverEmail(String email, Pageable pageable);

  @Query(
      "SELECT n FROM Notification n JOIN n.receiver r WHERE n.isRead = FALSE AND r.email = :email")
  List<Notification> getIsNotReadNotifications(@Param("email") String email);
}
