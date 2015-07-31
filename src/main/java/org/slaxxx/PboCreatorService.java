package org.slaxxx;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class PboCreatorService {
	private static String MAKE_PBO_COMMAND = "MakePbo.exe -E -P ";

	public static File createPboFromFolder(File file) throws Exception {
		if (!file.isDirectory()) {
			throw new Exception("Internal error, better start crying!");
		}

		File[] listFiles = file.listFiles();
		if (listFiles.length != 1) {
			throw new Exception(
					"Please zip the parent folder not the subfolders");
		}

		return createPbo(listFiles);

	}

	private static File createPbo(File[] listFiles) throws IOException,
			InterruptedException, Exception {
		System.out.println(MAKE_PBO_COMMAND + listFiles[0].getParentFile()
				+ "/" + listFiles[0].getName());

		Process process = Runtime.getRuntime().exec(
				MAKE_PBO_COMMAND + listFiles[0].getParentFile() + "/"
						+ listFiles[0].getName());

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				process.getInputStream()));
		StringBuilder sb = new StringBuilder();
		while ((reader.readLine()) != null) {
			sb.append(reader.readLine());
		}
		process.waitFor();
		if (process.exitValue() != 0) {
			throw new Exception("Error while creating the pbo:\n"+sb.toString());
		}
		
		File pboFile = new File(listFiles[0].getParentFile() + "/"
				+ listFiles[0].getName()+".pbo");
		if(!pboFile.isFile()){
			throw new Exception("PBO file is fishy, contact SlaxXx");
		}
		return pboFile;
	}
}
