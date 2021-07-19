package org.email.project.EmailApp;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import io.github.cdimascio.dotenv.Dotenv;

public class EmailController {

	private Session session;
	private RecipientDetails recipientDetails;
	
	public boolean sendMail(InternetAddress recipient){
		Map<String, String> m = new HashMap<>();

		m.put("mail.smtp.auth", "true");
		m.put("mail.smtp.starttls.enable", "true");
		m.put("mail.smtp.host", "smtp.gmail.com");
		m.put("mail.smtp.port", "587");

		Properties properties=new Properties();
		properties.putAll(m);
//		Session session = Session.getInstance((Properties) properties, new Authenticator() {
		session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(Dotenv.load().get("MY_ACCOUNT_EMAIL"), 
												  Dotenv.load().get("MY_ACCOUNT_PASSWORD"));
			}
		});
		
		return connect(recipient);
	}

	private boolean connect(InternetAddress recipient) {
		
		recipientDetails = new RecipientDetails(recipient);
		
		try {
			
			Message message = createMessage(recipient);
			Transport.send(message);
			System.out.println("MESSAGE SENT SUCCESSFULLY to "+recipientDetails.getRecieverName()+" !!!");
			return true;
		} catch (MessagingException e) {
			
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		} 
	}

	private Message createMessage(InternetAddress recipient) {
		
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(Dotenv.load().get("MY_ACCOUNT_EMAIL")));
			message.setRecipient(Message.RecipientType.TO, recipient);
			message.setSubject("Sample Email from Java Application");
			message.setText(recipientDetails.getMessage());
			System.out.println("MESSAGE GENERATED for "+recipientDetails.getRecieverName()+" !!!");
			return message;
		} catch (Exception e) {
			System.out.println("SOME ERROR OCCURED !!!");
			e.printStackTrace();
		}
		
		return message;
	}

}














