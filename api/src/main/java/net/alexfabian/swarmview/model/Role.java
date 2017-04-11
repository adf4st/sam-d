package net.alexfabian.swarmview.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Set;

/**
 * Created by alexfabian on 2/19/17.
 */

@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    private String id;

    private String name;
    private Set<User> users;

}
