package com.xuwei.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;


public class StringUtil {
	/**
	 * @Description: 将字符串source根据separator分割成字符串数组
	 * @Create: 2013-1-8 下午2:28:10
	 * @author lys
	 * @update logs
	 * @param source
	 * @param separator
	 * @return
	 */
	public static String[] split(String source, String separator) {
		String[] distArray = {};
		if (source == null) {
			return null;
		}
		int i = 0;
		distArray = new String[StringUtils.countMatches(source, separator) + 1];
		while (source.length() > 0) {
			String value = StringUtils.substringBefore(source, separator);
			distArray[i++] = StringUtils.isEmpty(value) ? null : value;
			source = StringUtils.substringAfter(source, separator);
		}
		if (distArray[distArray.length - 1] == null) {// 排除最后一个分隔符后放空
			distArray[distArray.length - 1] = null;
		}
		return distArray;
	}

	/**
	 * @Description: 将字符串source根据全局变量GobelConstants.SPLIT_SEPARATOR分割成字符串数组
	 * @Create: 2013-1-15 上午12:06:52
	 * @author lys
	 * @update logs
	 * @param source
	 * @return
	 */
	public static String[] split(String source) {
		return split(source, GlobalConstants.SPLIT_SEPARATOR);
	}

	/**
	 * @Description: 将字符串source根据separator分割成Integer数组
	 * @Created Time: 2013-3-4 下午10:15:04
	 * @Author lys
	 * @param source
	 * @param separator
	 * @return
	 */
	public static Integer[] splitToInteger(String source, String separator) {
		Integer[] distArray = {};
		if (source == null) {
			return distArray;
		}
		int i = 0;
		distArray = new Integer[StringUtils.countMatches(source, separator) + 1];
		while (source.length() > 0) {
			String value = StringUtils.substringBefore(source, separator);
			distArray[i++] = StringUtils.isEmpty(value) ? null : Integer.parseInt(value);
			source = StringUtils.substringAfter(source, separator);
		}
		if (distArray[distArray.length - 1] == null) {// 排除最后一个分隔符后放空
			distArray[distArray.length - 1] = null;
		}
		return distArray;
	}

	/**
	 * @Description: 将字符串source根据全局变量GobelConstants.SPLIT_SEPARATOR分割成Integer数组
	 * @Create: 2013-1-15 上午12:06:52
	 * @author lys
	 * @update logs
	 * @param source
	 * @return
	 */
	public static Integer[] splitToInteger(String source) {
		return splitToInteger(source, GlobalConstants.SPLIT_SEPARATOR);
	}

	/**
	 * @Description: 将字符串source根据separator分割成Integer数组，并提出其中的Null值
	 * @Created Time: 2013-3-4 下午10:15:04
	 * @Author lys
	 * @param source
	 * @param separator
	 * @return
	 */
	public static Integer[] splitToIntegerTrimNull(String source, String separator) {
		Integer[] distArray = {};
		if (source == null || source.length() == 0) {
			return distArray;
		}
		int i = 0;
		distArray = new Integer[StringUtils.countMatches(source, separator) + 1];
		while (source.length() > 0) {
			String value = StringUtils.substringBefore(source, separator);
			if (StringUtils.isNotEmpty(value)) {
				distArray[i++] = Integer.parseInt(value);
			}
			source = StringUtils.substringAfter(source, separator);
		}
		if (distArray[distArray.length - 1] == null) {// 排除最后一个分隔符后放空
			distArray[distArray.length - 1] = null;
		}
		return distArray;
	}

	/**
	 * @Description: 将字符串source根据全局变量GobelConstants.SPLIT_SEPARATOR分割成Integer数组，
	 *               并剔除Null值
	 * @Create: 2013-1-15 上午12:06:52
	 * @author lys
	 * @update logs
	 * @param source
	 * @return
	 */
	public static Integer[] splitToIntegerTrimNull(String source) {
		return splitToIntegerTrimNull(source, GlobalConstants.SPLIT_SEPARATOR);
	}

	/**
	 * @Description: 将字符串source根据separator分割成Boolean数组
	 * @Created Time: 2013-3-4 下午10:15:04
	 * @Author lys
	 * @param source
	 * @param separator
	 * @return
	 */
	public static Boolean[] splitToBoolean(String source, String separator) {
		Boolean[] distArray = {};
		if (source == null) {
			return distArray;
		}
		int i = 0;
		distArray = new Boolean[StringUtils.countMatches(source, separator) + 1];
		while (source.length() > 0) {
			String value = StringUtils.substringBefore(source, separator);
			distArray[i++] = StringUtils.isEmpty(value) ? null : Boolean.parseBoolean(value);
			source = StringUtils.substringAfter(source, separator);
		}
		if (distArray[distArray.length - 1] == null) {// 排除最后一个分隔符后放空
			distArray[distArray.length - 1] = null;
		}
		return distArray;
	}

