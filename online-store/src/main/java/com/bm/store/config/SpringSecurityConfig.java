package com.bm.store.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";

    @Value("${config.springSecurityConfig.userPassword}")
    public String userPassword;

    @Value("${config.springSecurityConfig.adminPassword}")
    public String adminPassword;

    @Bean
    public InMemoryUserDetailsManager userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        UserDetails defaultUser = User.withUsername("user")
                .password(bCryptPasswordEncoder.encode(userPassword))
                .roles(USER)
                .build();
        UserDetails admin = User.withUsername("admin")
                .password(bCryptPasswordEncoder.encode(adminPassword))
                .roles(USER, ADMIN)
                .build();
        return new InMemoryUserDetailsManager(defaultUser, admin);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authorizeHttpRequests ->
                authorizeHttpRequests
                        .requestMatchers("/",
                                "/favicon.ico",
                                "/api/cart/**",
                                "/api/catalogue/**",
                                "/api/product/**",
                                "/actuator/**",
                                "/explorer/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/swagger-resources",
                                "/api-docs/**",
                                "/h2-console/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/account/**").hasAnyRole(USER,ADMIN)
                        .requestMatchers(HttpMethod.DELETE,"/api/account/**").hasAnyRole(USER, ADMIN))
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
