package com.xuwei.service.impl;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.xuwei.service.RSAService;
import com.xuwei.util.RSAUtils;


@Service("rsaServiceImpl")
public class RSAServiceImpl implements RSAService {
	private static final String PRIVATE_KEY = "privateKey";

	@Transactional(readOnly = true)
	public RSAPublicKey generateKey(HttpServletRequest request) {
		Assert.notNull(request);
		KeyPair localKeyPair = RSAUtils.generateKeyPair();
		//生成密钥对：公钥和私钥，公钥传过前台，私钥放到session
		RSAPublicKey localRSAPublicKey = (RSAPublicKey) localKeyPair.getPublic();
		RSAPrivateKey localRSAPrivateKey = (RSAPrivateKey) localKeyPair.getPrivate();
		HttpSession localHttpSession = request.getSession();
		localHttpSession.setAttribute(PRIVATE_KEY, localRSAPrivateKey);
		return localRSAPublicKey;
	}

	@Transactional(readOnly = true)
	public void removePrivateKey(HttpServletRequest request) {
		Assert.notNull(request);
		HttpSession localHttpSession = request.getSession();
		localHttpSession.removeAttribute(PRIVATE_KEY);
	}
	@Transactional(readOnly = true)
	public String decryptParameter(String name, HttpServletRequest request) {
		Assert.notNull(request);
		if (name != null) {
			HttpSession localHttpSession = request.getSession();
			RSAPrivateKey localRSAPrivateKey = (RSAPrivateKey) localHttpSession
					.getAttribute(PRIVATE_KEY);
			String str = request.getParameter(name);
			if ((localRSAPrivateKey != null) && (StringUtils.isNotEmpty(str)))
				return RSAUtils.decrypt(localRSAPrivateKey, str);
		}
		return null;
	}
}
