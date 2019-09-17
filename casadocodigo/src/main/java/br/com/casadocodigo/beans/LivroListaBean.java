package br.com.casadocodigo.beans;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import br.com.casadocodigo.daos.LivroDAO;
import br.com.casadocodigo.models.Livro;

//Junção do Named com Request Scoped
@Model
public class LivroListaBean {
	
	@Inject
	private LivroDAO dao;
	private List<Livro> list= new ArrayList<>();
	
	public List<Livro> getLivros(){
		return dao.getAll();
	}

	public List<Livro> getList() {
		return list;
	}
}
