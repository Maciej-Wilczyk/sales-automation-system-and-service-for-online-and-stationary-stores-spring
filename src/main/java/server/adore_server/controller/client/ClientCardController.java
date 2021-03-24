package server.adore_server.controller.client;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import server.adore_server.model.client.ClientCard;
import server.adore_server.request.IdRequest;
import server.adore_server.service.client.ClientCardService;

@RestController
@RequestMapping("/api/client")
public class ClientCardController {

    @Autowired
    ClientCardService clientCardService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, value = "/addCard")
    public ResponseEntity<?> add(@RequestBody String jsonObject) {
        JSONObject jsonObject1 = new JSONObject(jsonObject);
        //return ResponseEntity.ok(clientCardService.add(jsonObject1));
        return new ResponseEntity<>(clientCardService.add(jsonObject1), HttpStatus.CREATED);
    }

    @Transactional
    @Modifying
    @PostMapping(consumes = MediaType.ALL_VALUE,
            value = "/delCard")
    public int delCard(@RequestBody IdRequest idRequest) {
        return clientCardService.delete(idRequest.getId());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/getInfo")
    public ResponseEntity<?> getInfo(@RequestBody String jsonObject) {
        JSONObject jsonObject1 = new JSONObject(jsonObject);
        try {
            return new ResponseEntity<>(clientCardService.getInfo(jsonObject1), HttpStatus.OK);
        }catch (javax.persistence.NonUniqueResultException e){
            return new ResponseEntity<>("wyszukaj po nr karty lub telefonu", HttpStatus.CONFLICT);
        }catch (java.lang.NullPointerException e){
            return new ResponseEntity<>("nie ma takiego użytkownika", HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>("error serwera", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @Modifying
    @PostMapping(consumes = MediaType.ALL_VALUE,
            value = "/cardUpdate")
    public void cardUpdate(@RequestBody String jsonObject) {
        JSONObject jsonObject1 = new JSONObject(jsonObject);
        clientCardService.cardUpdate(jsonObject1.getLong("card_id"), jsonObject1.getInt("phone_nr"), jsonObject1.getLong("card_nr"),
                jsonObject1.getString("name"), jsonObject1.getString("surname"), jsonObject1.getString("comment"), jsonObject1.getString("client_type"),
                jsonObject1.getString("type_of_card"), jsonObject1.getString("size_group"));
    }
    @PostMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/getByPhone")
    public ResponseEntity<ClientCard> getByPhone(@RequestBody String jsonObject){
        JSONObject jsonObject1 = new JSONObject(jsonObject);
        return ResponseEntity.ok(clientCardService.getByPhone(jsonObject1.getInt("phone_nr")));
    }

    @PostMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/getByCard")
    public ResponseEntity<ClientCard> getByCardNr(@RequestBody String jsonObject){
        JSONObject jsonObject1 = new JSONObject(jsonObject);
        return ResponseEntity.ok(clientCardService.getByCardNr(jsonObject1.getLong("card_nr")));
    }

    @PostMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/getByNameAndSurname")
    public ResponseEntity<ClientCard> getByNameAndSurname(@RequestBody String jsonObject){
        JSONObject jsonObject1 = new JSONObject(jsonObject);
        return ResponseEntity.ok(clientCardService.getByNameAndSurname(jsonObject1.getString("name"),jsonObject1.getString("surname")));
    }
    @PostMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/referralsSum")
    public ResponseEntity<Integer> getByNameAndSurname(@RequestBody IdRequest id){
        return ResponseEntity.ok(clientCardService.referralsSum(id.getId()));
    }

}
