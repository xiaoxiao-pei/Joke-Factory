package com.PPZ.JokeBar.service.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.PPZ.JokeBar.dto.AdminRepository;
import com.PPZ.JokeBar.model.Admin;

public class AdminUserDetailsService implements UserDetailsService {

	@Autowired
	private AdminRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException {
		Admin admin = repo.findByidAdmin(username);
		return new AdminUserDetails(admin);
	}

}
