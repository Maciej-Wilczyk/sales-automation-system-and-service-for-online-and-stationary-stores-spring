package server.adore_server.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import server.adore_server.model.client.ClientType;
import server.adore_server.request.IdRequest;
import server.adore_server.request.OneStringRequest;
import server.adore_server.service.client.ClientTypeService;
import server.adore_server.service.client.SizeGroupService;

@RestController
@RequestMapping("/api/client")
public class ClientTypeController {

    @Autowired
    ClientTypeService clientTypeService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, value = "/addType")
    public ResponseEntity<?> add(@RequestBody ClientType clientType){
        return ResponseEntity.ok(clientTypeService.add(clientType));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/getTypes")
    public ResponseEntity<?> getClientType(){
        return ResponseEntity.ok(clientTypeService.getClientType());
    }

    @Transactional
    @Modifying
    @PostMapping(consumes = MediaType.ALL_VALUE,
            value = "/delType")
    public int delDisc(@RequestBody IdRequest idRequest) {
        return clientTypeService.delete( idRequest.getId());
    }

}
