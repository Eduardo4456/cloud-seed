package com.example.CloudSeed.model;

import java.time.Instant;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tb_user")
public class UserModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user")
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@CreationTimestamp
	private Instant creationTimestamp;

	@UpdateTimestamp
	private Instant updatedTimestanp;

	public UserModel() {
	}

	public UserModel(Long id, String username, String email, String password, Instant creationTimestamp, Instant updatedTimInstant) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.creationTimestamp = creationTimestamp;
		this.updatedTimestanp = updatedTimInstant;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Instant getUpdatedTimestanp() {
		return updatedTimestanp;
	}

	public void setUpdatedTimestanp(Instant updatedTimestanp) {
		this.updatedTimestanp = updatedTimestanp;
	}
}
