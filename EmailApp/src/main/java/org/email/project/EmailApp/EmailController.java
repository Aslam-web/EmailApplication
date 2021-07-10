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

public class EmailController {

	private Session session;
	private SenderEmail senderEmail;
	
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
				return new PasswordAuthentication(SenderEmail.USERNAME, SenderEmail.PASSWORD);
			}
		});
		
		return connect(recipient);
	}

	private boolean connect(InternetAddress recipient) {
		
		senderEmail = new SenderEmail(recipient);
		
		try {
			
			Message message = createMessage(recipient);
			Transport.send(message);
			System.out.println("MESSAGE SENT SUCCESSFULLY to "+senderEmail.getRecieverName()+" !!!");
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
			message.setFrom(new InternetAddress(SenderEmail.USERNAME));
			message.setRecipient(Message.RecipientType.TO, recipient);
			message.setSubject("Testing main from hit21009 to wasa");
			message.setText(senderEmail.getMessage());
			System.out.println("MESSAGE GENERATED for "+senderEmail.getRecieverName()+" !!!");
			return message;
		} catch (Exception e) {
			System.out.println("SOME ERROR OCCURED !!!");
			e.printStackTrace();
		}
		
		return message;
	}

}














