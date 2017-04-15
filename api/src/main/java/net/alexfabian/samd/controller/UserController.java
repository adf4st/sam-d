package net.alexfabian.samd.controller;

import net.alexfabian.samd.dao.UserRepository;
import net.alexfabian.samd.model.User;
import net.alexfabian.samd.security.auth.jwt.JwtAuthenticationProvider;
import net.alexfabian.samd.security.model.token.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by alexfabian on 4/1/17.
 */

@Controller
public class UserController {

    @Autowired
    JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @RequestMapping(path = "/users", method = RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity getAllUsers(HttpServletRequest request, HttpServletResponse response){
        UserContext userContext = jwtAuthenticationProvider.getUserContextFromRequest(request);
        if(doesUserHaveAdminRole(userContext)){
            List<User> users = userRepository.findAll();
            for(User user : users){
                user.setPassword("xxxx");
            }
            return new ResponseEntity(users, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    @RequestMapping(path = "/users/currentUser", method = RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity getCurrentUser(HttpServletRequest request, HttpServletResponse response){
        UserContext userContext = jwtAuthenticationProvider.getUserContextFromRequest(request);

        User user = userRepository.findByUsername(userContext.getUsername());
        user.setPassword(null);

        return new ResponseEntity(user, HttpStatus.OK);
    }

    @RequestMapping(path = "/users", method = RequestMethod.PUT, produces={ MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity updateCurrentUser(HttpServletRequest request, HttpServletResponse response, @RequestBody User updatedUser){
        UserContext userContext = jwtAuthenticationProvider.getUserContextFromRequest(request);
        User currentUser = userRepository.findByUsername(userContext.getUsername());
        updatedUser.setId(currentUser.getId());  // only let the user modify the user record that corresponds with their token

        String updatedPassword = updatedUser.getPassword();
        if(updatedPassword != null && !updatedPassword.equals("")){
            updatedUser.setPassword(passwordEncoder.encode(updatedPassword));
        } else {
            updatedUser.setPassword(currentUser.getPassword());
        }

        User savedUser = userRepository.save(updatedUser);
        savedUser.setPassword("");
        return new ResponseEntity(savedUser, HttpStatus.OK);
    }

    private boolean doesUserHaveAdminRole(UserContext userContext) {
        for(GrantedAuthority grantedAuthority : userContext.getAuthorities()){
            if(grantedAuthority.getAuthority().equals("ADMIN")){
                return true;
            }
        }

        return false;
    }

}