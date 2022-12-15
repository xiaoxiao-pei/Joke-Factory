package com.PPZ.JokeBar.model;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="joke")
public class Joke {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int jokeid;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userid")
	private User user;
	
	/* private int userid; */
	
	@NotBlank
	private String topic;
	
	@NotBlank
	@Size(min=30, max=500)
	private String jokecontent;

	private String fingerprint;
	
	private int scores = 0;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name="userid", referencedColumnName = "id",nullable=true)
//	private User user;
	
	public int getJokeid() {
		return jokeid;
	}


	public String getTopic() {
		return topic;
	}

	public String getJokecontent() {
		return jokecontent;
	}

	public String getFingerprint() {
		return fingerprint;
	}

	public int getScores() {
		return scores;
	}

	public void setJokeid(int jokeid) {
		this.jokeid = jokeid;
	}


	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setJokecontent(String jokecontent) {
		this.jokecontent = jokecontent;
	}

	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	public void setScores(int scores) {
		this.scores = scores;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	

}
