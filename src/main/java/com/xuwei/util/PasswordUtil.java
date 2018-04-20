package com.xuwei.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @description: 密码工具类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年6月2日下午6:44:04
 * @author：lys
 * @version：1.0
 */
public class PasswordUtil {
	/**
	 * @description: 取得随机字符串
	 * @createTime: 2017年6月2日 下午6:00:53
	 * @author: lys
	 * @param from
	 * @param distance
	 * @return
	 */
	public static String getRandom(int from,int distance){
		int no =(int) (distance* Math.random() +from );
		return RandomStringUtils.randomAlphanumeric(no).toLowerCase();
	}
	
	/**
	 * @description: 取得用户密码
	 * @createTime: 2017年6月2日 下午6:38:03
	 * @author: lys
	 * @param prefix
	 * @param pwd
	 * @param suffix
	 * @return
	 */
	public static String getPwd(String prefix,String pwd,String suffix){
		try {
			String md5PWD = Hex.encodeHexString(CodeUtil.encryptMD5(pwd.getBytes()));
			String str = prefix+md5PWD+suffix;
			return Hex.encodeHexString(CodeUtil.encryptSHA(str.getBytes()));
		} catch (Exception e) {
			new RuntimeException(e.getMessage());
		}
		return null;
	}
	
	
	
	public static void main(String[] args) {
		String prefix = PasswordUtil.getRandom(Integer.parseInt(CommonUtil.getAppProParam("pwd.from")), Integer.parseInt(CommonUtil.getAppProParam("pwd.distance")));
		System.out.println(prefix.length());
		String suffix = PasswordUtil.getRandom(Integer.parseInt(CommonUtil.getAppProParam("pwd.from")), Integer.parseInt(CommonUtil.getAppProParam("pwd.distance")));
		System.out.println(suffix.length());
		String pwd = "a123456";
		String newPwd = getPwd(prefix,pwd,suffix);
		System.out.println(newPwd.length());
	}
	
}
