package com.PPZ.JokeBar.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.PPZ.JokeBar.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, String>{
	@Query("SELECT a FROM Admin a WHERE a.id_admin = ?1")
	public Admin findByidAdmin(String id_admin);
}
