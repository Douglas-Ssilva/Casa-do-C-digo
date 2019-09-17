package br.com.casadocodigo.util;

import javax.ejb.Stateless;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;

//Interceptor de mensagens, configuração de destino
@JMSDestinationDefinitions({
	@JMSDestinationDefinition(
		name="java:/jms/topics/CarrinhoComprasTopico",  
		interfaceName="javax.jms.Topic",
		destinationName="java:/jms/topics/CarrinhoComprasTopico")})

@Stateless
public class JMSInterceptDestination {

}
