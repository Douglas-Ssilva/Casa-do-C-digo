package br.com.casadocodigo.convertrs;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass= Calendar.class) //Converter p classe X
public class ConverterCalendar implements Converter{
	
	private DateTimeConverter timeConverter= new DateTimeConverter();
	
	public ConverterCalendar() {
		this.timeConverter.setPattern("dd/MM/yyyy");
		this.timeConverter.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
		System.out.println("Executando converter de calendar");
	}

	@Override //Conversão de tela p MB
	public Object getAsObject(FacesContext context, UIComponent component, String dataTexto) {
		if (dataTexto == null || dataTexto.trim().isEmpty()) {
			return null;
		}
		Date date= (Date) this.timeConverter.getAsObject(context, component, dataTexto); //Converta a dataTexto p Date
		Calendar calendar= Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	@Override //Conversão de MB p tela
	public String getAsString(FacesContext context, UIComponent component, Object dataObject) {
		if(dataObject == null) {
			return null;
		}
		Calendar calendar= (Calendar) dataObject;
		return this.timeConverter.getAsString(context, component, calendar.getTime());
	}

}
