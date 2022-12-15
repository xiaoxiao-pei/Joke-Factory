package com.PPZ.JokeBar.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PPZ.JokeBar.dto.AdminRepository;
import com.PPZ.JokeBar.model.Admin;

@Service
@Transactional
public class Admin_service {
	@Autowired
	private AdminRepository repo;
	
	//create
	public void save(Admin admin) {
		repo.save(admin);
	}
	
	//get by id
	public Admin get(String id) {
		return repo.findByidAdmin(id);
	}
	

}
