package com.example.employees.utils;

import java.io.InputStream;
import java.util.List;

public interface FileIO {
	List<String> read(InputStream file);
}
