package com.PPZ.JokeBar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(name = "admin")
public class Admin {
	
	@Id
	@Column
	private String id_admin;
	
	@Column
	@NotBlank
	@Size(min = 3, max = 20)
	private String firstname;
	
	@Column
	@NotBlank
	@Size(min = 3, max = 20)
	private String lastname;
	
	@Column
	@Email
	@NotBlank
	private String email;
	
	
	@Transient
	@Size(min = 6, max = 20)
	private String passwordUnHashed;
	
	@Transient
	@Size(min = 6, max = 20)
	private String rePassword;
	
	
	@Column
	private String password;
	
	public Admin() {}


	public Admin(String id_admin,String firstname, String lastname,String email, String passwordUnHashed,  String password) {
		super();
		this.id_admin = id_admin;
		this.firstname = firstname;
		this.lastname = lastname;
		this.passwordUnHashed = passwordUnHashed;
		this.email = email;
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getPasswordUnHashed() {
		return passwordUnHashed;
	}

	public String getPassword() {
		return password;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setPasswordUnHashed(String passwordUnHashed) {
		this.passwordUnHashed = passwordUnHashed;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getId_admin() {
		return id_admin;
	}


	public void setId_admin(String id_admin) {
		this.id_admin = id_admin;
	}


	public String getRePassword() {
		return rePassword;
	}


	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}
	
}
