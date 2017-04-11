package net.alexfabian.swarmview.security.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by alexfabian on 2/20/17.
 */
public enum ErrorCode {

    GLOBAL(2),

    AUTHENTICATION(10), JWT_TOKEN_EXPIRED(11);

    private int errorCode;

    private ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }
}
