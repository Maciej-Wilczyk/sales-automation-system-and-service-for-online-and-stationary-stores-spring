package server.adore_server.model.tokens;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "token_refresh_data")
public class TokenRefreshData {

    @Id
    private int id;
    @Column
    private String dataToAdore;
    @Column
    private String login;

    public TokenRefreshData() {
    }

    public TokenRefreshData(int id, String dataToAdore, String login) {
        this.id = id;
        this.dataToAdore = dataToAdore;
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataToAdore() {
        return dataToAdore;
    }

    public void setDataToAdore(String dataToAdore) {
        this.dataToAdore = dataToAdore;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
