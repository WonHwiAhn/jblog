package com.cafe24.jblog.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.jblog.repository.UserDAO;

@Service
public class FileUploadService {
	
	@Autowired
	private UserDAO userDAO;
	
	private static String SAVE_PATH = "/uploads";
	private static String PREFIX_URL = "/uploads/images/";
	
	public String restore(MultipartFile multipartFile, Long no) {
		String url = "";
		
		try {
			String originFilename = multipartFile.getOriginalFilename();
			
			System.out.println("originFilename ==> " + originFilename);
			
			if(!"".equals(originFilename)) {
				String extName = 
						originFilename.substring(originFilename.lastIndexOf("."), originFilename.length());
				
				Long size = multipartFile.getSize();
				String saveFilename = genSaveFilename(extName);
				
				System.out.println("##########" + originFilename);
				System.out.println("##########" + extName);
				System.out.println("##########" + size);
				System.out.println("##########" + saveFilename);
				
				writeFile(multipartFile, saveFilename);
				
				url = PREFIX_URL + saveFilename;
				
				
			}else {
				url = userDAO.getUrl(no);
			}
		}catch(IOException ex) {
			throw new RuntimeException(ex);
		}
		
		return url;
	}
	
	private void writeFile(MultipartFile multipartFile, String saveFilename) throws IOException {
		byte[] fileData = multipartFile.getBytes();
		FileOutputStream fos = 
				new FileOutputStream(SAVE_PATH  + File.separator + saveFilename);
		
		fos.write(fileData);
		fos.close();
	}
	
	private String genSaveFilename(String extName) {
		String filename = "";
		Calendar calendar = Calendar.getInstance();
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH+1);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += extName;
		
		return filename;
	}
}
