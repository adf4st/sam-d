package net.alexfabian.swarmview.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.alexfabian.swarmview.security.RestAuthenticationEntryPoint;
import net.alexfabian.swarmview.security.auth.ajax.AjaxAuthenticationProvider;
import net.alexfabian.swarmview.security.auth.ajax.AjaxLoginProcessingFilter;
import net.alexfabian.swarmview.security.auth.jwt.JwtAuthenticationProvider;
import net.alexfabian.swarmview.security.auth.jwt.JwtTokenAuthenticationProcessingFilter;
import net.alexfabian.swarmview.security.auth.jwt.SkipPathRequestMatcher;
import net.alexfabian.swarmview.security.auth.jwt.extractor.TokenExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by alexfabian on 12/15/16.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    public static final String JWT_TOKEN_HEADER_PARAM = "Authorization";
    public static final String FORM_BASED_LOGIN_ENTRY_POINT = "/auth/login";
    public static final String TOKEN_BASED_AUTH_ENTRY_POINT = "/**";
    public static final String TOKEN_REFRESH_ENTRY_POINT = "/auth/token";
    public static final String FORM_BASED_REGISTER_ENTRY_POINT = "/register";

    private static final String REALM="MY_TEST_REALM";

    @Autowired
    private RestAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AjaxAuthenticationProvider ajaxAuthenticationProvider;

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private TokenExtractor tokenExtractor;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ObjectMapper objectMapper;

    protected AjaxLoginProcessingFilter buildAjaxLoginProcessingFilter() throws Exception {
        AjaxLoginProcessingFilter filter = new AjaxLoginProcessingFilter(FORM_BASED_LOGIN_ENTRY_POINT, successHandler, failureHandler, objectMapper);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    protected JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter() throws Exception {
        List<String> pathsToSkip = Arrays.asList(TOKEN_REFRESH_ENTRY_POINT, FORM_BASED_LOGIN_ENTRY_POINT, FORM_BASED_REGISTER_ENTRY_POINT);
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, TOKEN_BASED_AUTH_ENTRY_POINT);
        JwtTokenAuthenticationProcessingFilter filter = new JwtTokenAuthenticationProcessingFilter(failureHandler, tokenExtractor, matcher);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // WARNING : the '.hasRole("NAME_OF_ROLE")' method apparently appends 'ROLE_' to the beginning of the role name... so you
        // have to use hasAuthority() if you want the role names to match exactly.
//        http
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .authorizeRequests()
//                    .antMatchers(HttpMethod.POST).hasAnyAuthority("ADMIN", "USER")
//                    .antMatchers(HttpMethod.PUT).hasAnyAuthority("ADMIN", "USER")
//                    .antMatchers(HttpMethod.GET).permitAll()
//                    .antMatchers("/users/**").hasAuthority("ADMIN")
//                    .and()
//                .addFilterBefore(new CustomAuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class)
//                .addFilterBefore(new ManagementEndpointAuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class);
////                .httpBasic()
////                    .realmName(REALM)
////                    .authenticationEntryPoint( new CustomBasicAuthenticationEntryPoint())
////                    .and()



        http
                .csrf().disable() // We don't need CSRF for JWT based authentication
                .exceptionHandling().authenticationEntryPoint(this.authenticationEntryPoint)

                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                    .authorizeRequests()
                    .antMatchers(FORM_BASED_LOGIN_ENTRY_POINT).permitAll() // Login end-point
                    .antMatchers(TOKEN_REFRESH_ENTRY_POINT).permitAll() // Token refresh end-point
                    .antMatchers("/console").permitAll() // H2 Console Dash-board - only for testing
                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/register").permitAll() // Register new user end-point
                    .antMatchers(HttpMethod.POST, "/register/**").permitAll() // Register new user end-point
                    .antMatchers(HttpMethod.POST, "/api/register").permitAll() // Register new user end-point
                    .antMatchers(HttpMethod.POST, "/api/register/**").permitAll() // Register new user end-point
                    .antMatchers("/register/**").permitAll() // Register new user end-point
                    .antMatchers("/register").permitAll() // Register new user end-point
                    .antMatchers("/register*").permitAll() // Register new user end-point
                    .antMatchers("/api/register").permitAll() // Register new user end-point
                    .antMatchers("/api/register/**").permitAll() // Register new user end-point

                .and()
                    .authorizeRequests()
                    .antMatchers(TOKEN_BASED_AUTH_ENTRY_POINT).authenticated() // Protected API End-points

                .and()
                    .addFilterBefore(buildAjaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(ajaxAuthenticationProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);
//        auth.userDetailsService(userDetailsServiceImpl);
    }

    /* To allow Pre-flight [OPTIONS] request from browser */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**"); //TODO: commenting out this line fixes the OPTIONS for auth/login
    }


}
