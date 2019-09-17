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

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(
				propertyName="destinationLookup",
				propertyValue="java:/jms/topics/CarrinhoComprasTopico"),
		@ActivationConfigProperty(
		        propertyName="destinationType",
		        propertyValue="javax.jms.Topic")
})
public class EnviaEmailCompra implements MessageListener {

	@Inject
	private MailSender mailSender;
	
	@Inject
	private CompraDAO compraDao;

	@Override
	public void onMessage(Message message) {

		try {
			TextMessage textMessage = (TextMessage) message;

			Compra compra = compraDao.findByUuid(textMessage.getText());

			String messageBody = "Sua compra foi realizada com sucesso, com número de pedido " + compra.getUuid();
			mailSender.send("compras@casadocodigo.com.br", compra.getUsuario().getEmail(), "Nova compra na CDC",
					messageBody);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
