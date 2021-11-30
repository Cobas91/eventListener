/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

package eventlistener.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class ApiError {
    private String text;
    private String exMessage;
    private LocalDateTime timestamp;

    public ApiError(String text, String exMessage) {
        this.text = text;
        this.exMessage = exMessage;
        this.timestamp = LocalDateTime.now();
    }
}
