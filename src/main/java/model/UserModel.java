package model;

import java.util.List;

import jakarta.persistence.*;

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

	private List<PlantModel> plants;
	
	public UserModel() {
		
	}

	public UserModel(long id, String username, String email, String password) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public String getEmailUser() {
		return email;
	}

	public void setEmailUser(String email) {
		this.email = email;
	}

	public long getIdUser() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
