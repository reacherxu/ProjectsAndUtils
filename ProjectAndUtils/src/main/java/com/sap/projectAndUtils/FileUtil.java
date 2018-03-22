package com.sap.projectAndUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {

	private final static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * 
	 * @param content
	 *            content
	 * @param path
	 *            file path
	 */
	public static void writeFile(String content, String path) {
		BufferedWriter writer = null;

		try {
			// check if file exists
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}

			// write file
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(content);
			writer.flush();
		} catch (IOException e) {
			logger.error(e.getStackTrace().toString());
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				logger.error("Error in closing the BufferedWritter" + e.getStackTrace().toString());
			}
		}
	}
}
