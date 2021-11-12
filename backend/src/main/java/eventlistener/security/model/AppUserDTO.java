package eventlistener.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class AppUserDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public AppUserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private String username;
    private String password;
}



