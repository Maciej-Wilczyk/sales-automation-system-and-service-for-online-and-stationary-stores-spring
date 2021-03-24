package server.adore_server.controller.tokens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import server.adore_server.model.tokens.TokenRefreshData;
import server.adore_server.repository.tokens.TokenRefreshDataRepository;
import server.adore_server.request.TokenRefreshDataRequest;
import server.adore_server.service.tokens.TokenRefreshDataService;

import javax.validation.Valid;
import java.util.Base64;

@RestController
@RequestMapping("/api")
public class TokenRefreshDataController {

    @Autowired
    TokenRefreshDataRepository tokenRefreshDataRepository;
    @Autowired
    TokenRefreshDataService tokenRefreshDataService;

    @Secured("ROLE_ADMIN")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/tokenRefreshData")
    public ResponseEntity changeData(@Valid @RequestBody TokenRefreshDataRequest tokenRefreshDataRequest) {
        TokenRefreshData tokenRefreshData;
        try {
            String login = tokenRefreshDataRequest.getLogin();
            String password = tokenRefreshDataRequest.getPassword();
            String dataToAdore = login + ":" + password;
            String encodedDataToAdore = Base64.getEncoder().encodeToString(dataToAdore.getBytes());

            tokenRefreshData = new TokenRefreshData(1, encodedDataToAdore, login);

            return ResponseEntity.ok(tokenRefreshDataRepository.save(tokenRefreshData));
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    @Secured("ROLE_ADMIN")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/getTokenLogin")
    public String getLogin() {
      return tokenRefreshDataService.getLogin();
    }
}
