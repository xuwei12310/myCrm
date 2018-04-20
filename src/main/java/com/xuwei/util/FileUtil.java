package com.xuwei.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

/**
 * @Description: 文件处理类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-2-1
 * @author lys
 * @vesion 1.0
 */
public class FileUtil {
	
	/**
	 * @description: 下载文件
	 * @createTime: 2016年9月30日 下午2:08:43
	 * @author: wsh
	 * @param response
	 * @param filePath
	 * @param fileName
	 */
	public static void downloadFile(HttpServletResponse response,
			String filePath, String fileName) {
		
		InputStream is = null;
		OutputStream os = null;
		BufferedOutputStream bos = null;
		File file = new File(filePath);

		try {
			if(file.exists()){
				response.setContentType("APPLICATION/OCTET-STREAM;charset=UTF-8");
				fileName = fileName.replace(",", "，");
				String formatFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename="+ formatFileName);
				
//				response.setHeader("Cache-Control","no-cache");

				is = new BufferedInputStream(new FileInputStream(file));
				os = response.getOutputStream();
				bos = new BufferedOutputStream(os);

				byte[] buffer = new byte[1024];

				int len = 0;

				while (-1 != (len = is.read(buffer))) {
					bos.write(buffer, 0, len);

				}
				bos.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
				if (os != null) {
					os.close();
				}
				if (is != null) {
					is.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	/**
	 * @Description: 删除文件
	 * @Created Time: 2013-2-21 下午2:11:09
	 * @Author lys
	 * @param filePath
	 */
	public static void deleteFile(String filePath){
		File file = new File(filePath);
		if(file.exists()){
			FileUtils.deleteQuietly(file);
		}
	}
	
	public static void saveFileNew(InputStream source, File dest) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new BufferedInputStream(source);

			os = new BufferedOutputStream(new FileOutputStream(dest));
			
			int len = 0;

			byte[] buffer = new byte[1024];

			while (-1 != (len = is.read(buffer))) {
				os.write(buffer, 0, len);

			}
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
