package server.adore_server.model.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import server.adore_server.model.SaleRecord;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "client_card", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class ClientCard implements Serializable {


    public ClientCard(long cardId, String creationDate, String email, int approvalSms, int approvalEmail, String name, String surname, int phoneNr, long cardNr, String comment, long recommendingCardNr, SizeGroup sizeGroup, ClientType clientType, TypeOfCard typeOfCard) {
        this.cardId = cardId;
        this.creationDate = creationDate;
        this.email = email;
        this.approvalSms = approvalSms;
        this.approvalEmail = approvalEmail;
        this.name = name;
        this.surname = surname;
        this.phoneNr = phoneNr;
        this.cardNr = cardNr;
        this.comment = comment;
        this.recommendingCardNr = recommendingCardNr;
        this.sizeGroup = sizeGroup;
        this.clientType = clientType;
        this.typeOfCard = typeOfCard;
    }

    public ClientCard() {
        email = "";
        surname = "";
        name = "";
        phoneNr = -1;
        recommendingCardNr = -1;


    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private long cardId;

    @Column(name = "creation_date")
    private String creationDate;

    @Column(name = "email")
    private String email;

    @Column(name = "approval_sms")
    private int approvalSms;

    @Column(name = "approval_email")
    private int approvalEmail;

//    @Column(name="name_surname")
//    private String nameSurname;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "phone_nr")
    private int phoneNr;

    @Column(name = "card_nr")
    private long cardNr;

    @Column(name = "comment")
    private String comment;

    @Column(name = "recommending_card_nr")
    private long recommendingCardNr;


    //    @Column(name="size_group")
//    private int sizeGroup;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "size_group_id", referencedColumnName = "size_group_id")
    private SizeGroup sizeGroup;


    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_type_id", referencedColumnName = "client_type_id")
    private ClientType clientType;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_of_card_id", referencedColumnName = "type_of_card_id")
    private TypeOfCard typeOfCard;

//    @OneToMany(mappedBy="clientCard")
//    private List<ClientShopping> clientShoppings = new ArrayList<>();
//
//    public List<ClientShopping> getClientShoppings() {
//        return clientShoppings;
//    }
//
//    public void setClientShoppings(List<ClientShopping> clientShoppings) {
//        this.clientShoppings = clientShoppings;
//    }

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "clientCard")
    private List<ClientShopping> clientShoppings;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "clientCard1")
    private List<SaleRecord> saleRecords;

    public void addClientShopping(ClientShopping clientShopping) {
        clientShoppings.add(clientShopping);
        clientShopping.setClientCard(this);
    }

    public void removeClientShopping(ClientShopping clientShopping) {
        clientShoppings.remove(clientShopping);
        clientShopping.setClientCard(null);
    }

    public void addCSaleRecord(SaleRecord saleRecord) {
        saleRecords.add(saleRecord);
        saleRecord.setClientCard(this);
    }


    public List<ClientShopping> getClientShoppings() {
        return clientShoppings;
    }

    public void setClientShoppings(List<ClientShopping> clientShoppings) {
        this.clientShoppings = clientShoppings;
    }

    public long getRecommendingCardNr() {
        return recommendingCardNr;
    }

    public void setRecommendingCardNr(long recommendingCardNr) {
        this.recommendingCardNr = recommendingCardNr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public SizeGroup getSizeGroup() {
        return sizeGroup;
    }

    public void setSizeGroup(SizeGroup sizeGroup) {
        this.sizeGroup = sizeGroup;
    }

    public long getCardId() {
        return cardId;
    }

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }

    public int getApprovalEmail() {
        return approvalEmail;
    }

    public void setApprovalEmail(int approvalEmail) {
        this.approvalEmail = approvalEmail;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getApprovalSms() {
        return approvalSms;
    }

    public void setApprovalSms(int approvalSms) {
        this.approvalSms = approvalSms;
    }

//    public String getNameSurname() {
//        return nameSurname;
//    }
//
//    public void setNameSurname(String nameSurname) {
//        this.nameSurname = nameSurname;
//    }

//    public int getSizeGroup() {
//        return sizeGroup;
//    }
//
//    public void setSizeGroup(int sizeGroup) {
//        this.sizeGroup = sizeGroup;
//    }


    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getPhoneNr() {
        return phoneNr;
    }

    public void setPhoneNr(int phoneNr) {
        this.phoneNr = phoneNr;
    }

    public long getCardNr() {
        return cardNr;
    }

    public void setCardNr(long cardNr) {
        this.cardNr = cardNr;
    }

    public TypeOfCard getTypeOfCard() {
        return typeOfCard;
    }

    public void setTypeOfCard(TypeOfCard typeOfCard) {
        this.typeOfCard = typeOfCard;
    }
}
