package server.adore_server.model.client;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "type_of_card")
public class TypeOfCard {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="type_of_card_id")
    private long typeOfCardId;

    @Column(name="card_type")
    private String CardType;

    @Column(name="discount")
    private double discount;

    @OneToMany(mappedBy="clientType")
    private Set<ClientCard> clientCards;

    public TypeOfCard() {
        discount = 0;
    }

    public long getTypeOfCardId() {
        return typeOfCardId;
    }

    public void setTypeOfCardId(long typeOfCardId) {
        this.typeOfCardId = typeOfCardId;
    }

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        CardType = cardType;
    }

    public Set<ClientCard> getClientCards() {
        return clientCards;
    }

    public void setClientCards(Set<ClientCard> clientCards) {
        this.clientCards = clientCards;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
