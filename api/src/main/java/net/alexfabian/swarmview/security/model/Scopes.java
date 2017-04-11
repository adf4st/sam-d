package net.alexfabian.swarmview.security.model;

/**
 * Created by alexfabian on 2/20/17.
 */
public enum Scopes {
    REFRESH_TOKEN;

    public String authority() {
        return "ROLE_" + this.name();
    }
}