package br.com.casadocodigo.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;

import br.com.casadocodigo.daos.LivroDAO;
import br.com.casadocodigo.models.Livro;

@Path("livros")
public class LivroResource {
	
	@Inject
	private LivroDAO dao;
	
	//Retorna tanto xml quanto json depende do content negotiation que passamos no postman
	@GET
	@Path("lancamentos")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Wrapped(element= "livros") //Annotation do RestEasy que nos permite modificar a tag raiz, antes estava collection no xml
	public List<Livro> ultimosLancamentosJson(){
		return dao.ultimosLancamentosJson();
	}
	
//	@GET
//	@Path("xml")
//	@Produces({MediaType.APPLICATION_XML})
//	@Wrapped(element= "livros") //Annotation do RestEasy que nos permite modificar a tag raiz
//	public List<Livro> ultimosLancamentosXml(){
//		return dao.ultimosLancamentosJson();
//	}

}



























