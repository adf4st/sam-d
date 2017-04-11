package net.alexfabian.swarmview.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 * Created by alexfabian on 6/13/16.
 */

@Getter
@Setter
@NoArgsConstructor
public class Tool {

    @Id
    private String id;

    private String  name;
    private String  url;
    private int     port;
    private String  version;
    private String  notes;

}