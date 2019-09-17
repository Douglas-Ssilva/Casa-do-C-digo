package br.com.casadocodigo.util;

import java.security.Principal;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.com.casadocodigo.daos.SecurityDAO;
import br.com.casadocodigo.models.SystemUser;

@Model
public class CurrentUser {
	
	@Inject
	private HttpServletRequest httpServletRequest;
	@Inject
	private SecurityDAO dao;
	
	private SystemUser systemUser;
	
	public SystemUser get() {
		return systemUser;
	}
	
	public boolean hasRole(String role) {
		return httpServletRequest.isUserInRole(role); //JAAS se integra com a API de servlets 3, assim conseguimos saber se o user tem role
	}
	
	@PostConstruct
	public void init() {
		Principal principal= httpServletRequest.getUserPrincipal();
		if (principal != null) {
			systemUser= dao.findByEmail(httpServletRequest.getUserPrincipal().getName()); //Pegando email
		}
	}
	
	public String logout() {
		httpServletRequest.getSession().invalidate();
		return "/pages/list_livros.xhtml?faces-redirect=true";
	}
}






























