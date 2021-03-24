package server.adore_server.model.tokens;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "base_linker_token")
public class BaseLinkerToken {
    @Id
    private long id;

    @Column
    String token;


    public BaseLinkerToken() {
    }

    public BaseLinkerToken(long id, String token) {
        this.id = id;
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
