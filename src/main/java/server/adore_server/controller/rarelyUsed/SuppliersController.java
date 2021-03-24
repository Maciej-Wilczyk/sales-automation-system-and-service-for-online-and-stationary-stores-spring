package server.adore_server.controller.rarelyUsed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.adore_server.model.clcker.Suppliers;
import server.adore_server.service.rarelyUsed.SuppliersService;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SuppliersController {

    @Autowired
    SuppliersService suppliersService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/addS")
    public ResponseEntity<Suppliers> addD(@Valid @RequestBody Suppliers supplier) {
        return ResponseEntity.ok(suppliersService.add(supplier));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/getS")
    public ResponseEntity<List<Suppliers>> getSList() {
        return ResponseEntity.ok(suppliersService.getSList());
    }
}
