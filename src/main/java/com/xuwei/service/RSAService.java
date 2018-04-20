package com.xuwei.service;

import java.security.interfaces.RSAPublicKey;
import javax.servlet.http.HttpServletRequest;

public interface RSAService {
	RSAPublicKey generateKey(HttpServletRequest paramHttpServletRequest);

	void removePrivateKey(HttpServletRequest paramHttpServletRequest);

	/**
	 * @Description: 解码参数
	 * @Created: 2015-1-23 上午1:29:37
	 * @author lys
	 * @param paramString
	 * @param paramHttpServletRequest
	 * @return
	 */
    String decryptParameter(String paramString, HttpServletRequest paramHttpServletRequest);
}
