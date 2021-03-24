package server.adore_server.service.client;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.adore_server.model.client.*;
import server.adore_server.repository.client.*;
import server.adore_server.response.ClientCardResponse;

@Service
public class ClientCardService {

    @Autowired
    ClientCardRepository clientCardRepository;

    @Autowired
    ClientShoppingRepository clientShoppingRepository;

    @Autowired
    SizeGroupRepository sizeGroupRepository;

    @Autowired
    ClientTypeRepository clientTypeRepository;

    @Autowired
    TypeOfCardRepository typeOfCardRepository;

    public Object add(JSONObject jsonObject) {
        try {
            int approvalEmail = jsonObject.getInt("approvalEmail");
            int approvalSms = jsonObject.getInt("approvalSms");
            String typeCT = jsonObject.getString("clientType");
            String typeCard = jsonObject.getString("cardType");
            String comment = jsonObject.getString("comment");
            String creationDate = jsonObject.getString("creationDate");
            String email = jsonObject.getString("email");
            String name = jsonObject.getString("name");
            String surname = jsonObject.getString("surname");
            String size = jsonObject.getString("size");
            int phoneNr = jsonObject.getInt("phoneNr");
            int cardNr = jsonObject.getInt("cardNr");
            long recommendingCardNr = jsonObject.getLong("recommending_card_nr");
            ClientCard clientCard = new ClientCard();
            clientCard.setApprovalEmail(approvalEmail);
            clientCard.setApprovalSms(approvalSms);
            clientCard.setCreationDate(creationDate);
            clientCard.setComment(comment);
            clientCard.setEmail(email);
            //clientCard.setNameSurname(nameSurname);
            clientCard.setName(name);
            clientCard.setSurname(surname);
            clientCard.setRecommendingCardNr(recommendingCardNr);


            System.out.println(clientCardRepository.countByCardNr(cardNr));
            if (clientCardRepository.countByCardNr(cardNr) > 0)
                return "ten numer karty jest juz zajety";
            else
                clientCard.setCardNr(cardNr);
            if (clientCardRepository.countByPhoneNr(phoneNr) > 0)
                return "ten numer telefonu jest juz zajety";
            else
                clientCard.setPhoneNr(phoneNr);

            SizeGroup sizeGroup;
            try {
                sizeGroup = sizeGroupRepository.findBySize(size).orElseThrow();
            } catch (Exception e) {
                return "nie ma takiego rozmiaru";
            }
            clientCard.setSizeGroup(sizeGroup);

            ClientType clientType;
            try {
                clientType = clientTypeRepository.findByClientType(typeCT).orElseThrow();
            } catch (Exception e) {
                return "nie ma takiego typu klienta";
            }
            clientCard.setClientType(clientType);

            TypeOfCard typeOfCard;
            try {
                typeOfCard = typeOfCardRepository.findByCardType(typeCard);
            } catch (Exception e) {
                return "nie ma takiego typu karty";
            }

            clientCard.setTypeOfCard(typeOfCard);

            return clientCardRepository.save(clientCard);

        } catch (DataIntegrityViolationException e) {
            return "ten email jest juz zajety";
        }
    }

    @Transactional
    @Modifying
    public int delete(long id) {
        return clientCardRepository.delete(id);
    }


    public ClientCardResponse getInfo(JSONObject jsonObject) {
        ClientCard client = null;
        double sumPoints;

        if (jsonObject.getInt("phoneNumber") != -1)
            client = clientCardRepository.findByPhoneNr(jsonObject.getInt("phoneNumber"));


        else if (jsonObject.getLong("cardNumber") != -1)
            client = clientCardRepository.findByCardNr(jsonObject.getLong("cardNumber"));

        else {
            try {
                client = clientCardRepository.findByNameAndSurname(jsonObject.getString("name"), jsonObject.getString("surname"));
            } catch (Exception e) {
                System.out.println("hej");
                throw new javax.persistence.NonUniqueResultException("wyszukaj po nr karty lub telefonu");
            }

        }
        try {
            sumPoints = clientShoppingRepository.sum(client.getCardId());
        } catch (Exception e) {
            sumPoints = 0;
        }

        ClientCardResponse cardResponse = new ClientCardResponse(
                client.getCardId(),
                client.getCreationDate(),
                client.getName(),
                client.getSurname(),
                client.getPhoneNr(),
                client.getCardNr(),
                client.getComment(),
                client.getRecommendingCardNr(),
                client.getSizeGroup().getSize(),
                client.getClientType().getClientType(),
                client.getTypeOfCard().getCardType(),
                sumPoints);
        return cardResponse;
    }

    @Transactional
    @Modifying
    public void cardUpdate(long cardId, int phone, long cardNr, String name, String surname, String comment, String clientType, String typeOfCard, String sizeGroup) {
        long clientTypeId = clientTypeRepository.findByClientType(clientType).orElseThrow().getClientTypeId();
        long typeOfCardId = typeOfCardRepository.findByCardType(typeOfCard).getTypeOfCardId();
        long sizeGroupId = sizeGroupRepository.findBySize(sizeGroup).orElseThrow().getSizeGroupId();

        clientCardRepository.cardUpdate(cardId, phone, cardNr, name, surname, comment, clientTypeId, typeOfCardId, sizeGroupId);
    }

    public ClientCard getByPhone(int nr) {
        return clientCardRepository.findByPhoneNr(nr);
    }

    public ClientCard getByCardNr(long nr) {
        return clientCardRepository.findByCardNr(nr);
    }

    public ClientCard getByNameAndSurname(String name, String surname) {
        return clientCardRepository.findByNameAndSurname(name, surname);
    }

    public int referralsSum(long card_nr){
        return clientCardRepository.referralsSum(card_nr);
    }


}
