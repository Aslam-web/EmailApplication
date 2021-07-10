package org.email.project.EmailApp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.email.project.EmailApp.reader.TextFileReader;

public class App {
	
	private static final String path = "C:\\Users\\Syed\\git\\repositoryHitProject\\EmailApp\\src\\main\\java\\org\\email\\project\\EmailApp\\db\\";
	private static final String filename = path + "email3.txt";

	public static void main(String... args) throws Exception {

		BulkEmailController bController = new BulkEmailController();
		// provide HashSet or ArrayList
		Collection<String> emailSet = new TextFileReader(filename).read(new ArrayList<String>()); 

		bController.sendMail(emailSet);
		
	}
}
