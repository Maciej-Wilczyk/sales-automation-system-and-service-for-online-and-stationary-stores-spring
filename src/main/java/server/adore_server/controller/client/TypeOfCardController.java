package server.adore_server.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import server.adore_server.model.client.SizeGroup;
import server.adore_server.model.client.TypeOfCard;
import server.adore_server.request.IdRequest;
import server.adore_server.service.client.SizeGroupService;
import server.adore_server.service.client.TypeOfCardService;

@RestController
@RequestMapping("/api/client")
public class TypeOfCardController {

    @Autowired
    TypeOfCardService typeOfCardService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, value = "/addTypeOfCard")
    public ResponseEntity<?> add(@RequestBody TypeOfCard s){
        return ResponseEntity.ok(typeOfCardService.add(s));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/getAllTypeOfCard")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(typeOfCardService.getAll());
    }

    @Transactional
    @Modifying
    @PostMapping(consumes = MediaType.ALL_VALUE,
            value = "/delTypeOfCard")
    public int delDisc(@RequestBody IdRequest idRequest) {
        return typeOfCardService.delete( idRequest.getId());
    }
}
