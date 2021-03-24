package server.adore_server.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import server.adore_server.model.client.SizeGroup;
import server.adore_server.request.IdRequest;
import server.adore_server.request.OneStringRequest;
import server.adore_server.service.client.SizeGroupService;

@RestController
@RequestMapping("/api/client")
public class SizeGroupController {

    @Autowired
    SizeGroupService sizeGroupService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, value = "/addSize")
    public ResponseEntity<?> add(@RequestBody SizeGroup s){
        return ResponseEntity.ok(sizeGroupService.add(s));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/getSizeGroups")
    public ResponseEntity<?> getSizeGroup(){
        return ResponseEntity.ok(sizeGroupService.getSizeGroups());
    }

    @Transactional
    @Modifying
    @PostMapping(consumes = MediaType.ALL_VALUE,
            value = "/delSize")
    public int delDisc(@RequestBody IdRequest idRequest) {
        return sizeGroupService.delete( idRequest.getId());
    }
}
