package com.xuwei.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.Attach;
import com.xuwei.mapper.AttachMapper;
import com.xuwei.service.IAttachService;
import com.xuwei.util.CommonUtil;
import com.xuwei.util.FileUtil;
import com.xuwei.util.Office2PDF;
import com.xuwei.util.PDF2SWF;

/**
 * @description: 系统_附件服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年06月30日 11:46:47
 * @author: lys
 * @version: 1.0
 */
@Service
public class AttachServiceImpl extends ServiceImpl<AttachMapper, Attach> implements IAttachService {
	@Resource
	private AttachMapper attachMapper;

	/**
	 * 根据id获取附件信息（微信端）
	 */
	@Override
	public List<Attach> getAttachList(Long attachId) {
		return attachMapper.getAttachList(attachId);
	}

	/**
	 * 生成SWF
	 */
	@Override
	public String createSWF(String fileBasePath, String attachName) {
		String msg = null;
		String basePath = CommonUtil.getAppProParam("project_files.base.path");
		String extention = FilenameUtils.getExtension(attachName).toLowerCase();
		String title = attachName.substring(0, attachName.indexOf("."+extention));
		String sourceFilePath = basePath+fileBasePath;
		File sourceFile = new File(sourceFilePath);
		if(!sourceFile.exists()){
			msg = "文件已不存在";
		}else if("pdf".equals(extention)){//pdf文件
			String swfFilePath = basePath+fileBasePath.replace("."+extention, ".swf");
			File swfFile = new File(swfFilePath);
			if(!swfFile.exists()){
				PDF2SWF.pdf2swf(sourceFilePath, swfFilePath);
			}
		}else if("doc,docx,xls,xlsx,ppt,pptx".indexOf(extention)!=-1){//office文件
			String swfFilePath = basePath+fileBasePath.replace("."+extention, ".swf");
			File swfFile = new File(swfFilePath);
			String pdfFilePath = basePath+ fileBasePath.replace("."+extention, ".pdf");
			if(!swfFile.exists()){
				File pdfFile = new File(pdfFilePath);
				if(!pdfFile.exists()){
					Office2PDF.office2pdf(sourceFilePath, pdfFilePath);
				}
				PDF2SWF.pdf2swf(pdfFilePath, swfFilePath);
				FileUtil.deleteFile(pdfFilePath);
			}
		}else if("txt".equals(extention)){
			String htmlFilePath = basePath+fileBasePath.replace("."+extention, ".html");
			File htmlFile = new File(htmlFilePath);
			if(!htmlFile.exists()){
				BufferedReader br = null;
				BufferedWriter bw = null;
				try {
					String line = null;
					StringBuilder sb = new StringBuilder();
					sb.append("<html>");
					sb.append("<head>");
					sb.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />");
					sb.append("<title>"+title+"</title>");
					sb.append("</head>");
					sb.append("<body>");
					br = new BufferedReader(new FileReader(sourceFile));
					bw = new BufferedWriter(new FileWriterWithEncoding(htmlFile,"UTF-8"));
					while ((line=br.readLine()) != null) {
						sb.append(line+"<br/>");
					}
					sb.append("</body>");
					sb.append("</html>");
					System.out.println(sb.toString());
					bw.write(sb.toString());
					bw.flush();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if(br!=null){
							br.close();
						}
						if(bw!=null){
							bw.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}else{
			msg = "不支持查看该格式的文件";
		}
		return msg;
	}

}
