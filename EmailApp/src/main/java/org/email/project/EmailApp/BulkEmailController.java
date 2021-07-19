package org.email.project.EmailApp;

import java.util.HashMap;
import java.util.HashSet;
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

	private InternetAddress myGmail;
	private String myPassword;
	private Session session;
	private RecipientDetails recipientDetails;
	private Set<InternetAddress> rAddresses;

	private static int count = 1;

	public BulkEmailController(String myGmail, String myPassword) {
		try {
			this.myGmail = new InternetAddress(myGmail);
		} catch (AddressException e) {
			System.out.println("Invalid Email Address");
		}
		this.myPassword = myPassword;
		this.rAddresses = new HashSet<>();
	}

	// setRecipients()
	public boolean sendBulkMail(Set<String> recipients) {

		// writing the neccessary properties to be included for the session using map
		Map<String, String> m = new HashMap<>();

		m.put("mail.smtp.auth", "true");
		m.put("mail.smtp.starttls.enable", "true");
		m.put("mail.smtp.host", "smtp.gmail.com");
		m.put("mail.smtp.port", "587");

		// session doesn't accept a HashMap as a parameter in place of Properties
		// hence using the putAll() method of Properties, an inherited method from
		// HashTable.
		// putAll() accepts any Map implementation as a parameter, hence replacing the
		// Properties with HashMap.
		Properties properties = new Properties();
		properties.putAll(m);

		session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myGmail.toString(), myPassword);
			}
		});

		// coverting the String of addresses to InternetAddress
		covertToAddress(recipients);

		System.out.println("Preparing the messages ...");

		rAddresses.forEach((r) -> connect(r));

		System.out.println("Finished processing !!!");

//		new Thread(() -> testThread()).start();
//		new Thread(() -> testThread()).start();

		return true;
	}

	private void covertToAddress(Set<String> recipients) {

		recipients.forEach((r) -> {
			try {
				rAddresses.add(new InternetAddress(r));
			} catch (AddressException e) {
				System.out.println("Invalid Address detected !!!\nAddress : " + r);
			}
		});
	}

	// connect() is responsible for creating(via createMessage() method) and sending
	// message
	private boolean connect(InternetAddress recipient) {

		recipientDetails = new RecipientDetails(recipient);

		try {

			Message message = createMessage(recipient);
			Transport.send(message);
			System.out
					.println(count++ + ".) MESSAGE SENT SUCCESSFULLY TO: <" + recipient + ">");
			return true;
		} catch (MessagingException e) {
			System.out.print("MESSAGE SENDING FAILED TO: <" + recipient + ">");
			System.out.println("\tProblem : " + e.getMessage());
			return false;
		}
	}

	// creates the message body
	private Message createMessage(InternetAddress recipient) {

		Message message = new MimeMessage(session);
		try {
			message.setFrom(myGmail);
			message.setRecipient(Message.RecipientType.TO, recipient);
			message.setSubject("Sending bulk email from JAVA program");
			message.setText(recipientDetails.getMessage());
			return message;
		} catch (Exception e) {
			System.out.printf("SOME ERROR OCCURED IN CREATING EMAIL FOR %s!!!", recipient);
			e.printStackTrace();
		}

		return message;
	}
}

//private void testThread() {
//
//		rAddresses.forEach((r) -> {
//			synchronized (r) {
//			connect(r);
//			}
//		});
//
//		System.out.println("Finished processing !!!");
//}