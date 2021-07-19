package org.email.project.EmailApp.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TextFileReader extends MyDataReader<String> {

	@Override
	public Set<String> read(String filename) {
		Set<String> set = new HashSet<String>();
		
		System.out.println("READING DATABASE !!!");
		try {Thread.sleep(1000);} catch (Exception e1) {}

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			for (String line; (line = br.readLine()) != null;) {
				set.add(line);
			}
			
			System.out.println("DATABASE READ SUCCESSFULLY !!!");
			try {Thread.sleep(1000);} catch (Exception e1) {}

		} catch (IOException e) {
			System.out.println("ERROR READING DATABASE !!!");
			e.printStackTrace();
		}

		return set;
	}
}
