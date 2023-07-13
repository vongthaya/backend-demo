package com.vongthaya.backenddemo.config;

import com.vongthaya.backenddemo.config.token.TokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final TokenFilter tokenFilter;

    @Autowired
    public SecurityConfig(TokenFilter tokenFilter) {
        this.tokenFilter = tokenFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(config -> {
            config.requestMatchers(HttpMethod.POST, "/user/register").anonymous()
                    .requestMatchers(HttpMethod.POST, "/user/login").anonymous()
                    .anyRequest().authenticated();
        });

        // add this tokenFilter before this UsernamePasswordAuthenticationFilter (basic authentication)
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.cors().disable();

        // use http basic authentication
        http.httpBasic().disable();

        http.csrf().disable();

        return http.build();
    }

}
