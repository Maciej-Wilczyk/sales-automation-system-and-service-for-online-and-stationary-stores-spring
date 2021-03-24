package server.adore_server.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import server.adore_server.Security.JwtTokenProvider;
import server.adore_server.adore_auth.AdoreAuth;
import server.adore_server.exception.AppException;
import server.adore_server.model.auth.Role;
import server.adore_server.model.auth.RoleName;
import server.adore_server.model.auth.User;
import server.adore_server.repository.auth.RoleRepository;
import server.adore_server.repository.auth.UserRolesRepository;
import server.adore_server.request.*;
import server.adore_server.response.ApiResponse;
import server.adore_server.response.JwtAuthenticationResponse;
import server.adore_server.repository.auth.UserRepository;
import server.adore_server.service.auth.UserRolesService;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRolesService userRolesService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRolesRepository userRolesRepository;


    @Autowired
    AdoreAuth adoreAuth;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, value = "/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        System.out.println(jwt);
        var user_id = userRepository.findByLogin(loginRequest.getUsernameOrEmail()).get().getUserId();
        var role_id = userRolesService.getRole_id(user_id).getRole_id();
        adoreAuth.saveAccessToken();
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt,role_id));
    }


    @Secured("ROLE_ADMIN")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE, value = "/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByLogin(signUpRequest.getLogin())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User(signUpRequest.getNameSurname(), signUpRequest.getLogin(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getLogin()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE, value = "/editUserP")
    public ResponseEntity<?> editUser(@Valid @RequestBody EditUserRequest editUserRequest) {

        User user = new User(editUserRequest.getUserId(),editUserRequest.getNameSurname(), editUserRequest.getLogin(),
                editUserRequest.getEmail(), editUserRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getLogin()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User edit successfully"));
    }


    @Transactional
    @Modifying
    @Secured("ROLE_ADMIN")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE, value = "/editUser")
    public ResponseEntity<?> editUserP(@Valid @RequestBody EditUserPRequest editUserPRequest) {

        User user = new User(editUserPRequest.getUserId(),editUserPRequest.getNameSurname(), editUserPRequest.getLogin(),
                editUserPRequest.getEmail());


        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));


        userRepository.updateUser(editUserPRequest.getEmail(),editUserPRequest.getLogin(),editUserPRequest.getNameSurname(),editUserPRequest.getUserId());
        return  new ResponseEntity(HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @Transactional
    @Modifying
    @PostMapping(consumes = MediaType.ALL_VALUE,
            value = "/delUser")
    public int deleteByTransactionId(@RequestBody IdRequest idRequest){
        userRolesRepository.deleteRole(idRequest.getId());
        return userRepository.deleteUser(idRequest.getId());
    }

}