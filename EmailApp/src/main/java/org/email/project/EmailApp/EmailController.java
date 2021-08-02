package org.email.project.EmailApp;

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
	private RecipientDetails recipientDetails;
	private String fromEmail;
	private String password;
	
	public EmailController(String fromEmail, String password) {
		this.fromEmail = fromEmail;
		this.password = password;
	}

	public boolean sendMail(String recipient){
		Properties props = new Properties();

		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail,password);
			}
		});
		
		return connect(recipient);
	}

	private boolean connect(String recipient) {
		
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

	private Message createMessage(String recipient) {
		
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress());
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			message.setSubject("Generated email through properties file");
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














