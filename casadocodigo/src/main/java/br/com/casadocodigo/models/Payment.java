package br.com.casadocodigo.models;

import java.math.BigDecimal;

public class Payment {
	
	private BigDecimal value;

	public Payment(BigDecimal total) {
		value = total;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
}
