package br.com.casadocodigo.beans;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import br.com.casadocodigo.daos.LivroDAO;
import br.com.casadocodigo.models.Livro;

@Model
public class HomeBean {
	
	@Inject
	private LivroDAO dao;
	
	public List<Livro> ultimosLancamentos(){
		System.out.println("Buscando últimos lançamentos");
		return dao.ultimosLancamentos();
	}
	
	public List<Livro> demaisLivros(){
		System.out.println("Buscando demais livros");
		return dao.demaisLivros();
	}
}
