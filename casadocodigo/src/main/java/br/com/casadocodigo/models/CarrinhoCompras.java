package br.com.casadocodigo.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.transaction.Transactional;

import br.com.casadocodigo.daos.CompraDAO;
import br.com.casadocodigo.util.MyMessage;

@Named
@SessionScoped
public class CarrinhoCompras implements Serializable { // Sempre que usamos session temos que implementar essa
														// interface, pois o CDI guarda o objeto no disco p usá-lo mais
														// tarde
	private static final long serialVersionUID = 1L;

	private Set<CarrinhoItem> itens = new LinkedHashSet<>();

	@Inject
	private CompraDAO compraDAO;

	public void add(CarrinhoItem item) {
		this.itens.add(item);
	}

	public void remove(CarrinhoItem item) {
		this.itens.remove(item); // Procura pelo hash do item e tenta remover pelo Hash do Item
	}

	// Pegando a quantidade total de cada carrinhoItem
	public Integer getTotalItens() {
		return this.itens.stream().mapToInt(item -> item.getQuantidade()).sum();
	}

	public List<CarrinhoItem> getItens() {
		return new ArrayList<>(itens); // ui:repeat nao se comporta muito bem com o Set
	}

	public BigDecimal getPrecoPorItem(CarrinhoItem item) {
		return item.getLivro().getPreco().multiply(new BigDecimal(item.getQuantidade()));
	}

	public BigDecimal getTotal() {
		BigDecimal total = BigDecimal.ZERO;

		for (CarrinhoItem carrinhoItem : itens) {
			total = total
					.add(carrinhoItem.getLivro().getPreco().multiply(new BigDecimal(carrinhoItem.getQuantidade())));
		}
		return total;
	}

	@Transactional
	public void finalize(Compra compra) {
		try {
//			String response = PaymentGateway.requestPayment(getTotal());
			
			compra.setItens(convertToJson());
			compra.setTotal(getTotal());
			compraDAO.save(compra);
			
			MyMessage.addMsgSuccessfully("Pagamento efetuado com sucesso!");
		} catch (RuntimeException e) {
			MyMessage.addMsgError("System unavailable!");
			e.printStackTrace();
		}
	}

//	private String clientPayment() {
		// Usando o JAX-RS
//		Client client = ClientBuilder.newClient(); // contrutor do client, assim como os plugins de navegadores são
//		Payment payment = new Payment(getTotal());
//		String target = "http://book-payment.herokuapp.com/payment";
//		Entity<Payment> json = Entity.json(payment);// Transforma um objeto p json p ser enviado pelo Jax
//		return client.target(target).request().post(json, String.class);
//		WebTarget webTarget = client.target(target); // enviando o json
//		Builder request = webTarget.request();// ligando o alvo com json
//		String response = request.post(json, String.class); // passo o json e o tipo de retorno
//	}

	// Usando a src
	private String convertToJson() {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		for (CarrinhoItem carrinhoItem : itens) {
			jsonArrayBuilder.add(Json.createObjectBuilder().add("titulo", carrinhoItem.getLivro().getTitulo())
					.add("preco", carrinhoItem.getLivro().getPreco()).add("quantidade", carrinhoItem.getQuantidade())
					.add("total", getPrecoPorItem(carrinhoItem)));
		}
		return jsonArrayBuilder.build().toString();
	}

}
