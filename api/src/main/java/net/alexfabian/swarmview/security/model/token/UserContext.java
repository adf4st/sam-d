package net.alexfabian.swarmview.security.model.token;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by alexfabian on 2/20/17.
 */
public class UserContext {
    private final String username;
    private final String firstName;
    private final String lastName;
    private final List<GrantedAuthority> authorities;

    public UserContext(String username, String firstName, String lastName, List<GrantedAuthority> authorities) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorities = authorities;
    }

    public static UserContext create(String username, String firstName, String lastName, List<GrantedAuthority> authorities) {
        if (StringUtils.isEmpty(username)) throw new IllegalArgumentException("Username is blank: " + username);
        return new UserContext(username, firstName, lastName, authorities);
    }


    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
