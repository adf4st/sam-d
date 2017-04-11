package net.alexfabian.samd.controller;

import net.alexfabian.samd.dao.RoleRepository;
import net.alexfabian.samd.dao.UserRepository;
import net.alexfabian.samd.model.Role;
import net.alexfabian.samd.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by alexfabian on 3/27/17.
 */
@RestController
public class RegisterEndpoint {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @RequestMapping(value="/register", method= RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody
    ResponseEntity register(@RequestBody User user) throws IOException, ServletException {
        if(user.getPassword() == null || user.getUsername() == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Role userRole = roleRepository.findByName("USER");
        user.getRoles().add(userRole); //defaults role to user role
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.insert(user);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
