package server.adore_server.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import server.adore_server.model.client.ClientType;
import server.adore_server.model.client.SizeGroup;
import server.adore_server.model.client.TypeOfCard;

import javax.persistence.*;

public class ClientCardResponse {

    public ClientCardResponse(long cardId, String creationDate, String name, String surname, int phoneNr, long cardNr, String comment, long recommendingCardNr, String sizeGroup, String clientType, String typeOfCard, double points) {
        this.cardId = cardId;
        this.creationDate = creationDate;
        this.name = name;
        this.surname = surname;
        this.phoneNr = phoneNr;
        this.cardNr = cardNr;
        this.comment = comment;
        this.recommendingCardNr = recommendingCardNr;
        this.sizeGroup = sizeGroup;
        this.clientType = clientType;
        this.typeOfCard = typeOfCard;
        this.points = points;
    }

    private long cardId;

    private String creationDate;

    private String name;

    private String surname;

    private int phoneNr;

    private long cardNr;

    private String comment;

    private long recommendingCardNr;

    private String sizeGroup;

    private String clientType;

    private String typeOfCard;

    private double points;

    public long getCardId() {
        return cardId;
    }

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getRecommendingCardNr() {
        return recommendingCardNr;
    }

    public void setRecommendingCardNr(long recommendingCardNr) {
        this.recommendingCardNr = recommendingCardNr;
    }

    public String getSizeGroup() {
        return sizeGroup;
    }

    public void setSizeGroup(String sizeGroup) {
        this.sizeGroup = sizeGroup;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getTypeOfCard() {
        return typeOfCard;
    }

    public void setTypeOfCard(String typeOfCard) {
        this.typeOfCard = typeOfCard;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }
}
