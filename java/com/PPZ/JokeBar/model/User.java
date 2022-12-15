package com.PPZ.JokeBar.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "user")
@DynamicUpdate
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotBlank(groups = {CreateGroup.class, UpdateGroup.class})
	@Email(message = "Please enter a valid email address")
	private String email;
	
	@NotBlank(groups =CreateGroup.class)
	@Transient
	@Size(min=6, max=20)
	private String passwordUnHashed;
	
	@Transient
	private String rePassword;
	
	@Column(name="password", nullable = false, length = 64)
	private String password;
	
	@NotBlank(groups = {CreateGroup.class, UpdateGroup.class})
	@Size(min=3, max=50)
	private String name;
	
	private int credit = 50;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date joinDate;	
	
	public int getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPasswordUnHashed() {
		return passwordUnHashed;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public int getCredit() {
		return credit;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPasswordUnHashed(String passwordUnHashed) {
		this.passwordUnHashed = passwordUnHashed;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}
	

}
