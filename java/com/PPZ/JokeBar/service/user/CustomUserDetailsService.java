package com.PPZ.JokeBar.service.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.PPZ.JokeBar.dto.UserRepository;
import com.PPZ.JokeBar.model.User;

public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username);
		if (user == null) {
            throw new UsernameNotFoundException("No user found with the given email.");
        }
		return new CustomerUserDetails(user);
	}

}
