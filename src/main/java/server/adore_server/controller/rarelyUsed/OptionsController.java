package server.adore_server.controller.rarelyUsed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import server.adore_server.service.rarelyUsed.OptionsService;

@RestController
@RequestMapping("/api")

public class OptionsController {

    @Autowired
    OptionsService optionsService;

    @Secured("ROLE_ADMIN")
    @Transactional
    @Modifying
    @PostMapping(consumes = MediaType.ALL_VALUE,
            value = "/updateOptions")
    public void updateOptions() throws Exception {
        optionsService.updateOptions();
    }


}
