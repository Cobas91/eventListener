package eventlistener.model.notificationuser;

import lombok.*;


import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class NotificationUserDTO {
    private String email;
    private String name;
    private List<Long> listenEvents;
}
