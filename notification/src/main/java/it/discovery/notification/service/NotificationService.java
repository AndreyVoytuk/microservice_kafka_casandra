package it.discovery.notification.service;

import it.discovery.event.NotificationCreatedEvent;
import it.discovery.notification.domain.Notification;
import it.discovery.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void sendNotification(Notification notification) {
        System.out.println("Sending notification ... " + notification.toString());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notificationRepository.save(notification);

        System.out.println("Notification sent");
    }

    @EventListener
    public void onNotificationEvent(NotificationCreatedEvent event) {
		Notification notification = new Notification();
		notification.setEmail(event.getEmail());
		notification.setRecipient(event.getRecipient());
		notification.setTitle(event.getTitle());
		notification.setText(event.getText());

        sendNotification(notification);
    }

}
