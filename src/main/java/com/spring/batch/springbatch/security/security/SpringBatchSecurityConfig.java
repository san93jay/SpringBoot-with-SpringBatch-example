package com.spring.batch.springbatch.security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringBatchSecurityConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws  Exception{
               httpSecurity
                       .csrf().disable()
                       .authorizeRequests()
                       .antMatchers("/api/batch/save").permitAll()
                       .antMatchers("/api/batch/getById","/api/batch/update").hasRole("ADMIN")
                       .anyRequest()
                       .authenticated()
                       .and()
                       .httpBasic();
        return httpSecurity.build();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.
                inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN");
    }
}
