package server.adore_server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import server.adore_server.adore_auth.AdoreToken;


@SpringBootApplication
public class AdoreServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdoreServerApplication.class, args);

    }


}
