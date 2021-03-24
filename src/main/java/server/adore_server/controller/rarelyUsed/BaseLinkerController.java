package server.adore_server.controller.rarelyUsed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.adore_server.model.SaleRecord;
import server.adore_server.model.auth.Status;
import server.adore_server.request.IdRequest;
import server.adore_server.request.GetOrdersRequest;
import server.adore_server.service.rarelyUsed.BaseLinkerService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BaseLinkerController {

    @Autowired
    BaseLinkerService baseLinkerService;
    @GetMapping(value = "/getStatusList",consumes = MediaType.ALL_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getStatusList() {
        String method = "getOrderStatusList";
        String parameters = "";
        try{
            return ResponseEntity.ok(baseLinkerService.getStatusList(baseLinkerService.getStatusListConnector(method,parameters)));

        }
        catch (Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/getOrders",consumes = MediaType.ALL_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Status> getOrders(@Valid @RequestBody GetOrdersRequest getOrdersRequest) {
        String method = "getOrders";

        String parameters = "&parameters={\"status_id\":"+getOrdersRequest.getId()+",\"date_from\":"+getOrdersRequest.getDate()+"}";
        System.out.println(parameters);


        return baseLinkerService.getOrders(baseLinkerService.getStatusListConnector(method,parameters));
    }

    @PostMapping(value = "/getOrderToSaleRecord",consumes = MediaType.ALL_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOrders(@Valid @RequestBody IdRequest id) {
        String method = "getOrders";

        String parameters = "&parameters={\"order_id\":"+id.getId()+"}";

            return ResponseEntity.ok(baseLinkerService.getOrderToSaleRecord(baseLinkerService.getStatusListConnector(method,parameters),id.getId()));

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, value = "/addB")
    public List<SaleRecord> addSaleRecordBaseLinker(@Valid @RequestBody List<SaleRecord> saleRecord) throws Exception {
        return baseLinkerService.addSaleRecordBaseLinker(saleRecord);
    }
}
