package com.example.demo.secConfig;

import org.apache.coyote.Request;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JWTFilter jwtFilter;
    @Autowired
    private UserDetailsService userDetailsService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf(Customizer->Customizer.disable()).//to remove the csrf
                authorizeHttpRequests(Request->Request
                .requestMatchers("/","/register","/login")
                .permitAll()
                .anyRequest().authenticated()).//to authenticate every request credentials
                httpBasic(Customizer.withDefaults()).//the basic
                sessionManagement(Session->Session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).//making the session as stateless
                addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).
                build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();//it returns the authMang obj
    }

    //Authentication provider for the dao obj to fetch from the data base
    @Bean
    public AuthenticationProvider authenticationProvider(){//It is an interface so we need an
        // class which implements this auth provider so daoAuthPro is used
//      and it requires the userdetails in constructor initializer
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;
    }
}
