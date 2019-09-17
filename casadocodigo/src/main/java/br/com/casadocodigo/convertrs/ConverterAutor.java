package br.com.casadocodigo.convertrs;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.casadocodigo.models.Autor;

@FacesConverter("converterAutor")
public class ConverterAutor implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String id) {
		if (id == null || id.trim().isEmpty()) {
			return null;
		}
		System.out.println("Converterndo p objeto: " + id);
		//É preciso implementar equals e hashcode
		Autor autor = new Autor(); 
		autor.setId(Integer.valueOf(id)); //Equals foi definido que um autor é= a outro se tiverem o mesmo id
		return autor;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object autor) {
		if (autor == null) {
			return null;
		}
		System.out.println("Converterndo p String: " + autor);
		Autor autor2= (Autor) autor;
		return autor2.getId().toString();
	}

}
