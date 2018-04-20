package com.xuwei.util;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import net.sf.ehcache.Cache;


/**
 * @Description:共用的工具类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-13
 * @author lys
 * @vesion 1.0
 */
public class CommonUtil {
	static Properties applicationPro = new Properties();
	static Map<String,Integer> controlTimeHtmlMap=new HashMap<String,Integer>();
	static{
		try {
			applicationPro.load(CommonUtil.class.getClassLoader().getResourceAsStream("db.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static boolean isInControl(String key){
		Object htmlObj = controlTimeHtmlMap.get(key);
        return htmlObj != null;
    }
	/**
	 * @description: 是否有权限
	 * @createTime: 2016年1月8日 上午10:32:34
	 * @author: lys
	 * @param permission
	 * @param errorCode
	 */
	public static  void assertHasPermission(String permission, String errorCode) {
		if (!SecurityUtils.getSubject().isPermitted(permission)) {
			RequestAttributes localRequestAttributes = RequestContextHolder.currentRequestAttributes();
			localRequestAttributes.setAttribute("content", SpringUtils.getMessage(errorCode, permission), 0);
			throw new UnauthorizedException(SpringUtils.getMessage(errorCode, permission));
		}
    }
	
	
	@SuppressWarnings("rawtypes")
	public static  void printCache(String cacheKey){
		EhCacheCacheManager cacheManager = (EhCacheCacheManager)SpringUtils.getBean("cacheManager");
		Cache cache = cacheManager.getCacheManager().getCache(cacheKey);
		List keys = cache.getKeys();
		for (Object key : keys) {
			if(cache.get(key)!=null){
				Object obj = cache.get(key).getObjectValue();
				System.out.println("key="+key+":"+obj);
			}
		}
	}
	
	
	/**
	 * @Description: 取得大题题号
	 * @Created Time: 2013-5-9 上午10:15:40
	 * @Author lys
	 * @param no
	 * @return
	 */
	public static String getBigNo(Integer no){
		String result ="";
		switch (no) {
		case 1:
			result ="I";
			break;
		case 2:
			result ="II";
			break;
		case 3:
			result ="III";
			break;
		case 4:
			result ="IV";
			break;
		case 5:
			result ="V";
			break;
		case 6:
			result ="VI";
			break;
		case 7:
			result ="VII";
			break;
		case 8:
			result ="VIII";
			break;
		case 9:
			result ="IX";
			break;
		case 10:
			result ="X";
			break;
		case 11:
			result ="XI";
			break;
		case 12:
			result ="XXII";
			break;
		default:
			break;
		}
		return result;
	}
	/**
	 * @Description: 取得选项编号
	 * @Created Time: 2013-5-15 下午1:48:27
	 * @Author lys
	 * @param optionIndex
	 * @return
	 */
	public static char getOptionChar(int optionIndex){
		return (char)('A'+optionIndex);
	}
	/**
	 * @Description: 是否取得分数
	 * @Created Time: 2013-5-15 下午3:36:41
	 * @Author lys
	 * @param standardOption
	 * @param answer
	 * @return
	 */
	public static boolean isGoalScore(String standardOption,String answer){
		boolean isGoalScore = false;
		if(StringUtils.isEmpty(answer)){
			isGoalScore = false;
		}else{
			String[] answerArray = StringUtil.split(standardOption, "!@#");
			for (int i = 0; i < answerArray.length; i++) {
				String answerStr = answerArray[i];
				if (StringUtils.isNotEmpty(answerStr)&&answerStr.trim().equals(answer.trim())) {
					isGoalScore = true;
					break;
				}
			}
		}
		return isGoalScore;
	}
	/**
	 * @Description: 根据指定长度 分隔字符串
	 * @Created: 2013-9-13 下午4:03:43
	 * @Author lys
	 * @param str 需要处理的字符串
	 * @param length 分隔长度
	 * @return 字符串集合
	 */
	public static List<String> splitString(String str, int length) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < str.length(); i += length) {
			int endIndex = i + length;
			if (endIndex <= str.length()) {
				list.add(str.substring(i, i + length));
			} else {
				list.add(str.substring(i, str.length() - 1));
			}
		}
		return list;
	}
	
	/**
	 * @Description: 切割栏目编号
	 * @Create: 2013-9-26 上午1:33:01
	 * @author lys
	 * @update logs
	 * @param catCode
	 * @return
	 */
	public static String[] splitPositionCatCode(String catCode) {
		int catCodeLength = catCode.length();
		String[] catCodeArray = new String[catCodeLength/2];
		for (int i = 1; i < catCodeLength/2; i++) {
			catCodeArray[i]= catCode.substring(0, i*2+2);
		}
		return catCodeArray;
	}
	/**
	 * @Description: 切割编号
	 * @Create: 2013-11-27 下午07:29:07
	 * @author jcf
	 * @update logs
	 * @param pkCode 编号
	 * @param length 初始长度
	 * @param splitLength 按splitLength长度切割
	 * @return
	 */
	public static String[] splitPkCode(String pkCode,Integer length,Integer splitLength){
		int pkCodeLength = pkCode.length();
		String[] pkCodeArray =new String[(pkCodeLength-length)/splitLength+1];
		for(int i=0;i<=(pkCodeLength-length)/splitLength;i++){
			pkCodeArray[i] = pkCode.substring(0, i*splitLength+length);
		}
		
		return pkCodeArray;
	}
	/**
	 * @Description: 取得学工部部门Id
	 * @Create: 2013-10-12 下午7:57:00
	 * @author lys
	 * @update logs
	 * @return
	 */
	public static String getSTUMSSchoolDeptId(){
		return applicationPro.get("STUMS.schooolDeptId").toString();
	}
	public static String getWebSites(){
		return applicationPro.get("STUMS.websites").toString();
	}

	public static String getSTUMSTemplateCode() {
		return applicationPro.get("STUMS.STUMSTemplateCode").toString();
	}
	
	public static void setSTUMSTemplateCode(String STUMSTemplateCode){
		 applicationPro.setProperty("STUMS.STUMSTemplateCode", STUMSTemplateCode);
		 OutputStream fos = null;
		 try {
			fos = new BufferedOutputStream(new FileOutputStream(CommonUtil.class.getClassLoader().getResource("application.properties").getPath()));
			fos.flush();
			applicationPro.store(fos, "写入到propertise文件");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getAppProParam(String key){
		return applicationPro.get(key).toString();
	}
	
	public static Integer getIntegerAppProParam(String key){
		return applicationPro.get(key)==null?0:Integer.parseInt(applicationPro.get(key).toString());
	}
	
	public static void setAppProParam(String key, String value){
		applicationPro.setProperty(key, value);
		OutputStream fos = null;
		 try {
			fos = new BufferedOutputStream(new FileOutputStream(CommonUtil.class.getClassLoader().getResource("application.properties").getPath()));
			fos.flush();
			applicationPro.store(fos, "写入到propertise文件");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	
	/**
	 * @Description: 取得根权限
	 * @Create: 2014-6-1 下午8:16:10
	 * @author lys
	 * @update logs
	 * @return
	 */
	public static String getRootRightId() {
		return applicationPro.get("rootRightId").toString();
	}
	/**
	 * @Description: 取得根学生权限
	 * @Create: 2014-6-1 下午5:02:15
	 * @author lys
	 * @update logs
	 * @return
	 */
	public static String getRootStuRightId() {
		return applicationPro.get("rootStuRightId").toString();
	}
	/**
	 * @Description: 取得key的配置值
	 * @Create: 2014-6-10 下午3:47:46
	 * @author lys
	 * @update logs
	 * @param key
	 * @return
	 */
	public static String getConfig(String key) {
		return applicationPro.get(key).toString();
	}
	
	/**
	 * @Description: 密码验证:不能全是数字或全是字母，长度>6
	 * @Create: 2014-6-21 下午4:20:44
	 * @author lys
	 * @update logs
	 * @return
	 */
	public static Boolean valdateUsepwd(String usepwd){
		Pattern pattern = Pattern.compile("(?!^\\d+$)(?!^[a-zA-Z]+$).{6,}");
		  Matcher matcher = pattern.matcher(usepwd);
		return matcher.matches();
	}
	
	
	public static void main(String[] args) {
//		String descript= "<p class=\"p0\">   答辩委员会票决答辩结果，经全体成员(&nbsp;)以上同意，方可通过。  </p>";
//		Pattern p = Pattern.compile("<p.*?>(.*?)</p>");// 正则表达式，后面的参数指定忽略大小写
//		Matcher m = p.matcher(descript);// 匹配的字符串
//		if (m.find())//
//		{
//			descript = m.group(1);
//		}
//		System.out.println(descript);
		System.out.println(getUUID().length());
	}
	
	/**
	 * @Description: 格式化字符串
	 * @Create: 2014-12-8 下午3:32:17
	 * @author lys
	 * @update logs
	 * @param pattern
	 * @param source
	 * @return
	 */
	public static String getChartId(Integer topic,Integer index){
		//规则：c+题目topic+子项信息（5位）
		 java.text.DecimalFormat df = new java.text.DecimalFormat("0000");
			return topic+df.format(index);
	}
}
