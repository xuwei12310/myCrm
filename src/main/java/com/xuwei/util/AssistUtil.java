package com.xuwei.util;

/**
 * 
 * @description: 辅助工具类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年9月1日下午4:48:56
 * @author：caw
 * @version：1.0
 */
public class AssistUtil {
	
	/**
	 * 
	 * @description: 提取身份证中的出生日期
	 * @createTime: 2017年9月1日 下午4:51:20
	 * @author: caw
	 * @param idCard
	 * @return
	 */
	public static String getBirthdayByIdCard(String idCard) { 
        String idCardNumber = idCard.trim(); 
        int idCardLength = idCardNumber.length(); 
        String birthday = null; 
        if (idCardNumber == null || "".equals(idCardNumber)) { 
            return null; 
        } 
        if (idCardLength == 18) { 
            birthday = idCardNumber.substring(6, 10) + "-" 
                    + idCardNumber.substring(10, 12) + "-" 
                    + idCardNumber.substring(12, 14); 
        } 
        if (idCardLength == 15) { 
            birthday = "19" + idCardNumber.substring(6, 8)+ "-" 
                    + idCardNumber.substring(8, 10) + "-" 
                    + idCardNumber.substring(10, 12); 
        } 
        return birthday; 
    } 
}
