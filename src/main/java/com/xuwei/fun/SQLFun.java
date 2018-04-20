package com.xuwei.fun;

import java.sql.Timestamp;
import java.util.Date;

public class SQLFun {

	
	/**
	 * 将java.util.Date类型的对象转换为 java.sql.Timestamp类型
	 * @param pDate 为null，则返回null
	 * @return
	 */
	public static Timestamp Date2Timestamp(Date pDate) {
		Timestamp stamp = null;
		
		if(pDate!=null) {
			
			stamp = new Timestamp(pDate.getTime());
		}
		
		
		return stamp;
	}
}
