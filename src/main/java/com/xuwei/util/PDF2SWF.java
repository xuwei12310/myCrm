package com.xuwei.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

/**
 * PDF转SWF工具
 * 
 * @date 2012-11-5
 * @author xhw
 * 
 */
public class PDF2SWF {
	/**
	 * 获得文件的路径
	 * 
     * @param file
     *            文件的路径 ,如："c:/test/test.swf"
     * @return 文件的路径
     */
	private static String getFilePath(String file) {
        String result = file.substring(0, file.lastIndexOf("/"));
        if (file.substring(2, 3) == "/") {
            result = file.substring(0, file.lastIndexOf("/"));
        } else if (file.substring(2, 3) == "\\") {
            result = file.substring(0, file.lastIndexOf("\\"));
        }
        return result;
    }

    /**
     * 新建一个目录
     * 
     * @param folderPath
     *            新建目录的路径 如："c:\\newFolder"
     */
	private static void newFolder(String folderPath) {
        try {
            File myFolderPath = new File(folderPath.toString());
            if (!myFolderPath.exists()) {
                myFolderPath.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * the exit value of the subprocess represented by this Process object. By
     * convention, the value 0 indicates normal termination.
     * 
     * @param sourcePath
     *            pdf文件路径 ，如："c:/hello.pdf"
     * @param destPath
     *            swf文件路径,如："c:/test/test.swf"
     * @return 正常情况下返回：0，失败情况返回：1
     * @throws IOException
     */
	private static int convertPDF2SWF(String sourcePath, String destPath) throws IOException {
        // 如果目标文件的路径是新的，则新建路径
        newFolder(getFilePath(destPath));
        // 源文件不存在则返回
        File source = new File(sourcePath);
        if (!source.exists()) {
            return 0;
        }
        // 调用pdf2swf命令进行转换
        String command = null;
        //判断操作系统
        String osName = System.getProperty("os.name");
        String SWFToolsHome = CommonUtil.getConfig("SWFTools.HOME");
        String SWFTools_LINUX_LANGUAGE_HOME = CommonUtil.getConfig("SWFTools.LINUX_LANGUAGE_HOME");
        if (Pattern.matches("Linux.*", osName)) {
            command = SWFToolsHome + "/pdf2swf " + sourcePath + " -o " + destPath + " -T 9 -f " + "-s languagedir="+SWFTools_LINUX_LANGUAGE_HOME;
        } else if (Pattern.matches("Windows.*", osName)) {
            command = SWFToolsHome + "/pdf2swf.exe \"" + sourcePath + "\" -o \"" + destPath + "\" -T 9 -f ";
        } 
        // 调用外部程序
        Process process = Runtime.getRuntime().exec(command);
        final InputStream is1 = process.getInputStream();
        InputStream is2 = process.getErrorStream();
		try {
			new Thread(new Runnable() {
			    public void run() {
			        BufferedReader br = new BufferedReader(new InputStreamReader(is1));
			        try {
			            while (br.readLine() != null);
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
			    }
			}).start(); // 启动单独的线程来清空process.getInputStream()的缓冲区
			BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
			// 保存输出结果流
			StringBuilder buf = new StringBuilder();
			String line = null;
			while ((line = br2.readLine()) != null){
				// 循环等待ffmpeg进程结束
			    buf.append(line);
			}
			while (br2.readLine() != null);
			try {
			    process.waitFor();
			} catch (InterruptedException e) {
			    e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return process.exitValue();
    }

    /**
     * pdf文件转换为swf文件操作
     * 
     * @param sourcePath
     *            pdf文件路径 ，如："c:/hello.pdf"
     * @param destPath
     *            swf文件路径,如："c:/test/test.swf"
     */
    public static void pdf2swf(String sourcePath, String destPath) {
        try {
            PDF2SWF.convertPDF2SWF(sourcePath, destPath);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
