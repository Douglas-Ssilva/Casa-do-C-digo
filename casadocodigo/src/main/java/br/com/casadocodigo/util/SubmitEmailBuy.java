package br.com.casadocodigo.util;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import br.com.casadocodigo.daos.CompraDAO;
import br.com.casadocodigo.models.Compra;

//Fazendo o email ser enviado em segundo plano, a fim de nao pesar mais a aplicação
//Config p class ser o receptor das mensagens. Por meio dessa annotation conseguimos escutar as mensagens
//@MessageDriven(activationConfig= {
//		@ActivationConfigProperty(propertyName="destinationLookup", 
//				propertyValue="java:/jms/topics/CarrinhoComprasTopico"),
//})
public class SubmitEmailBuy implements MessageListener{

	@Inject
	private MailSender mailSender;
	@Inject
	private  CompraDAO dao;

	@Override
	public void onMessage(Message message) {
		TextMessage textMessage= (TextMessage) message;//prgando o texto que foi enviado do outro lado
		try {
			Compra compra = dao.findByUuid(textMessage.getText());
			mailSender.send("douglaaasilva952@gmaill.com", compra.getUsuario().getEmail(), "Compra na Casa do Código","Compra na Casa do Código");
		} catch (JMSException e) {
			e.printStackTrace(); //Como o email é enviado de forma assincrona, nao tem muito o que fazer, visto que o user nem na aplicação pode estar aqui mais
		}
	}
	


}
