package com.PPZ.JokeBar;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.PPZ.JokeBar.dto.AdminRepository;
import com.PPZ.JokeBar.model.Admin;

@Configuration
public class DatabaseLoader {
	private AdminRepository repo;
 
    public DatabaseLoader(AdminRepository repo) {
        this.repo = repo;
    }
 
    @Bean
    public CommandLineRunner initializeDatabase() {
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return args -> {
            Admin admin1 = new Admin("admin001", "Oliver", " Mann","oliver@gmail.com","admin001",passwordEncoder.encode("admin001"));
            Admin admin2 = new Admin("admin002", "Kylan", "Bentley","Kylan@gmail.com","admin002",passwordEncoder.encode("admin002"));
            Admin admin3 = new Admin("admin003", "Ivy", "Jefferson","Ivy@gmail.com","admin002",passwordEncoder.encode("admin003")); 
            repo.saveAll(List.of(admin1, admin2, admin3));
       
            System.out.println("Database initialized");
        };
    }

}