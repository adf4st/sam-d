package net.alexfabian.swarmview;

import net.alexfabian.swarmview.dao.RoleRepository;
import net.alexfabian.swarmview.dao.ToolRepository;
import net.alexfabian.swarmview.dao.UserRepository;
import net.alexfabian.swarmview.model.Role;
import net.alexfabian.swarmview.model.Tool;
import net.alexfabian.swarmview.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by alexfabian on 12/9/16.
 */

@SpringBootApplication
public class Application {

    Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]){
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner appDataInitializer(ToolRepository toolRepository, UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        return (args) -> {

            toolRepository.deleteAll();
            roleRepository.deleteAll();
            userRepository.deleteAll();

            Tool tool1 = new Tool();
            tool1.setName("tool 1");
            tool1.setNotes("some notes about the tool");
            tool1.setPort(80);
            tool1.setUrl("www.google.com");
            tool1.setVersion("1.1.1");

            toolRepository.save(tool1);

            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setName("USER");
            roleRepository.save(userRole);

            User adminUser = new User();
            adminUser.getRoles().add(adminRole);
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("admin"));
            adminUser.setFirstName("Admin");
            adminUser.setLastName("Man");
            userRepository.save(adminUser);

            User userUser = new User();
            userUser.getRoles().add(userRole);
            userUser.setUsername("user");
            userUser.setPassword(passwordEncoder.encode("user"));
            userUser.setFirstName("Jane");
            userUser.setLastName("Doe");
            userRepository.save(userUser);

        };
    }

}