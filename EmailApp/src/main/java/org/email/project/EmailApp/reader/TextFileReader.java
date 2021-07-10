package org.email.project.EmailApp.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TextFileReader implements MyFileReader {
	private String filename;

	public TextFileReader(String filename) {
		this.filename = filename;
	}

	@Override
	public Set<String> read() {
		Set<String> emailSet = new HashSet<>();

		try (BufferedReader br = new BufferedReader(new FileReader(this.filename))) {
			for (String line; (line = br.readLine()) != null;) {
				emailSet.add(line);
//				System.out.println(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return emailSet;
	}
}
