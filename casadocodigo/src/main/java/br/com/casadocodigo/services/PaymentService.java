package br.com.casadocodigo.services;

import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.servlet.ServletContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import br.com.casadocodigo.daos.CompraDAO;
import br.com.casadocodigo.models.Compra;

@Path("/pagamento")
public class PaymentService { //Nao foi usado nada do JSF aqui, e sim APIS do Jax-RS e CDI

	@Inject
	private CompraDAO dao;
	@Context 
	private ServletContext context; 
	@Inject
	private JMSContext jmsContext;	//Faz toda comunicação com server. é uma especificação do javaee que nos permite enviar uma mensagem de forma assincrona
	
	//é provido pelo jndi. Tipos de mensagens assincronas: Topicos: lista de discurssão primeiro, manda p lista e chega p várias pessoas.
    //Fila, queue: manda a mensagem p uma, essa pessoa pega a mensagem e faz sua coisa. Cada pessoa recebe uma msg
	@Resource(name="java:/jms/topics/CarrinhoComprasTopico")
	private Destination destination;//destination é criado pelo proprio server 

	// Nos permite executar as coisas por meio de um pool de conexões, criamos um
	// pool autogerenciavel, o java cuida disso
	private static ExecutorService executor = Executors.newFixedThreadPool(50); // Quantidade de threads

				// Só receberá chamadas do tipo Post, Async avisos ao server que minha
				// requisição acabou. @suspended notifica o server que a execução do method será
				// feita de forma assincrona. @suspended faz com que ele libere a thread principal e usamos a thread assincrona de execução
				//Executar o pagamento de forma assincrona permite a liberação da thread p outros usuarios, já que o serviço pode passar por um tempo de instabilidade
	@POST 
	public void pay(@Suspended final AsyncResponse ar, @QueryParam("uuid") String uuid) { // Param que chegará da url
		Compra compra = dao.findByUuid(uuid);
		String contextPath = context.getContextPath(); //dentro da Thread ele nao consegue achar o context

		//temos produtor e um receptor
		JMSProducer jmsProducer = jmsContext.createProducer();
		
		executor.submit(new Runnable() { 

			@Override
			public void run() {
				try {
					PaymentGateway.requestPayment(compra.getTotal());
					
					jmsProducer.send(destination, compra.getUuid());

					// Pegando a API do JAX-RS e criando uma resposta
					URI responseURI = UriBuilder.fromPath("http://localhost:8080" + contextPath + "/index.xhtml").
							queryParam("msg", "Compra realizada com sucesso!").build();
					Response response = Response.seeOther(responseURI).build();
					ar.resume(response); //Notifica o server que pode continuar
				} catch (Exception e) {
					ar.resume(new WebApplicationException(e));
				}
			}
		});
	}

}

























