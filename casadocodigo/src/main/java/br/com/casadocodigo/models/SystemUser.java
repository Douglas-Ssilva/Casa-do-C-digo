package br.com.casadocodigo.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.validator.constraints.Email;

@Entity
public class SystemUser implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	
	@Email
	private String login;
	
	private String senha;
	
	//Cada user terá uma ou mais roles
	 //uma role pode ter mais de um user
	@ManyToMany(fetch= FetchType.EAGER) //Carregue o SystemRole quando pegar o SystemUser
	private List<SystemRole> roles= new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "SystemUser [id=" + id + ", login=" + login + ", senha=" + senha + "]";
	}
}
