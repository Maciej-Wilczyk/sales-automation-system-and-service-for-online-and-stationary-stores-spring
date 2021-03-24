package server.adore_server.model.client;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "client_type")
public class ClientType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="client_type_id")
    private long clientTypeId;

    @Column(name="client_type")
    private String clientType;

    @OneToMany(mappedBy="clientType")
    private Set<ClientCard> clientCards;

    public long getClientTypeId() {
        return clientTypeId;
    }

    public void setClientTypeId(long clientTypeId) {
        this.clientTypeId = clientTypeId;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public Set<ClientCard> getClientCards() {
        return clientCards;
    }

    public void setClientCards(Set<ClientCard> clientCards) {
        this.clientCards = clientCards;
    }
}
