package eventlistener.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("events")
public class EventContentDTO {
    @Id
    @JsonProperty("datapackageid")
    private String id;

    @JsonProperty("entitytoken")
    private String customerRef;

    @JsonProperty("environmentname")
    private String environmentName;

    private String project;

    private long timestamp_long;

    private Date timestamp_ts;

    @JsonProperty("comment_user")
    private String commentUser;

    @JsonProperty("comment_extract")
    private String commentExtract;

    @JsonProperty("execution_started")
    private Date executionStarted;

    @JsonProperty("execution_finished")
    private Date executionFinished;

    private String content;

}
