package net.alexfabian.samd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by alexfabian on 2/19/17.
 */


@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    private String id;

    private String username;
    private String firstName;
    private String lastName;
    private Set<Role> roles = new HashSet<>();

//    @JsonIgnore //excludes password field from being included in JSON response
    private String password;

    @JsonIgnore //excludes password field from being included in JSON response
    private String passwordConfirm;
}
