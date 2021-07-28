package org.email.project.EmailApp;

import java.util.ArrayList;
import java.util.List;
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
	private List<InternetAddress> rAddresses;

	private int count;
	private int failedMessages;
	private String status = "SUCCESS";

	public BulkEmailController(String myGmail, String myPassword) {
		try {
			this.myGmail = new InternetAddress(myGmail);
		} catch (AddressException e) {
			System.out.println("Invalid Email Address");
		}
		this.myPassword = myPassword;
		this.rAddresses = new ArrayList<>();
	}

	// setRecipients()
	public boolean sendBulkMail(Set<String> recipients) {

		// writing the neccessary properties to be included for the session using map
		Properties props = new Properties();

		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myGmail.toString(), myPassword);
			}
		});

		// coverting the String of addresses to InternetAddress
		covertToAddress(recipients);
		
		// starts the process using the given amount of threads
		startThreadOperation(4);

		return true;
	}

	private void startThreadOperation(int threadCount) {

		System.out.println("Preparing the messages ...\nTotal no.of reciepients: "+rAddresses.size());
		System.out.println("\n----------------------------------------------");
		long startTime = System.currentTimeMillis();

		Thread[] threads = new Thread[threadCount];

		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new MyRunnable());
			threads[i].start();
		}
		
		for (Thread thread : threads) {
		    try {thread.join();} catch (InterruptedException e) {e.printStackTrace();}
		}

		System.out.println("----------------------------------------------\n");
		System.out.printf("Total recipients: %s\nSent : %s,\tFailed : %s,\tStatus : %s\nTime taken : %ds\nNo.Of Threads used : %d", 
				rAddresses.size(), count-failedMessages, failedMessages, status,
				(System.currentTimeMillis() - startTime)/1000, threadCount);
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

	// connect() is responsible for creating [i.e via createMessage()] and sending
	// message
	private boolean send(InternetAddress recipient) {

		// convenience class
		recipientDetails = new RecipientDetails(recipient);

		try {

			Message message = createMessage(recipient);
			Transport.send(message);
			System.out.printf("Message successfully sent to <%s>\n",recipient);
		} catch (MessagingException e) {
			System.out.printf("Failed to send message to : <%s>",recipient);
			System.out.println("\tProblem : " + e.getMessage());
			failedMessages++;
			this.status = "OK";
			return false;
		}

		return true;
	}

	// creates the message body
	private Message createMessage(InternetAddress recipient) {

		Message message = new MimeMessage(session);
		try {
			message.setFrom(myGmail);
			message.setRecipient(Message.RecipientType.TO, recipient);
			message.setSubject("Using Multi Threading to send Bulk Mail");
			message.setText(recipientDetails.getMessage());
			return message;
		} catch (Exception e) {
			System.out.printf("SOME ERROR OCCURED IN CREATING EMAIL FOR %s!!!", recipient);
			e.printStackTrace();
		}

		return message;
	}

	private class MyRunnable implements Runnable {
		
		@Override
		public void run() {
			for (; count < rAddresses.size();) {

				int localCount = 0;
				synchronized (this) {
					localCount = count;
					count++;
				}
				try {
					Thread.sleep(100);
				} catch (Exception e) {
				}
				send(rAddresses.get(localCount));

			}
		}
	}
}