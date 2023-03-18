package mg.inclusiv.mihary.configuration;

import lombok.RequiredArgsConstructor;
import mg.inclusiv.mihary.filter.JWTAuthenticationFilter;
import mg.inclusiv.mihary.filter.JWTAuthorizationFilter;
import mg.inclusiv.mihary.service.AuthenticationUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationUserDetailService authenticationUserDetailService;

    @Override protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, AuthenticationConfigConstants.SIGN_UP_URL).permitAll()
                //ROLE BASED AUTHENTICATION START
                .antMatchers("/api/utilisateurs/**").anonymous()
                .antMatchers("/api/**").permitAll()
                //.antMatchers("/**").hasAnyAuthority("USER", "ADMIN")
           //.antMatchers("/**").hasAnyAuthority("COOPERATIVE")
              //  .antMatchers("/**").hasAnyAuthority("AGRICULTEUR")
            //.antMatchers("/**").hasAnyAuthority("CLIENT")
                //ROLE BASED AUTHENTICATION END
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationUserDetailService).passwordEncoder(bCryptPasswordEncoder);
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
