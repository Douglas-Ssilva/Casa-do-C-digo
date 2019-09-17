package br.com.casadocodigo.util;

import java.security.MessageDigest;

import org.jboss.security.Base64Encoder; //Usando a class do JBos, JAAS nao é completo nesse sentido.

public class PasswordGenerated {
	
	public static void main(String[] args) {
		System.out.println(encoding("123"));
	}
	
	public static String encoding(String pass) {
		try {
			byte[] digest = MessageDigest.getInstance("sha-256").digest(pass.getBytes()); //algoritmo de hash, baseado em 256 caracteres
			return Base64Encoder.encode(digest);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
