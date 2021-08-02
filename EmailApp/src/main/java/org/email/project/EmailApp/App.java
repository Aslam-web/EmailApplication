package org.email.project.EmailApp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.email.project.EmailApp.localdb.CollectionData;
import org.email.project.EmailApp.reader.TextFileReader;

public class App {
	
	private static Properties props = new Properties();
	
	static {
		try {
			props.load(new FileInputStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String... args) throws Exception {
		String email = props.get("MY_ACCOUNT_EMAIL").toString();
		String pass = props.get("MY_ACCOUNT_PASSWORD").toString();
		
		BulkEmailController bController = new BulkEmailController(email, pass);
		
		bController.setThreadCount(4);
		bController.sendBulkMail(CollectionData.get());
		
//		bController.sendBulkMail(new TextFileReader().read("data/email100.txt"));
	}
}
