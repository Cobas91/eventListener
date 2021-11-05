package eventlistener.service.notificaionuser;

import eventlistener.exception.EventNotFoundException;
import eventlistener.model.event.Event;
import eventlistener.model.notificationuser.NotificationUser;
import eventlistener.model.notificationuser.NotificationUserDTO;
import eventlistener.model.notificationuser.NotificationUserEditDTO;
import eventlistener.repo.EventRepo;
import eventlistener.repo.NotificationUserRepo;
import eventlistener.service.event.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class NotificationUserService {

    NotificationUserRepo notificationUserRepo;

    EventRepo eventRepo;

    NotificationUserMapper notificationUserMapper;

    EventService eventService;

    public NotificationUserService(NotificationUserRepo notificationUserRepo,EventRepo eventRepo, NotificationUserMapper notificationUserMapper, EventService eventService) {
        this.notificationUserRepo = notificationUserRepo;
        this.eventRepo = eventRepo;
        this.notificationUserMapper = notificationUserMapper;
        this.eventService = eventService;
    }

    public List<NotificationUser> getAllUser() {
        return notificationUserRepo.findAll();
    }

    public NotificationUser addUser(NotificationUserDTO userDTO) {
        if(eventService.eventsExist(userDTO.getListenEvents())){
            NotificationUser newUser = notificationUserMapper.mapDTO(userDTO);
            NotificationUser userWithID = notificationUserRepo.save(newUser);
            eventService.addUserToEvents(userDTO.getListenEvents(), userWithID.getId());
            return userWithID;
        }else{
            throw new EventNotFoundException("Can not add User: "+userDTO.getName()+" events are not existing.");
        }
    }


    public NotificationUserEditDTO getSingleUser(String id) {
        Optional<NotificationUser> optionalUser = notificationUserRepo.findById(id);
        if(optionalUser.isPresent()){
            return notificationUserMapper.mapUserToEditUser(optionalUser.get());
        }
        throw new NoSuchElementException("Cant find User with id " +id);
    }

    public NotificationUser editUser(String id, NotificationUserDTO userToEdit) {
        Optional<NotificationUser> optionalUser = notificationUserRepo.findById(id);
        if(optionalUser.isPresent() && optionalUser.get().getId().equals(id)){
            List<Event> newUserEvents = eventService.setNotificationUserToEvents(optionalUser.get().getId(), userToEdit.getListenEvents());
            log.info("Updating User "+userToEdit+". Setting Events: "+newUserEvents);
            return notificationUserRepo.save(notificationUserMapper.mapDTO(userToEdit));
        }
        throw new NoSuchElementException("Can´t edit User with ID "+id);
    }

}
