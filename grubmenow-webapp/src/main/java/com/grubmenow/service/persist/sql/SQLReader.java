package com.grubmenow.service.persist.sql;

import java.io.File;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.text.StrSubstitutor;

public class SQLReader {

	private static String loadTemplate(String file) {
		try {
			return FileUtils.readFileToString(new File(SQLReader.class.getResource(file).getFile()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String loadSQL(String file, Map<String, String> tokens) {
		String template = loadTemplate(file);
		return replaceTokens(template, tokens);
	}

	private static String replaceTokens(String template,
			Map<String, String> tokens) {
		return StrSubstitutor.replace(template, tokens);
	}
}
