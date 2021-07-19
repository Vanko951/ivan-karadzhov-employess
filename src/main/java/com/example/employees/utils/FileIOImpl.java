package com.example.employees.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileIOImpl implements FileIO {
	private static final Logger LOG = LoggerFactory.getLogger(FileIOImpl.class);
	
	@Override
	public List<String> read(InputStream file) {
        List<String> content = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    content.add(line);
                }
            }
        } catch (IOException e) {
            LOG.warn("Error reading data from file.", e);
        }

        return content;
    }
}
