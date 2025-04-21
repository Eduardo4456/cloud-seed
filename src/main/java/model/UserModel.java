package model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_user")
public class UserModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idUser;
	
	private String nameUser;
	private String emailUser;
	private List<PlantModel> plants;
	
	public UserModel() {
		
	}

	public UserModel(long idUser, String nameUser, String emailUser) {
		super();
		this.idUser = idUser;
		this.nameUser = nameUser;
		this.emailUser = emailUser;
	}

	public String getNameUser() {
		return nameUser;
	}

	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}

	public String getEmailUser() {
		return emailUser;
	}

	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}

	public long getIdUser() {
		return idUser;
	}
	
}
