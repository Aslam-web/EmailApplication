package org.email.project.EmailApp;

import javax.mail.internet.InternetAddress;

public class RecipientDetails {
	
	private String recieverName;
	private String message;

	public RecipientDetails(InternetAddress email) {
		this.recieverName = email.toString().split("@")[0];
		this.message = "Dear Mr. " + this.recieverName + ",\n" + "\tGreetings to you. I hope you are at the best of your health. "
				+ "Welcome to my GitHub account - https://github.com/Aslam-web/EmailApplication\n\n" + "Thanks and Regards\n"
				+ "Mr. Aslam";
	}

	public String getRecieverName() {
		return recieverName;
	}

	public String getMessage() {
		return message;
	}
}
