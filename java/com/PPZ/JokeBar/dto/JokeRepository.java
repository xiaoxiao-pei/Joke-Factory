package com.PPZ.JokeBar.dto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.PPZ.JokeBar.model.Joke;
import com.PPZ.JokeBar.model.User;

public interface JokeRepository extends JpaRepository<Joke, Integer> {
	@Query("SELECT j FROM Joke j WHERE j.fingerprint=?1")
	public Joke findByFingerprint(String fingerprint);
	
	public List<Joke> findAllByUser(User user);
	
	
}
