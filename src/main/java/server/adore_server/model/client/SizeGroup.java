package server.adore_server.model.client;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "size_group")
public class SizeGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="size_group_id")
    private long sizeGroupId;

    @Column(name="size_")
    private String size;

    @OneToMany(mappedBy="sizeGroup")
    private Set<ClientCard> clientCards;

    public Set<ClientCard> getClientCards() {
        return clientCards;
    }

    public void setClientCards(Set<ClientCard> clientCards) {
        this.clientCards = clientCards;
    }

    public long getSizeGroupId() {
        return sizeGroupId;
    }

    public void setSizeGroupId(long sizeGroupId) {
        this.sizeGroupId = sizeGroupId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


}