	/**
	 * @Description: 将字符串source根据全局变量GobelConstants.SPLIT_SEPARATOR分割成Boolean数组
	 * @Create: 2013-1-15 上午12:06:52
	 * @author lys
	 * @update logs
	 * @param source
	 * @return
	 */
	public static Boolean[] splitToBoolean(String source) {
		return splitToBoolean(source, GlobalConstants.SPLIT_SEPARATOR);
	}

	/**
	 * @Description: 将字符串source根据separator分割成Boolean数组
	 * @Created Time: 2013-3-4 下午10:15:04
	 * @Author lys
	 * @param source
	 * @param separator
	 * @return
	 */
	public static Double[] splitToDouble(String source, String separator) {
		Double[] distArray = {};
		if (source == null) {
			return distArray;
		}
		int i = 0;
		distArray = new Double[StringUtils.countMatches(source, separator) + 1];
		while (source.length() > 0) {
			String value = StringUtils.substringBefore(source, separator);
			distArray[i++] = StringUtils.isEmpty(value) ? null : Double.parseDouble(value);
			source = StringUtils.substringAfter(source, separator);
		}
		if (distArray[distArray.length - 1] == null) {// 排除最后一个分隔符后放空
			distArray[distArray.length - 1] = null;
		}
		return distArray;
	}

	/**
	 * @Description: 将字符串source根据全局变量GobelConstants.SPLIT_SEPARATOR分割成Double数组
	 * @Create: 2013-1-15 上午12:06:52
	 * @author lys
	 * @update logs
	 * @param source
	 * @return
	 */
	public static Double[] splitToDouble(String source) {
		return splitToDouble(source, GlobalConstants.SPLIT_SEPARATOR);
	}

	/**
	 * @Description: 将null等空Empty情况变为空字符串
	 * @Created Time: 2013-5-9 上午9:20:22
	 * @Author lys
	 * @param note
	 * @return
	 */
	public static String getEmptyToBlank(String source) {
		if (StringUtils.isEmpty(source)) {
			return "";
		}
		return source;
	}

	/**
	 * @Description: 切割code，取得父code
	 * @Created: 2013-9-20 下午4:23:01
	 * @Author lys
	 * @param code
	 * @param itemLength
	 * @return
	 */
	public static String[] splitParentCode(String code, Integer itemLength) {
		if (StringUtils.isEmpty(code) || code.length() % itemLength != 0) {
			return null;
		}
		String[] itemArray = new String[code.length() / itemLength];
		for (int i = 0; i < itemArray.length; i++) {
			itemArray[i] = code.substring(0, itemLength + itemLength * i);
		}
		return itemArray;
	}

	public static double[] calDoubleStr(String doubleStr) {
		double[] dbs = { 0, 0 };
		if (StringUtils.isEmpty(doubleStr)) {
			return dbs;
		} else {
			String[] seperatorStrings = { "+", "_", "-" };
			String seperator = null;
			for (String temp : seperatorStrings) {
				if (doubleStr.contains(temp)) {
					seperator = temp;
					break;
				}
			}
			String[] doubleArray = doubleStr.split("\\" + seperator);
			int index = 0;
			for (String temp : doubleArray) {
				double tempDouble = Double.parseDouble(temp);
				dbs[index++] = tempDouble;
			}
			return dbs;
		}
	}

	/**
	 * @Description 验证是否为一样的数字或字母 或者按顺序的字符串
	 * @author csj
	 * @createDate 2015-6-16
	 * @return
	 */
	public static boolean checkEmployingUnitCode(String str) {
		return isOrder(str) || isSame(str);
	}

	static String orderStr = "";

	static {
		for (int i = 33; i < 127; i++) {
			orderStr += Character.toChars(i)[0];
		}
	}

	// 判断是否有顺序
	public static boolean isOrder(String str) {
		if (!str.matches("((\\d)|([a-z])|([A-Z]))+")) {
			return false;
		}
		return orderStr.contains(str);
	}

	// 判断是否相同
	public static boolean isSame(String str) {
		String regex = str.substring(0, 1) + "{" + str.length() + "}";
		return str.matches(regex);
	}

	public static List<String> removeDuplicate(List<String> list) {
		Set<String> set = new HashSet<String>();
		List<String> newList = new ArrayList<String>();
		for (Iterator<String> iter = list.iterator(); iter.hasNext();) {
			String element = iter.next();
			if (set.add(element))
				newList.add(element);
		}
		return newList;
	}

	public static void main(String[] args) {

		String orderStr = "";
		for (int i = 33; i < 127; i++) {
			orderStr += Character.toChars(i)[0];
		}
		System.out.println(orderStr);
		System.out.println(isOrder("123"));
		System.out.println(isSame("11121"));

	}

}
