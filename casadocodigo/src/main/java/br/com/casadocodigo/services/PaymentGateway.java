package br.com.casadocodigo.services;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;

import br.com.casadocodigo.models.Payment;

public class PaymentGateway implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String URL = "http://book-payment.herokuapp.com/payment";

	public static String requestPayment(BigDecimal value) { //JAX-RS nos permite fazer a integração com um sistema REST
		Client client = ClientBuilder.newClient();
		Entity<Payment> json = Entity.json(new Payment(value));
		return client.target(URL).request().post(json, String.class);
	}

}
