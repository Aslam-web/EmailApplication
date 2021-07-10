package org.email.project.EmailApp;

import java.util.Collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class BulkEmailController {
	
	private Session session;
	private SenderEmail senderEmail;
	private static int count=1;
	
	public boolean sendMail(Collection<String> recipients) {
		
		// writing the neccessary properties to be included for the session using map
		Map<String, String> m = new HashMap<>();

		m.put("mail.smtp.auth", "true");
		m.put("mail.smtp.starttls.enable", "true");
		m.put("mail.smtp.host", "smtp.gmail.com");
		m.put("mail.smtp.port", "587");

		// session doesnt accept a HashMap as a parameter in place of Properties
		// hence using the putAll() method of Properties, an inherited method from HashTable.
		// putAll() accepts any Map implementation as a parameter, hence replacing the Properties with HashMap. 
		Properties properties = new Properties();
		properties.putAll(m);

		session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SenderEmail.USERNAME, SenderEmail.PASSWORD);
			}
		});
		
		
		
		// convert the Set<String> to InternetAddress[] array
		InternetAddress[] rAddresses = new InternetAddress[recipients.size()];
		Iterator<String> emailIter = recipients.iterator();
		
		int i=0;
		while(emailIter.hasNext()) {
			try {
				rAddresses[i] = new InternetAddress(emailIter.next());
				i++;
			} catch (AddressException e) {
				System.out.println("Invalid email address at line "+i+"\n lastly added email: "+rAddresses[i]);
				e.printStackTrace();
			}
		}
		
		System.out.println("Preparing the messages ...");
		for(InternetAddress address:rAddresses) {
			System.out.print(count++ +".)");
			connect(address);			// sending message one by one
		}
		System.out.println("Message sent to everyone ...");
		return true;
	}

	// connect() is responsible for creating(via createMessage() method) and sending message
	private boolean connect(InternetAddress recipient) {
		
		senderEmail = new SenderEmail(recipient);
		
		try {

			Message message = createMessage(recipient);
			Transport.send(message);
//			System.out.println(count+".) MESSAGE SENT SUCCESSFULLY to "+senderEmail.getRecieverName());
			System.out.println(" MESSAGE SENT SUCCESSFULLY to "+senderEmail.getRecieverName());
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}


	// createMessage() is responsible for creating the message body, determining the recipients and returning it to the caller
	private Message createMessage(InternetAddress recipient) {

		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(SenderEmail.USERNAME));
			message.setRecipient(Message.RecipientType.TO, recipient);
			message.setSubject("Seperate object creation for each message");
			message.setText(senderEmail.getMessage());
//			System.out.println("MESSAGE GENERATED for "+senderEmail.getRecieverName()+" !!!");
			return message;
		} catch (Exception e) {
			System.out.println("SOME ERROR OCCURED !!!");
			e.printStackTrace();
		}

		return message;
	}
}
