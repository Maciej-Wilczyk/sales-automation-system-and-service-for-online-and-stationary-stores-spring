package server.adore_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import server.adore_server.model.Discounts;
import server.adore_server.request.DiscountsRequest;
import server.adore_server.request.IdRequest;
import server.adore_server.response.DiscountsResponse;
import server.adore_server.service.DiscountsService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DiscountsController {

    @Autowired
    DiscountsService discountsService;


    @GetMapping(value = "/getDiscName")
    public ResponseEntity<List<String>> getDiscountsName() {
        return ResponseEntity.ok(discountsService.getDiscName());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, value = "/getGiveDisc")
    public ResponseEntity<DiscountsResponse> getGiveDisc(@Valid @RequestBody DiscountsRequest s) {
        return ResponseEntity.ok(discountsService.getDiscInfo(s));
    }

    @GetMapping(value = "/getDiscList")
    public ResponseEntity<List<Discounts>> getDiscList() {
        return ResponseEntity.ok(discountsService.getDiscList());
    }

    @PostMapping(value = "/addDisc")
    public ResponseEntity<Discounts> addDisc(@RequestBody Discounts discounts){
        return ResponseEntity.ok(discountsService.addDisc(discounts));
    }


    @Transactional
    @Modifying
    @PostMapping(consumes = MediaType.ALL_VALUE,
            value = "/delDisc")
    public int delDisc(@RequestBody IdRequest idRequest) {
        return discountsService.delDisc( idRequest.getId());
    }

}
