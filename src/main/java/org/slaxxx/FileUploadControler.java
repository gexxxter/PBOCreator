package org.slaxxx;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

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
		if (!file.isEmpty()) {
			try (FileOutputStream stream = new FileOutputStream(
					file.getOriginalFilename())) {
				byte[] bytes = file.getBytes();
				stream.write(bytes);
				//Write to hdd done
				startDownload(response, bytes);
			} catch (Exception ex) {
				return "Something went horribly wrong :(!";
			}
		}
		return "File " + file.getOriginalFilename() + " uploaded successful";
	}

	private void startDownload(HttpServletResponse response, byte[] bytes)
			throws IOException {
		response.reset();
		response.setContentType("application/octet-stream");
		response.setContentLength(bytes.length);
		
		OutputStream outStream = response.getOutputStream();
		outStream.write(bytes);
	}

}
