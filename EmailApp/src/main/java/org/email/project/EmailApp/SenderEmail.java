package org.email.project.EmailApp;

import javax.mail.internet.InternetAddress;

// SendingEmailDetails, SendEmailDetails, EmailSendDetails
final public class SenderEmail {
	public static final String USERNAME = "aslamhit21009@gmail.com";
	public static final String PASSWORD = "hit21009";

	// private
	private String recieverName;
	private String message;

	public SenderEmail(InternetAddress email) {
		this.recieverName = email.toString().split("@")[0];
		this.message = "Dear Mr. " + this.recieverName + ",\n" + "\tGreetings to you. I hope you are at the best of your health. "
				+ "Welcome to my GitHub account - https://github.com/Aslam-web\n\n" + "Thanks and Regards\n"
				+ "Mr. Aslam";
	}

	public String getRecieverName() {
		return recieverName;
	}

	public String getMessage() {
		return message;
	}

}
