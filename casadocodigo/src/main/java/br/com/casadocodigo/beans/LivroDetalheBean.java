package br.com.casadocodigo.beans;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import br.com.casadocodigo.daos.LivroDAO;
import br.com.casadocodigo.models.Livro;

@Model
public class LivroDetalheBean {
	
	@Inject
	private LivroDAO dao;
	private Integer id;
	private Livro livro;
	
	public void carregaLivro() {
		this.livro= dao.findById(id);
	}
	
	public LivroDAO getDao() {
		return dao;
	}
	public void setDao(LivroDAO dao) {
		this.dao = dao;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Livro getLivro() {
		return livro;
	}
	public void setLivro(Livro livro) {
		this.livro = livro;
	} 

}
