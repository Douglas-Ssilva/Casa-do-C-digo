package br.com.casadocodigo.beans;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import br.com.casadocodigo.daos.LivroDAO;
import br.com.casadocodigo.models.CarrinhoCompras;
import br.com.casadocodigo.models.CarrinhoItem;
import br.com.casadocodigo.models.Livro;

@Model
public class CarrinhoComprasBean {

	@Inject
	private LivroDAO dao;
	
	@Inject
	private CarrinhoCompras carrinho;
	
	
	public String add(Integer id) {
		Livro livro= dao.findById(id); //Guardando o livro no carrinho de compras
		CarrinhoItem item = new CarrinhoItem(livro);
		carrinho.add(item);
		return "carrinho?faces-redirect=true";
	}
	
}
