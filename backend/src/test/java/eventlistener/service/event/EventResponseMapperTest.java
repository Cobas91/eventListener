package eventlistener.service.event;

import eventlistener.model.Action;
import eventlistener.model.event.Event;
import eventlistener.model.event.ResponseEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class EventResponseMapperTest {

    EventResponseMapper eventResponseMapper = new EventResponseMapper();

    @Test
    @DisplayName("Return a list of Responseevents. Mapping Events to Response Events")
    void testMapResponseEvents() {
        //GIVEN
        List<ResponseEvent> responseEvents = List.of(
                ResponseEvent.builder()
                        .id("1")
                        .description("Beschreibung")
                        .actions(List.of(Action.MAIL))
                        .name("Test Event")
                        .build(),
                ResponseEvent.builder()
                        .id("2")
                        .description("Beschreibung2")
                        .actions(List.of(Action.MAIL))
                        .name("Test Event 2")
                        .build()
        );
        List<Event> events = List.of(
                Event.builder()
                        .id("1")
                        .description("Beschreibung")
                        .actions(List.of(Action.MAIL))
                        .name("Test Event")
                        .notificationUser(List.of("1", "2"))
                        .build(),
                Event.builder()
                        .id("2")
                        .description("Beschreibung2")
                        .actions(List.of(Action.MAIL))
                        .name("Test Event 2")
                        .notificationUser(List.of("1", "2"))
                        .build()
        );
        //WHEN
        List<ResponseEvent> actual = eventResponseMapper.mapResponseEvents(events);
        //THEN
        assertThat(actual, is(responseEvents));
    }
}