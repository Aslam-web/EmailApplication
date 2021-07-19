package org.email.project.EmailApp;

import javax.mail.internet.InternetAddress;

public class RecipientDetails {
	
	private String recieverName;
	private String message;

	public RecipientDetails(InternetAddress email) {
		this.recieverName = email.toString().split("@")[0];
		this.message = "Dear Mr. " + this.recieverName + ",\n"
				+ "\tGreetings to you. I hope you are at the best of your health. "
				+ "\nWelcome to my GitHub account - https://github.com/Aslam-web/EmailApplication"
				
				+ "\n\n\nThanks & Regards"
				+ "\nMr M.N Aslam,"
				+ "\nJAVA developer Trainer,"
				+ "\nHaaris Infotech Institutions,"
				+ "\nEmail" + ":aslam1qqqq@gmail.com,"
				+ "\nPhone: +91 63799 71782.";
	}

	public String getRecieverName() {
		return recieverName;
	}

	public String getMessage() {
		return message;
	}
}
