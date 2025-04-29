package com.example.CloudSeed.model;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tb_user")
public class UserModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "username")
	private String username;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@CreationTimestamp
	private Instant creationTimestamp;

	@UpdateTimestamp
	private Instant updatedTimInstant;

	public UserModel() {
	}

	public UserModel(long id, String username, String email, String password, Instant creationTimestamp, Instant updatedTimInstant) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.creationTimestamp = creationTimestamp;
		this.updatedTimInstant = updatedTimInstant;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Instant getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Instant creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public Instant getUpdatedTimInstant() {
		return updatedTimInstant;
	}

	public void setUpdatedTimInstant(Instant updatedTimInstant) {
		this.updatedTimInstant = updatedTimInstant;
	}
}
