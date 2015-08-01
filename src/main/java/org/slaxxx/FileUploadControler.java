package org.slaxxx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadControler {

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public @ResponseBody String provideUploadInfo() {
		return "You can upload files by posting to this URL.";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody String handleFileUpload(HttpServletResponse response,
			@RequestParam("file") MultipartFile file) {
		try {
			if (null == file) {
				throw new Exception("Files looking fishy");
			}
			String folder = Utils.getCurrentTimestamp() + "";
			File dest = new File(folder);
			boolean success = dest.mkdir();
			if (!success) {
				throw new Exception("Can´t create folder");
			}

			File writtenFile = writeFileToHdd(file, dest);
			UnzipService.unzip(writtenFile);
			File pboFile = PboCreatorService.createPboFromFolder(dest);
			startDownload(response, pboFile);

		} catch (Exception ex) {
			return ex.getMessage();
		}
		return "File uploaded successful";
	}

	private void startDownload(HttpServletResponse response, File file)
			throws Exception {
		byte[] bytes = Utils.fileToByteArray(file);
		response.reset();
		response.setContentType("application/octet-stream");
		response.setContentLength(bytes.length);
		response.setHeader("Content-Disposition",
				"attachment; filename=\""+file.getName()+"\"");
		OutputStream outStream = response.getOutputStream();
		outStream.write(bytes);
	}

	private File writeFileToHdd(MultipartFile file, File destination)
			throws Exception {
		File destFile = new File(destination.getPath() + "//"
				+ file.getOriginalFilename());
		try (FileOutputStream stream = new FileOutputStream(destFile)) {

			byte[] bytes = file.getBytes();
			stream.write(bytes);

		} catch (Exception ex) {
			throw new Exception("Something went horribly wrong :(!");
		}
		return destFile;
	}

}
