package com.emp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/h2-console/**").permitAll()
            .antMatchers(HttpMethod.POST, "/employees").hasRole("ADMIN")
            .antMatchers(HttpMethod.GET, "/employees/get/**").hasAnyRole("ADMIN", "USER")
            .anyRequest().authenticated()
            .and()
            .httpBasic();
        
        
        
        http.headers().frameOptions().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
	        .withUser("admin")
	        .password("admin@123")
	        .roles("ADMIN")
	        .and()
	        .withUser("user")
            .password("user@123")
            .roles("USER");
            
            
        
    }
    @Bean
    public static NoOpPasswordEncoder noOpPasswordEncoder(){
    	return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}

