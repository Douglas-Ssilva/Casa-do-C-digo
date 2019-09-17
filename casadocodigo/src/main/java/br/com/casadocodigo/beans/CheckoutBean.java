package br.com.casadocodigo.beans;

import java.io.Serializable;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import br.com.casadocodigo.models.CarrinhoCompras;
import br.com.casadocodigo.models.Compra;
import br.com.casadocodigo.models.Usuario;

@Model
public class CheckoutBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Usuario usuario= new Usuario();
	
	@Inject
	private CarrinhoCompras carrinhoCompras;
	@Inject
	private FacesContext facesContext;
	
	public void save() {
		Compra compra = new Compra();
		compra.setUsuario(usuario);
		carrinhoCompras.finalize(compra);
		
		executorOtherRequest(compra);
		
		usuario= new Usuario();
	}

	private void executorOtherRequest(Compra compra) {
		//		informando p JSF que queremos fazer uma outra requisição.. Um componente externo dentro da minha aplicação
		//		JSF só consegue chamar outro services se o mesmo for geerenciado pelo service do JSF
		//		Falando p navegador fazer uma requisição externa, que nao faz parte do JSF
				String contextName = facesContext.getExternalContext().getRequestContextPath(); //retorna o nome do contexto
				HttpServletResponse httpServletResponse= (HttpServletResponse) facesContext.getExternalContext().getResponse(); //JSF, me dê o response
				//307 status. Não muda a requisiçao.
				//informando ao navegador p manter o método que foi invocado. Se put manhenha o put. Se post, mantenha (nao é apresentado ao user)
				httpServletResponse.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT); 
				httpServletResponse.setHeader("Location", contextName + "/services/pagamento?uuid=" + compra.getUuid());//fazendo redirect pelo location do header
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}