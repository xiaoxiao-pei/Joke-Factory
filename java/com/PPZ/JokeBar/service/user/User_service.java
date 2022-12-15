package com.PPZ.JokeBar.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PPZ.JokeBar.dto.UserRepository;
import com.PPZ.JokeBar.model.User;

@Service
@Transactional
public class User_service {
	
	@Autowired
	private UserRepository repo ;
	
	public List<User> ListAll() {
		return repo.findAll();
	}
	
	//create
	public void save(User user) {
		repo.save(user);
	}
	
	//delete
	public void delete(Integer id) {
		repo.deleteById(id);
	}
	
	//get by id
	public User get(Integer id) {
		return repo.findById(id).get();
	}

}
