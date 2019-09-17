package br.com.casadocodigo.daos;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.casadocodigo.models.SystemUser;

public class SecurityDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public SystemUser findByEmail(String email) {
		return entityManager.createQuery("select su from SystemUser su where su.login= :login", SystemUser.class)
				.setParameter("login", email).getSingleResult();
	}

}
