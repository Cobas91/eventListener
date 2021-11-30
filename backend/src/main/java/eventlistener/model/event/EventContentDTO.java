/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

package eventlistener.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class EventContentDTO {
    @Id
    @JsonProperty("datapackageid")
    private String id;

    @JsonProperty("entitytoken")
    private String customerRef;

    @JsonProperty("environmentname")
    private String environmentName;

    private String project;
    @JsonProperty("timestamp_long")
    private long timestampLong;

    @JsonProperty("comment_user")
    private String commentUser;

    @JsonProperty("comment_extract")
    private String commentExtract;


    private String content;

}
