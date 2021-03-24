package server.adore_server.controller.client;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import server.adore_server.request.IdRequest;
import server.adore_server.service.client.PrizeService;

@RestController
@RequestMapping("/api/client")
public class PrizeController {

    @Autowired
    PrizeService prizeService;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, value = "/addPrize")
    public ResponseEntity<?> add(@RequestBody String jsonObject) {
        JSONObject jsonObject1 = new JSONObject(jsonObject);
        return ResponseEntity.ok(prizeService.add(jsonObject1));
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/getPrizes")
    public ResponseEntity<?> getPrizes(){
        return ResponseEntity.ok(prizeService.getPrizes());
    }

    @Transactional
    @Modifying
    @PostMapping(consumes = MediaType.ALL_VALUE,
            value = "/delPrize")
    public int delDisc(@RequestBody IdRequest idRequest) {
        return prizeService.delete( idRequest.getId());
    }
}
