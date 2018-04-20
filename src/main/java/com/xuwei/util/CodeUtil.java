package com.xuwei.util;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomStringUtils;

public class CodeUtil {
	public static final String KEY_SHA = "SHA";
	public static final String KEY_MD5 = "MD5";

	/**
	 * MAC算法可选以下多种算法
	 * 
	 * <pre>
	 * HmacMD5 
	 * HmacSHA1 
	 * HmacSHA256 
	 * HmacSHA384 
	 * HmacSHA512
	 * </pre>
	 */
	public static final String KEY_MAC = "HmacMD5";

	/**
	 * MD5加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptMD5(byte[] data) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);
		return md5.digest();

	}

	/**
	 * SHA加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA(byte[] data) throws Exception {
		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		sha.update(data);
		return sha.digest();
	}
	
	public static void main(String[] args) throws Exception {
		int no =(int) ( 40 * Math.random() + 10);
		String str = RandomStringUtils.randomAlphanumeric(no);
		System.out.println(str);
		System.out.println(Hex.encodeHexString(encryptMD5("a123456".getBytes())));
		System.out.println( Hex.encodeHexString(encryptSHA(("a123456"+str).getBytes())));
	}
}
