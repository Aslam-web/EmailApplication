package org.email.project.EmailApp;

import org.email.project.EmailApp.localdb.CollectionData;
import org.email.project.EmailApp.reader.TextFileReader;

import io.github.cdimascio.dotenv.Dotenv;

public class App {

	public static void main(String... args) throws Exception {

//		BulkEmailController bController = new BulkEmailController("sample@gmail.com","pass****");
		BulkEmailController bController = new BulkEmailController(Dotenv.load().get("MY_ACCOUNT_EMAIL"),
													Dotenv.load().get("MY_ACCOUNT_PASSWORD"));

		bController.sendBulkMail(CollectionData.get());
//		bController.sendBulkMail(new TextFileReader().read("data/email100.txt"));
	}
}
