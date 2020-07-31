package com.weiwudev.config;

import com.weiwudev.filters.CustomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SecurityContextRepository securityContextRepository;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .pathMatchers("/AuthService/register").hasAuthority("ROLE_SYSTEM")
                .pathMatchers("/UserService/**").hasAuthority("ROLE_USER")
                .pathMatchers("/AuthService/**", "/checkno", "/AuthService/logout", "/RegistrationService/register", "/RegistrationService/check", "/AuthService/check", "/AuthService/checkno").permitAll()
                .pathMatchers("/check").authenticated()
                .anyExchange().authenticated()
                .and()
                .httpBasic().disable()
                .cors().and()
                .csrf().disable()
                .build();

    }

}
