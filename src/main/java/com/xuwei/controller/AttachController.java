package com.xuwei.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xuwei.bean.Attach;
import com.xuwei.service.IAttachService;
import com.xuwei.util.CommonUtil;


/**
 * @description: 系统_附件前端控制器
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年06月30日 11:46:47
 * @author: lys
 * @version: 1.0
 */
@Controller
@RequestMapping("/admin/attach")
public class AttachController {
	@Resource
	private IAttachService attachService;

	@RequestMapping(value = { "/getImg" }, method = { RequestMethod.GET })
	public void getImg(Long attachId, HttpServletRequest request, HttpServletResponse response) {
		// 文件保存目录路径
		String basePath = CommonUtil.getAppProParam("project_files.base.path");
		String path = null;
		if (attachId != null) {
			Attach attach = attachService.selectById(attachId);
			if (attach != null) {
				path = attach.getPath();
			}
		} else {
			throw new RuntimeException("没有找到相应的图片");
		}
		if (StringUtils.isNotEmpty(path)) {
			String filePath = basePath + path;
			File file = new File(filePath);
			try {
				if (file.exists()) {
					ServletOutputStream outputStream = response.getOutputStream();
					InputStream in = new FileInputStream(filePath);
					BufferedOutputStream bout = new BufferedOutputStream(outputStream);
					byte b[] = new byte[1024];
					int len = in.read(b);
					while (len > 0) {
						bout.write(b, 0, len);
						len = in.read(b);
					}
					bout.close();
					in.close();
					outputStream.flush();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
