package server.adore_server.controller.tokens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.adore_server.model.tokens.BaseLinkerToken;
import server.adore_server.repository.tokens.BaseLinkerTokenRepository;
import server.adore_server.request.OneStringRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class BaseLinkerTokenController {

    @Autowired
    BaseLinkerTokenRepository baseLinkerTokenRepository;


    @Secured("ROLE_ADMIN")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/baseLinkerTokenRefresh")
    public ResponseEntity changeToken(@Valid @RequestBody OneStringRequest oneStringRequest) {

        try {
            String token = oneStringRequest.getS();
            BaseLinkerToken baselinkerToken = new BaseLinkerToken(1, token);

            return ResponseEntity.ok(baseLinkerTokenRepository.save(baselinkerToken));
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
