package eventlistener.repo;

import eventlistener.model.notificationuser.NotificationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationUserRepo extends JpaRepository<NotificationUser, Long> {

    List<NotificationUser> findAllByIdIsIn(List<String> notificationUserIds);
}
