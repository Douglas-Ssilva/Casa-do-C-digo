package br.com.casadocodigo.daos;

import java.util.List;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.QueryHints;

import br.com.casadocodigo.models.Livro;

//@Stateful //Uso do EXTENDED está restrito a essa annotation. Fica vivo durante o Request do user, @Model
		  //Por isso devemos evitar annotations que mantém nossos Beans vivos por muito tempo
public class LivroDAO {
	
	//@PersistenceContext(type= PersistenceContextType.EXTENDED) //Fique vivo durante todo o escopo e nao durante apenas uma transação(manager). A fim de evitar lazy nos autores
	@PersistenceContext													   //Não preciso colocar REquestScoped aqui, pois foi feito no detalheBEan
	private EntityManager manager;
	
	public void insert(Livro livro) {
		this.manager.persist(livro);
	}

	public List<Livro> getAll() {
		System.out.println("Retornando Livros...");
		String jpql= "select distinct(l) from Livro l join fetch l.autores"; //fetch traga os autores relacionados àquele livro
		TypedQuery<Livro> query= this.manager.createQuery(jpql, Livro.class);//distinct independente da quantidade de autores que aquele livro possui, ele trará somente 1 livro
		return query.getResultList();										// e nao o repetirá p cada autor
	}

	public List<Livro> ultimosLancamentos() {
		String jpql= "select l from Livro l order by l.dataPublicacao desc";
		return this.manager.createQuery(jpql, Livro.class)
				.setHint(QueryHints.HINT_CACHEABLE, true)		//JPA é o responsavel por buscar os dados no banco, cache de segundo nivel do Hibernate(IMPL). Mantenha o resultado da query no cache
				.setHint(QueryHints.HINT_CACHE_REGION, "home") //funcionalidade do hibernate, criando uma region especifica
				.setMaxResults(5).getResultList(); //Limitando a 3 resultados
	}
	
	public List<Livro> demaisLivros() {
		String jpql= "select l from Livro l order by l.id desc";
		return this.manager.createQuery(jpql, Livro.class)
				.setHint(QueryHints.HINT_CACHEABLE, true) //Cacheie essa query
				.setHint(QueryHints.HINT_CACHE_REGION, "home")
				.setFirstResult(5).getResultList(); //Pegando a partir do 3 result
	}

	public Livro findById(Integer id) {
//		return this.manager.find(Livro.class, id); Uso com a annotations do ejb e Extend
		String jpql= "select l from Livro l join fetch l.autores where l.id = :id";  	//Faz somente um select. Fazendo um carregamento antecipado dos autores evitando 
		return this.manager.createQuery(jpql, Livro.class).setParameter("id", id).getSingleResult(); //assim a exception de lazy
	}
	
	//Lazy ocorre quando ele tenta carregar uma lista e o manager já foi fechado
	
	public void clearCache() {
		Cache cache = manager.getEntityManagerFactory().getCache();
		cache.evict(Livro.class); //Limpe o cache dessaa class, há sobrecarga desse método, podendo passar ID
		cache.evictAll(); //limpando cache de todas entidades
		
		SessionFactory unwrap = manager.getEntityManagerFactory().unwrap(SessionFactory.class);
		unwrap.getCache().evictAllRegions(); //separando por regiões
		unwrap.getCache().evictQueryRegion("home"); //limpando cache apenas da tela X
	}
	
	public List<Livro> ultimosLancamentosJson(){
		return manager.createQuery("select l from Livro l join fetch l.autores order by l.dataPublicacao desc", Livro.class).getResultList();
	}

}


























