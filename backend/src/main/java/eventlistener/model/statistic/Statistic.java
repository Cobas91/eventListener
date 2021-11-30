/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

package eventlistener.model.statistic;

import eventlistener.model.Action;
import eventlistener.model.event.Event;
import eventlistener.model.event.EventContentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Statistic {
    private Event triggeredEvent;
    private Action action;
    private EventContentDTO content;
    private LocalDateTime triggeredAt;
    private LocalDateTime finishedAt;
}
