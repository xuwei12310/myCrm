package com.xuwei.util;

import java.io.File;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.ExternalOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

/**
 * 这是一个工具类，主要是为了使Office2003-2007全部格式的文档(.doc|.docx|.xls|.xlsx|.ppt|.pptx)
 * 转化为pdf文件<br>
 * Office2010的没测试<br>
 * 
 * @date 2012-11-5
 * @author xhw
 * 
 */
public class Office2PDF {
    /**
     * 
     * @Description: 转换文件 
     * @Create: 2014-2-24 下午04:10:53
     * @author yk
     * @update logs
     * @param inputFilePath
     * @param outputFilePath
     * @param isServiceRunning
     */
    public static void office2pdf(String inputFilePath, String outputFilePath) {
    	//配置
    	ExternalOfficeManagerConfiguration config = new ExternalOfficeManagerConfiguration();
    	config.setPortNumber(8100);
    	//根据配置获得已启动的服务
    	OfficeManager officeManager = config.buildOfficeManager();
        //转换器
        OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
        if (null!=inputFilePath && null!=outputFilePath) {
        	File inputFile = new File(inputFilePath);
            if (inputFile.exists()) {
            	File outputFile = new File(outputFilePath);
                if (!outputFile.getParentFile().exists()) {
                    outputFile.getParentFile().mkdirs();
                }
                //转换
                converter.convert(inputFile, outputFile);
            }
        }
    }
}