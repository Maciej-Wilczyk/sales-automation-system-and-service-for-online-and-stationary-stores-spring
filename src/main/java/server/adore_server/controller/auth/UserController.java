package server.adore_server.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import server.adore_server.model.auth.User;
import server.adore_server.service.auth.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    public UserController() {
    }

    @Autowired
    UserService usersService;

    @GetMapping("/getUsers")
    public List<User> getUsers() {
        return usersService.getUsers();
    }

}
