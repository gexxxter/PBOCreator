package org.slaxxx;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

public class Utils {
	public static long getCurrentTimestamp() {
		Date date = new Date();
		return date.getTime();
	}

	public static void deleteFile(File file) {
		file.delete();
	}

	public static byte[] fileToByteArray(File file) throws Exception {
		FileInputStream fileInputStream = null;

		byte[] bFile = new byte[(int) file.length()];

		try {
			// convert file into array of bytes
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();

			System.out.println("Done");
		} catch (Exception e) {
			throw new Exception("Error while converting pbo to byte array");
		}
		return bFile;
	}
}
