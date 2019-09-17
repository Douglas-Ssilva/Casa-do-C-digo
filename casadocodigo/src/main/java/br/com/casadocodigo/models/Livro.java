package br.com.casadocodigo.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Cacheable //Informando que a entidade será cacheada
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD) //Para que possa alterar nome da tag xml de cada atributo
public class Livro {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	
	//Pois espaço não necessariamente seja vazio, Valida já vazio e espeços em branco
	@NotBlank(message="Título não pode estar vazio!")
	private String titulo;
	
	@Lob //Uso p textos longos
	@NotBlank //caso coloque 10 caracteres e um espaço
	@Length(min= 10)
	private String descricao;
	
	@Min(value=50, message="O valor minino é 50!")
	private Integer numeroPaginas;
	
	@DecimalMin(value="20")
	private BigDecimal preco;
	
	@ManyToMany
	@Size(min=1)
	@NotNull // A lista não pode ser nula
	@XmlElement(name="autor")
	@XmlElementWrapper(name="autores")
	private List<Autor> autores= new ArrayList<>();
	
	private String capaPath; //Estava havendo conflito de validações devido aos files de menssage
	
	@Temporal(TemporalType.DATE)
	@NotNull
	private Calendar dataPublicacao;
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Integer getNumeroPaginas() {
		return numeroPaginas;
	}
	public void setNumeroPaginas(Integer numeroPaginas) {
		this.numeroPaginas = numeroPaginas;
	}
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	
	public List<Autor> getAutores() {
		return autores;
	}
	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}
	public Calendar getDataPublicacao() {
		return dataPublicacao;
	}
	public void setDataPublicacao(Calendar dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}
	public String getCapaPath() {
		return capaPath;
	}
	public void setCapaPath(String capaPath) {
		this.capaPath = capaPath;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Livro other = (Livro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Livro [id=" + getId() + ", titulo=" + titulo + ", descricao=" + descricao + ", numeroPaginas="
				+ numeroPaginas + ", preco=" + preco + ", autores=" + autores + "]";
	}
}
