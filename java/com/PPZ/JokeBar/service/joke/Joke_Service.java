package com.PPZ.JokeBar.service.joke;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PPZ.JokeBar.dto.JokeRepository;
import com.PPZ.JokeBar.model.Joke;
import com.PPZ.JokeBar.model.User;

@Service
@Transactional
public class Joke_Service {

@Autowired JokeRepository repo;
	
	//get one
	public Joke get(int jokeid)
	{
		return repo.findById(jokeid).get();
	}
	
	//get all
	public List<Joke> listAll()
	{
		return repo.findAll();
	}
	
	//delete 
	public void delete(int jokeid)
	{
		repo.deleteById(jokeid);
	}
	
	//add
	public void add(Joke joke)
	{
		repo.save(joke);
	}
	
	public List<Joke> listJokesByUser(User user){
		return repo.findAllByUser(user);
	}
	
}
