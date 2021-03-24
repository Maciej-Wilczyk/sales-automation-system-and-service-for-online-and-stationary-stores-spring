package server.adore_server.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.adore_server.adore_auth.AdoreAuth;
import server.adore_server.adore_auth.AdoreToken;

@RestController
@RequestMapping("/api")
public class AdoreAuthController {
    @Autowired
    AdoreAuth adoreAuth;
    @GetMapping("/token")
    public String getAT(){
        System.out.println(AdoreToken.access_token);
        return AdoreToken.access_token;
    }

}
