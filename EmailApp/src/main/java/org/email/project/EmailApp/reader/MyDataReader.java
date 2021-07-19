package org.email.project.EmailApp.reader;

import java.util.Set;

abstract public class MyDataReader<T> {
	abstract Set<String> read(T dataSource);
}
