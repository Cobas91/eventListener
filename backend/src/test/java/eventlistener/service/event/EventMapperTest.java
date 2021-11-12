package eventlistener.service.event;

import eventlistener.model.Action;
import eventlistener.model.event.Event;
import eventlistener.model.event.ResponseEventDTO;
import eventlistener.model.notificationuser.NotificationUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

class EventMapperTest {

    EventMapper eventMapper = new EventMapper();

    @Test
    @DisplayName("Return a list of Responseevents. Mapping Events to Response Events")
    void testMapResponseEvents() {
        //GIVEN
        List<ResponseEventDTO> responseEventDTOS = List.of(
                ResponseEventDTO.builder()
                        .id(1)
                        .description("Beschreibung")
                        .actions(List.of(Action.MAIL))
                        .name("Test Event")
                        .build(),
                ResponseEventDTO.builder()
                        .id(2)
                        .description("Beschreibung2")
                        .actions(List.of(Action.MAIL))
                        .name("Test Event 2")
                        .build()
        );
        List<Event> events = List.of(
                Event.builder()
                        .id(1)
                        .description("Beschreibung")
                        .actions(List.of(Action.MAIL))
                        .name("Test Event")
                        .notificationUser(List.of(NotificationUser.builder().build(), NotificationUser.builder().build()))
                        .build(),
                Event.builder()
                        .id(2)
                        .description("Beschreibung2")
                        .actions(List.of(Action.MAIL))
                        .name("Test Event 2")
                        .notificationUser(List.of(NotificationUser.builder().build(), NotificationUser.builder().build()))
                        .build()
        );
        //WHEN
        List<ResponseEventDTO> actual = eventMapper.mapDtoToEvent(events);
        //THEN
        assertThat(actual, is(responseEventDTOS));
    }
}