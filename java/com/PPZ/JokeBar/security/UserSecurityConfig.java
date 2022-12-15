package com.PPZ.JokeBar.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.PPZ.JokeBar.service.user.CustomUserDetailsService;



@Configuration
@Order(2)
public class UserSecurityConfig {
 
    @Bean
    public UserDetailsService userDetailsService2() {
        return new CustomUserDetailsService();
    }
 
    @Bean
	public BCryptPasswordEncoder passwordEncoder2() {
		return new BCryptPasswordEncoder();
	}
 
    @Bean
    public DaoAuthenticationProvider authenticationProvider2() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService2());
        authProvider.setPasswordEncoder(passwordEncoder2());
 
        return authProvider;
    }
 
    @Bean
    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider2());
 
        http.authorizeRequests().antMatchers("/").permitAll();
 
        http.antMatcher("/user/**")
            .authorizeRequests().anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/user/login")
                    .usernameParameter("email")
                    .loginProcessingUrl("/user/login")
                    .defaultSuccessUrl("/user/home")
                .permitAll()
            .and()
            .logout()
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/");
 
        return http.build();
    }
 
}
