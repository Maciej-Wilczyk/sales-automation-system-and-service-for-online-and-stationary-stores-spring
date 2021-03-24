package server.adore_server.controller.rarelyUsed;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.adore_server.model.clcker.Delivered;
import server.adore_server.request.OneStringRequest;
import server.adore_server.service.rarelyUsed.DeliveredService;
import server.adore_server.service.StockTableService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DeliveredController {

    @Autowired
    DeliveredService deliveredService;

    @Autowired
    StockTableService stockTableService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/addD")
    public ResponseEntity<List<Delivered>> addD(@Valid @RequestBody List<Delivered> list) {
        return ResponseEntity.ok(deliveredService.add(list));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/addDChange")
    public ResponseEntity<?> addDChange(@RequestBody List<Delivered> list) throws Exception {
        JSONArray jsonArray = new JSONArray(list);

        List<Long> conflict = stockTableService.increaseStock(jsonArray);
        if (conflict.isEmpty() == true) {
            return ResponseEntity.ok(deliveredService.add(list));
        } else {
            for (int i = 0; i < conflict.size(); i++) {

                for(Delivered j: list){
                    if(j.getStock_id() == conflict.get(i))
                        j.setReturned(-1);
                }

            }
            return new ResponseEntity<>(deliveredService.add(list), HttpStatus.CONFLICT);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/changeEan")
    public ResponseEntity<?> changeEan(@RequestBody String s) {
        JSONObject jsonObject = new JSONObject(s);
        boolean b = stockTableService.changeEan(jsonObject);
        if (b == true)
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/getDelId")
    public long getMaxDeliveredId() {
        try {
            return deliveredService.getMaxDelId();
        } catch (Exception e) {
            return 0;
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/getByInvoice")
    public ResponseEntity<Object[][]> getByInvoiceCounted(@RequestBody OneStringRequest s) {
        return ResponseEntity.ok(deliveredService.getByInvoiceCounted(s.getS()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/getBy3")
    public ResponseEntity<List<Delivered>> getByInvoice3(@RequestBody String s) {
        JSONObject jsonObject = new JSONObject(s);
        String invoice = jsonObject.getString("invoice");
        long stock_id = jsonObject.getLong("stock_id");
        double purchase_price = jsonObject.getDouble("purchase_price");
        return ResponseEntity.ok(deliveredService.getBy3(invoice, stock_id, purchase_price));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/addC")
    public ResponseEntity<?> addC(@RequestBody List<Delivered> list) {
        boolean b = deliveredService.addC(list);
        if (b == true)
            return new ResponseEntity(HttpStatus.CREATED);
        else
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}