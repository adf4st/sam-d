package net.alexfabian.swarmview.security.endpoint;

import io.jsonwebtoken.lang.Assert;
import net.alexfabian.swarmview.dao.UserRepository;
import net.alexfabian.swarmview.model.User;
import net.alexfabian.swarmview.security.config.WebSecurityConfig;
import net.alexfabian.swarmview.security.auth.jwt.extractor.TokenExtractor;
import net.alexfabian.swarmview.security.config.JwtSettings;
import net.alexfabian.swarmview.security.exception.InvalidJwtToken;
import net.alexfabian.swarmview.security.model.RefreshToken;
import net.alexfabian.swarmview.security.model.token.JwtToken;
import net.alexfabian.swarmview.security.model.token.JwtTokenFactory;
import net.alexfabian.swarmview.security.model.token.RawAccessJwtToken;
import net.alexfabian.swarmview.security.model.token.UserContext;
import net.alexfabian.swarmview.security.auth.jwt.verifier.TokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alexfabian on 2/20/17.
 */

@RestController
public class RefreshTokenEndpoint {
    @Autowired
    private JwtTokenFactory tokenFactory;
    @Autowired private JwtSettings jwtSettings;
    @Autowired private UserRepository userRepository;
    @Autowired private TokenVerifier tokenVerifier;
    @Autowired @Qualifier("jwtHeaderTokenExtractor") private TokenExtractor tokenExtractor;

    @RequestMapping(value="/auth/token", method= RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody
    JwtToken refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM));

        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, jwtSettings.getTokenSigningKey()).orElseThrow(() -> new InvalidJwtToken());

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtToken();
        }

        String subject = refreshToken.getSubject();
        User user = getUser(subject);

        if (user.getRoles().isEmpty()) throw new InsufficientAuthenticationException("User has no roles assigned");
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());

        UserContext userContext = UserContext.create(user.getUsername(), user.getFirstName(), user.getLastName(), authorities);

        return tokenFactory.createAccessJwtToken(userContext);
    }

    private User getUser(String username){
        Assert.notNull(username);
        User user = userRepository.findByUsername(username);
        if(user == null) throw new UsernameNotFoundException("User not found: " + username);

        return user;
    }



}
