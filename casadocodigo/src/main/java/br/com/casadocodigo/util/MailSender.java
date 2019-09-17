package br.com.casadocodigo.util;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@ApplicationScoped //pois será injetado no paymentService
public class MailSender {
	
	@Resource(mappedName="java:jboss/mail/gmail") //Já procura todas config do jndi, e injeta aqui p gente. @inject ainda nao tem essa integração
	private Session session;

	public void send(String from, String to, String subject, String body) {
		MimeMessage mimeMessage= new MimeMessage(session);
		
		try {
			mimeMessage.setRecipients(RecipientType.TO, InternetAddress.parse(to));
			mimeMessage.setFrom(new InternetAddress(from));
			mimeMessage.setSubject(subject);
			mimeMessage.setContent(body, "text/html");
			
			Transport.send(mimeMessage); //É preciso desativar o antivirus p que o email chegue
		} catch (MessagingException e) {
			throw new RuntimeException(e); //sempre lançar a exception p cima, assim ela  será vista por alguem
		}
	}

}
