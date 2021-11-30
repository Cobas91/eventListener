/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

package eventlistener.security.model.api.office;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccessTokenRequestDTO {
    String client_id;
    String scope;
    String code;
    String redirect_uri;
    String client_secret;
}
