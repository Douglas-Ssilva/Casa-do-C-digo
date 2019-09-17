package br.com.casadocodigo.beans;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.transaction.Transactional;

import br.com.casadocodigo.daos.AutorDAO;
import br.com.casadocodigo.daos.LivroDAO;
import br.com.casadocodigo.models.Autor;
import br.com.casadocodigo.models.Livro;
import br.com.casadocodigo.util.FileSaver;

@Named
@RequestScoped
public class LivroBean {

	private Livro livro = new Livro();
	// CDI já possui o manager injetado aqui, se eu dar o new terei que injetar o entityManager 
	@Inject
	private LivroDAO dao;
	@Inject //Faço o CDI nos intregar esse objeto
	private AutorDAO daoAutor;
	@Inject //poderia passar a inicialização no construtor também, porém com CDI é melhor prática
	private FacesContext context;
	private Part capaLivro;
	
	@Transactional // Passo onde que começa e termina a transação.Annotation responsável por iniciar transações. Faz parte do JTA
	public String save() {
		dao.insert(livro);
		FileSaver fileSaver = new FileSaver();
		String wayImage = fileSaver.write(capaLivro, "livros");
		livro.setCapaPath(wayImage);
		displayMessage();
		return "/pages/list_livros?faces-redirect=true"; // Se não usarmos o redirect ao dar enter na páginas, cadastra
	}

	private void displayMessage() {
		Flash flash = context.getExternalContext().getFlash();
		flash.setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Saved successfully!"));
	}

	public List<Autor> getAutores() {
		return daoAutor.getAutores();
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Part getCapaLivro() {
		return capaLivro;
	}

	public void setCapaLivro(Part capaLivro) {
		this.capaLivro = capaLivro;
	}
}
