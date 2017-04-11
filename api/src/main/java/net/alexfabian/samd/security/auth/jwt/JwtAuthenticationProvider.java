package net.alexfabian.samd.security.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import net.alexfabian.samd.security.auth.jwt.extractor.TokenExtractor;
import net.alexfabian.samd.security.config.JwtSettings;
import net.alexfabian.samd.security.auth.JwtAuthenticationToken;
import net.alexfabian.samd.security.config.WebSecurityConfig;
import net.alexfabian.samd.security.model.token.RawAccessJwtToken;
import net.alexfabian.samd.security.model.token.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alexfabian on 2/20/17.
 */
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtSettings jwtSettings;

    @Autowired
    public JwtAuthenticationProvider(JwtSettings jwtSettings) {
        this.jwtSettings = jwtSettings;
    }

    @Autowired
    private TokenExtractor tokenExtractor;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();

        UserContext userContext = getUserContext(rawAccessToken);

        return new JwtAuthenticationToken(userContext, userContext.getAuthorities());
    }

    public UserContext getUserContext(RawAccessJwtToken rawAccessJwtToken){
        Jws<Claims> jwsClaims = rawAccessJwtToken.parseClaims(jwtSettings.getTokenSigningKey());
        String subject = jwsClaims.getBody().getSubject();
        String firstName = jwsClaims.getBody().get("net.alexfabian.firstName", String.class);
        String lastName = jwsClaims.getBody().get("net.alexfabian.lastName", String.class);
        List<String> scopes = jwsClaims.getBody().get("scopes", List.class);
        List<GrantedAuthority> authorities = scopes.stream()
                .map(authority -> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toList());

        return UserContext.create(subject, firstName, lastName, authorities);
    }

    public UserContext getUserContextFromRequest(HttpServletRequest request){
        String tokenPayload = request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM);
        RawAccessJwtToken rawAccessJwtToken = new RawAccessJwtToken(tokenExtractor.extract(tokenPayload));

        return getUserContext(rawAccessJwtToken);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
