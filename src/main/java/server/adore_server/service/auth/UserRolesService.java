package server.adore_server.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.adore_server.model.auth.UserRoles;
import server.adore_server.repository.auth.UserRolesRepository;

@Service
public class UserRolesService {
    @Autowired
    UserRolesRepository userRolesRepository;
    public UserRoles getRole_id(long user_id){
        return userRolesRepository.findById(user_id).orElseThrow();
    }

}
