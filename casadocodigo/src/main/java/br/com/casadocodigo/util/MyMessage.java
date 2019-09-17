package br.com.casadocodigo.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MyMessage {
	
	public static void addMsgSuccessfully(String msg) {
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, ""));
	}
	public static void addMsgError(String msg) {
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, ""));
	}
	
	private static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

}
