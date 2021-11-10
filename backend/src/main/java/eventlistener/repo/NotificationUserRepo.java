package eventlistener.repo;

import eventlistener.model.notificationuser.NotificationUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationUserRepo extends PagingAndSortingRepository<NotificationUser, Long> {
    @Override
    List<NotificationUser> findAll();
    List<NotificationUser> findAllByIdIsIn(List<String> notificationUserIds);
}
