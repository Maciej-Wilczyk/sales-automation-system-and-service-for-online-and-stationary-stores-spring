package server.adore_server.controller.rarelyUsed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.adore_server.service.rarelyUsed.OptionValuesService;

@RestController
@RequestMapping("/api")
public class OptionValuesController {

    @Autowired
    OptionValuesService optionValuesService;
    @Secured("ROLE_ADMIN")
    @Transactional
    @Modifying
    @PostMapping(consumes = MediaType.ALL_VALUE,
            value = "/updateOptionValues")
    public void updateOptionValues() throws Exception {
        optionValuesService.updateOptionValues();
    }
}
