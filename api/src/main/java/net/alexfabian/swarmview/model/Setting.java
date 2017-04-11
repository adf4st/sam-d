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
public class Setting {

    @Id
    private String id;

    private String swarmManagerUrl;

    private Boolean active;

    public String getSwarmManagerUrl() {
        return swarmManagerUrl;
    }

    public void setSwarmManagerUrl(String swarmManagerUrl) {
        this.swarmManagerUrl = swarmManagerUrl;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
