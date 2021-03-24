package server.adore_server.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.adore_server.request.IdRequest;
import server.adore_server.response.ClientShoppingHistoryProductsResponse2;
import server.adore_server.response.ClientShoppingHistoryResponse;
import server.adore_server.service.client.ClientShoppingService;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientShoppingController {

    @Autowired
    ClientShoppingService clientShoppingService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, value = "/history")
    public ResponseEntity<List<ClientShoppingHistoryResponse>> getHistory(@RequestBody IdRequest id) {
        return new ResponseEntity<>(clientShoppingService.getHistory(id.getId()), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, value = "/historyProducts")
    public ResponseEntity<List<ClientShoppingHistoryProductsResponse2>> getHistoryProducts(@RequestBody IdRequest id) {
        return new ResponseEntity<>(clientShoppingService.getHistoryProducts(id.getId()), HttpStatus.OK);
    }
}
