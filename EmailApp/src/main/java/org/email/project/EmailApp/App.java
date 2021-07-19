package org.email.project.EmailApp;

import java.util.Iterator;

import org.email.project.EmailApp.localdb.CollectionData;
import org.email.project.EmailApp.reader.TextFileReader;

import io.github.cdimascio.dotenv.Dotenv;

public class App {

	private static final String path = System.getProperty("user.dir")
			+ "//src/main/java/org/email/project/EmailApp/localdb/";
	private static final String filename = path + "email4.txt";

	public static void main(String... args) throws Exception {

//		BulkEmailController bController = new BulkEmailController("sample@gmail.com","pass****");

		BulkEmailController bController = new BulkEmailController("aslamhit21009@gmail.com","hit21009");
		

//		Iterator<String> iter = CollectionData.get().iterator();
//		int i = 1;
//		while (iter.hasNext()) {
//			System.out.println(i++ +" "+iter.next());
//		}
		
		bController.sendBulkMail(CollectionData.get());
		
	}
}
