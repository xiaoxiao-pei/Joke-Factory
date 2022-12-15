package com.PPZ.JokeBar.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.PPZ.JokeBar.service.admin.AdminUserDetailsService;

@Configuration
@Order(1)
public class AdminSecurityConfig {
 
    @Bean
    public UserDetailsService userDetailsService() {
        return new AdminUserDetailsService();
    }
 
    @Bean
	public BCryptPasswordEncoder passwordEncoder1() {
		return new BCryptPasswordEncoder();
	}
 
    @Bean
    public DaoAuthenticationProvider authenticationProvider1() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder1());
 
        return authProvider;
    }
 
    @Bean
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider1());
 
        http.authorizeRequests().antMatchers("/").permitAll();
 
        http.antMatcher("/admin/**")
            .authorizeRequests().anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/admin/login")
                    .usernameParameter("id_admin")
                    .loginProcessingUrl("/admin/login")
                    .defaultSuccessUrl("/admin/home")
                .permitAll()
            .and()
            .logout()
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/");
 
        return http.build();
    }
 
}
