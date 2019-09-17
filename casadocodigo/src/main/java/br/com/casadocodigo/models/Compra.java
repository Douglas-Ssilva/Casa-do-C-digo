package br.com.casadocodigo.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

@Entity
public class Compra implements Serializable {		//Compra possui usuários e itens

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(cascade= CascadeType.PERSIST) //salvando o user juntamente com a compra. Sempre que salvar uma compra salve o user junto.
	private Usuario usuario;
	
	private String uuid;
	
	private BigDecimal total;
	
	private String itens;
//	private List<CarrinhoItem> itens;
//	1- Carrinho item nao é uma entidade
//	2- CarrinhoItem so representa os itens daquele momento. Não é um item vendido.
//	3- Melhor prática seria criar uma entidade itensVendidos
//	4- A vantagem de salvar texto ???
//	XML
//	JSON
//	Estes formatos são os mais comuns para transportar dados de um sistema para outro! Então salvar os itens como texto vai nos permitir essa 
//	flexibilidade e evoluir nosso sistema.
	

	
	//gerando uuid a fim  de  o user nao vir o ID do user na URL
	@PrePersist //JPA antes ded persistir, chame esse método 
	public void createUuid() {
		uuid=  UUID.randomUUID().toString();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getItens() {
		return itens;
	}

	public void setItens(String itens) {
		this.itens = itens;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
}
