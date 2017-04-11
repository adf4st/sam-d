package net.alexfabian.samd.security.exception;

import org.springframework.security.authentication.AuthenticationServiceException;

/**
 * Created by alexfabian on 2/20/17.
 */
public class AuthMethodNotSupportedException extends AuthenticationServiceException {
    private static final long serialVersionUID = 3705043083010304496L;

    public AuthMethodNotSupportedException(String msg) {
        super(msg);
    }
}
