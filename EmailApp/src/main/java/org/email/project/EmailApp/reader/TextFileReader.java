package org.email.project.EmailApp.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

public class TextFileReader extends MyFileReader {
	private String filename;

	public TextFileReader(String filename) {
		this.filename = filename;
	}

	@Override
	public Collection<String> read(Collection<String> collection) {

		try (BufferedReader br = new BufferedReader(new FileReader(this.filename))) {
			for (String line; (line = br.readLine()) != null;) {
				collection.add(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return collection;
	}
}
