package server.adore_server.controller.client;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.adore_server.service.client.MinimumAmountService;

@RestController
@RequestMapping("/api/client")
public class MinimumAmountController {
    @Autowired
    MinimumAmountService minimumAmountService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/getMinimum")
    public ResponseEntity<?> get(){
        return ResponseEntity.ok(minimumAmountService.get());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, value = "/editMinimum")
    public ResponseEntity<?> edit(@RequestBody String jsonObject) {
        JSONObject jsonObject1 = new JSONObject(jsonObject);
        return ResponseEntity.ok(minimumAmountService.edit(jsonObject1.getDouble("amount")));
    }
}
