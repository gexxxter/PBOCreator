package org.slaxxx;

import java.io.File;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class UnzipService {

	public static void unzip(File file) throws Exception {
		try {
			ZipFile zipFile = new ZipFile(file);
			System.out.println("Unzipping to: "+file.getAbsolutePath()+"\\unzipped");
			zipFile.extractAll(file.getParent());
			Utils.deleteFile(file);
		} catch (ZipException e) {
			throw new Exception("Error while unzipping!");
		}
	}

}
