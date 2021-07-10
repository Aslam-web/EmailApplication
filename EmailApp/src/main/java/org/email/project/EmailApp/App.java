package org.email.project.EmailApp;

import java.util.Set;

import org.email.project.EmailApp.reader.TextFileReader;

// implement clonable later since the new object gets created everytime
public class App {
	
	private static final String singleFilename = "./src/main/java/org/email/project/EmailApp/db/SingleEmail.txt";
	private static final String bulkFilename = "./src/main/java/org/email/project/EmailApp/db/BulkEmail.txt";
	private static final String megaFilename = "./src/main/java/org/email/project/EmailApp/db/MegaEmail.txt";
	
	public static void main(String... args) {
//		EmailController eController = new EmailController();
//		eController.sendMail("wasa.wasa.in@gmail.com");
//		
		BulkEmailController bController = new BulkEmailController();
		TextFileReader textFileReader = new TextFileReader(megaFilename);
		Set<String> emailSet = textFileReader.read();
		bController.sendMail(emailSet);
		
		
	}
}
