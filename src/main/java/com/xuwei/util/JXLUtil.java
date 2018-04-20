package com.xuwei.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * @Description:jxl操作Excel自定义工具类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-8-14
 * @author lys
 * @vesion 1.0
 */
public class JXLUtil {
	
	/**
	 * 
	 * @Description: 从Excel单元格里面获取内容
	 * @param
	 * @Create: 2012-8-3 上午10:39:20
	 * @author longweier
	 * @update logs
	 * @param sheet
	 * @param column
	 * @param row
	 * @return
	 * @return
	 * @throws Exception
	 */
	public static String getContent(Sheet sheet, int column, int row){
		
		Cell cell = sheet.getCell(column, row);
		
		if(cell.getContents()==null || cell.getType() == CellType.EMPTY || "".equals(cell.getContents().toString().trim())){
			return null;
		}
		if(cell.getType()== CellType.DATE){
			DateCell dc = (DateCell)cell;
			Date date = dc.getDate();
			SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd");
			return ds.format(date);
		}
		return cell.getContents().toString().trim();
	}
	/**
	 * @Description: 检查excel的模板
	 * @Created: 2013-8-14 下午10:28:58
	 * @Author lys
	 * @param sheet
	 * @param sheetTemplate
	 * @return
	 */
	public static boolean checkTemplate(Sheet sheet, Sheet sheetTemplate){
		return checkTemplate(sheet,sheetTemplate,0);
	}
	/**
	 * @Description: 检查excel的模板
	 * @Created: 2013-8-14 下午10:28:58
	 * @Author lys
	 * @param sheet
	 * @param sheetTemplate
	 * @return
	 */
	public static boolean checkTemplate(Sheet sheet, Sheet sheetTemplate,int checkRow){
		int columns = sheet.getColumns();
		int columnsTemplate = sheetTemplate.getColumns();
		
		if(columns<columnsTemplate){
			return false;
		}
		Cell cell = null;
		Cell cellTemplate = null;
		boolean isTrue = false;
		for(int j = 0; j < columnsTemplate; j++){
			cell = sheet.getCell(j, checkRow);
			cellTemplate = sheetTemplate.getCell(j, checkRow);
			if(null != cell.getContents() && cell.getType() != CellType.EMPTY && cell.getContents().toString().trim().equals(cellTemplate.getContents().toString().trim())){
				isTrue = true;
			} else {
				isTrue = false;
				break;
			}
		}
		return isTrue;
	}
	/**
	 * @Description: 检查excel的模板
	 * @Created: 2013-8-14 下午10:28:58
	 * @Author lys
	 * @param sheet
	 * @param sheetTemplate
	 * @return
	 */
	public static boolean checkTemplate(Sheet sheet, String[] checkProperties){
		return checkTemplate(sheet,checkProperties,0);
	}
	/**
	 * @Description: 检查excel的模板
	 * @Created: 2013-8-14 下午10:28:58
	 * @Author lys
	 * @param sheet
	 * @param sheetTemplate
	 * @return
	 */
	public static boolean checkTemplate(Sheet sheet, String[] checkProperties,int checkRow){
		int columns = sheet.getColumns();
		if(columns!=checkProperties.length){
			return false;
		}
		Cell cell = null;
		boolean isTrue = false;
		for(int j = 0; j < checkProperties.length; j++){
			cell = sheet.getCell(j, checkRow);
			if(null != cell.getContents() && cell.getType() != CellType.EMPTY && cell.getContents().toString().trim().equals(checkProperties[j].trim())){
				isTrue = true;
			} else {
				isTrue = false;
				break;
			}
		}
		return isTrue;
	}
	
	public static WritableCellFormat getFontStyleTitle() {
		WritableFont font = new WritableFont(WritableFont.TIMES, 11, WritableFont.BOLD);
		WritableCellFormat format = new WritableCellFormat(font);
		try {
			format.setAlignment(jxl.format.Alignment.CENTRE);
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format.setShrinkToFit(true);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return format;
	}
	
	public static WritableCellFormat getFontStyleTitle(Integer fontSize) {
		WritableFont font = new WritableFont(WritableFont.TIMES, fontSize, WritableFont.BOLD);
		WritableCellFormat format = new WritableCellFormat(font);
		try {
			format.setAlignment(jxl.format.Alignment.CENTRE);
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format.setShrinkToFit(true);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return format;
	}
	
	public static WritableCellFormat getFontStyleContent() {
		WritableFont font = new WritableFont(WritableFont.TIMES, 10);
		WritableCellFormat format = new WritableCellFormat(font);
		try {
			format.setAlignment(jxl.format.Alignment.CENTRE);
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format.setWrap(true);
			format.setShrinkToFit(true);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return format;
	}
	/**
	 * @Description: 将Map型数据导出成Excel
	 * @Created: 2013-8-28 下午9:32:24
	 * @Author lys
	 * @param titleExport
	 * @param fieldNamesExport
	 * @param fieldsExport
	 * @throws Exception 
	 */
	public static void exportExcelMap(List<Map<String,Object>> listMap, String titleExport, String fieldNamesExport,
									  String fieldsExport,HttpServletResponse response, Map<String, Map<String, String>> proConvertMaps) throws Exception{
		String[] fieldNameArray =StringUtil.split(fieldNamesExport) ;
		String[] fieldArray =StringUtil.split(fieldsExport);
		if(fieldArray!=null){
			response.setContentType("application/msexcel;charset=UTF-8");
			response.setHeader("Content-disposition", "attachment; filename=" + java.net.URLEncoder.encode(titleExport, "UTF-8") + ".xls");
			
			WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());
			WritableSheet  sheet = 	workbook.createSheet(titleExport, 0);
			WritableCellFormat formatTitle = JXLUtil.getFontStyleTitle();
			WritableCellFormat formatContent = JXLUtil.getFontStyleContent();
			if(fieldNameArray!=null&&fieldNameArray.length>0){
				sheet.mergeCells(0, 0, fieldNameArray.length-1, 0);
			}
			Label label00  =   new  Label(0 ,  0 , titleExport,formatTitle);
			sheet.addCell(label00);
			for (int i = 0; i < fieldNameArray.length; i++) {
				 Label label  =   new  Label(i ,  1 ,  fieldNameArray[i],formatTitle);
				 sheet.addCell(label);
			}
			for (int i = 0; i < listMap.size(); i++) {
				Map<String,Object> map = listMap.get(i);
				for (int j = 0; j < fieldArray.length; j++) {
					Object valueObj = map.get(fieldArray[j]);
					String labelValueString = valueObj==null?"": valueObj .toString();

					if(proConvertMaps!=null && StringUtils.isNotEmpty(labelValueString)){
						Map<String,String> convertMap =  proConvertMaps.get(fieldArray[j]);
						if(convertMap!=null){
							labelValueString = convertMap.get(labelValueString);
						}
					}
					Label label  =   new  Label(j ,  i+2 , labelValueString,formatContent);
					sheet.addCell(label);
				}
			}
			workbook.write();
			workbook.close();
		}
	}
	/**
	 * @description: 导出数据到文件中
	 * @createTime: 2016年10月9日 上午12:32:00
	 * @author: lys
	 * @param listMap
	 * @param titleExport
	 * @param fieldNamesExport
	 * @param fieldsExport
	 * @param filePath
	 * @param proConvertMaps
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void exportExcelMapToFile(List<Map<String,Object>> listMap,String titleExport,String fieldNamesExport,String fieldsExport,String filePath, Map<String, Map<String, String>>... proConvertMaps) throws Exception{
		String[] fieldNameArray =StringUtil.split(fieldNamesExport) ;
		String[] fieldArray =StringUtil.split(fieldsExport);
		if(fieldArray!=null){
			
			WritableWorkbook workbook = Workbook.createWorkbook(new File(filePath));
			WritableSheet  sheet = 	workbook.createSheet(titleExport, 0);
			WritableCellFormat formatTitle = JXLUtil.getFontStyleTitle();
			WritableCellFormat formatContent = JXLUtil.getFontStyleContent();
			if(fieldNameArray!=null&&fieldNameArray.length>0){
				sheet.mergeCells(0, 0, fieldNameArray.length-1, 0);
			}
			Label label00  =   new  Label(0 ,  0 , titleExport,formatTitle);
			sheet.addCell(label00);
			for (int i = 0; i < fieldNameArray.length; i++) {
				 Label label  =   new  Label(i ,  1 ,  fieldNameArray[i],formatTitle);
				 sheet.addCell(label);
			}
			for (int i = 0; i < listMap.size(); i++) {
				Map<String,Object> map = listMap.get(i);
				for (int j = 0; j < fieldArray.length; j++) {
					Object valueObj = map.get(fieldArray[j]);
					String labelValueString = valueObj==null?"": valueObj .toString();
					//如果有需要转化的属性，需将属性值转化到对应的属性值
					if(proConvertMaps!=null&&proConvertMaps.length>0&&!"photoId".equals(fieldArray[j])){
					   Map<String, Map<String, String>>	proConvertMap =proConvertMaps[0];
					   Map<String, String> proConvertValueMap =  proConvertMap.get(fieldArray[j]);
					   if(proConvertValueMap!=null&&StringUtils.isNotEmpty(labelValueString)){
						   labelValueString = proConvertValueMap.get(labelValueString);
					   }
					}
					if("photoId".equals(fieldArray[j])&&StringUtils.isNotEmpty(labelValueString)&&proConvertMaps[0]!=null){
						Map<String, String> photoPathMap = proConvertMaps[0].get("photoPath");
						if(photoPathMap!=null){
							if(photoPathMap.get("path")!=null){
								File fileImage=new File(photoPathMap.get("path")+ "/" + labelValueString+ ".png");
								if(fileImage.exists()){
									WritableImage image=new WritableImage(j ,  i+2,1,1,fileImage);
									sheet.addImage(image);
								}
							}
						}
					}else{
						Label label  =   new  Label(j ,  i+2 , labelValueString,formatContent);
						sheet.addCell(label);
					}
					
				}
			}
			workbook.write();
			workbook.close();
		}
	}
	/**
	 * @Description: 将List型数据导出成Excel
	 * @Created: 2013-8-29 上午12:04:21
	 * @Author lys
	 * @param list
	 * @param titleExport
	 * @param fieldNamesExport
	 * @param fieldsExport
	 * @param properties
	 * @param response
	 * @param proConvertMaps
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void exportExcelList(List list,String titleExport,String fieldNamesExport,String fieldsExport,String[] properties,HttpServletResponse response, Map<String, Map<String, String>>... proConvertMaps) throws Exception{
		//取得转换前的属性
		String[] fieldArray = StringUtil.split(fieldsExport);
		String[] propertiesExport = new String[fieldArray.length];
		for (int i = 0; i < fieldArray.length; i++) {
			String filed = fieldArray[i];
			for (int j = 0; j < properties.length; j++) {
				if(properties[j].contains(filed)){
					String pros[] = properties[j].split(":");
					if(pros.length>1){
						if(pros[pros.length-1].equals(filed)){
							propertiesExport[i] = properties[j];
							break;
						}
					}else{
						pros = properties[j].split("\\.");
						if(pros[pros.length-1].equals(filed)){
							propertiesExport[i] = properties[j];
							break;
						}
					}
				}
			}
		}
		String[] fieldNamesExportArray =StringUtil.split(fieldNamesExport) ;
		if(fieldArray!=null){
			response.setContentType("application/msexcel;charset=UTF-8");
			response.setHeader("Content-disposition", "attachment; filename=" + java.net.URLEncoder.encode(titleExport, "UTF-8") + ".xls");
			
			WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());
			WritableSheet  sheet = 	workbook.createSheet(titleExport, 0);
			WritableCellFormat formatTitle = JXLUtil.getFontStyleTitle();
			formatTitle.setLocked(true);
			WritableCellFormat formatContent = JXLUtil.getFontStyleContent();
			if(fieldNamesExportArray!=null&&fieldNamesExportArray.length>0){
				sheet.mergeCells(0, 0, fieldNamesExportArray.length-1, 0);
			}
			Label label00  =   new  Label(0 ,  0 , titleExport,formatTitle);
			sheet.addCell(label00);
			for (int i = 0; i < fieldNamesExportArray.length; i++) {
				 Label label  =   new  Label(i ,  1 ,  fieldNamesExportArray[i],formatTitle);
				 sheet.addCell(label);
			}
			for (int i = 0; i < list.size(); i++) {
				Object object = list.get(i);
				for (int j = 0; j < propertiesExport.length; j++) {
					String valueObj ="";
					String property = propertiesExport[j];
					if(StringUtils.isNotEmpty(property)){
						if(property.contains(":")){
							 property =  StringUtils.substringBefore(property, ":");
						}
						if(property.contains(".")){
							String propertyPrefix = "";
							String propertySuffix = property;
							boolean canGetProperty = true;
							/*
							 * 判读.前的属性是否为null，如果为null，则不能读取该属性
							 * 如果不为null，则需截取.后的属性，继续相同判断，直到最后截取的属性中不含有.
							 */
							String haveTestStr = "";
							while(propertySuffix.contains(".")){
								propertyPrefix = StringUtils.substringBefore(propertySuffix, ".");
								if(BeanUtils.getProperty(object, haveTestStr+propertyPrefix)==null){
									canGetProperty = false;
									break;
								}
								haveTestStr+=propertyPrefix+".";
                                propertySuffix = StringUtils.substringAfter(propertySuffix, ".");
							}
							if(canGetProperty){
								valueObj =  BeanUtils.getProperty(object, property);
							}
						}else{
							valueObj =  BeanUtils.getProperty(object, property);
						}
						String labelValueString = valueObj==null?"": valueObj .toString();
						//如果有需要转化的属性，需将属性值转化到对应的属性值
						String photoIdProperty = property;
						if(property.lastIndexOf(".")>-1){
							photoIdProperty = property.substring(property.lastIndexOf(".")+1);
						}
						
						if(proConvertMaps!=null&&proConvertMaps.length>0&&!"photoId".equals(photoIdProperty)){
						   Map<String, Map<String, String>>	proConvertMap =proConvertMaps[0];
						   Map<String, String> proConvertValueMap =  proConvertMap.get(property);
						   if(proConvertValueMap!=null&&StringUtils.isNotEmpty(labelValueString)){
							   labelValueString = proConvertValueMap.get(labelValueString);
						   }
						}
						if("photoId".equals(photoIdProperty)&&StringUtils.isNotEmpty(labelValueString)&&proConvertMaps[0]!=null){
							Map<String, String> photoPathMap = proConvertMaps[0].get("photoPath");
							if(photoPathMap!=null){
								if(photoPathMap.get("path")!=null){
									File fileImage=new File(photoPathMap.get("path")+ "/" + labelValueString+ ".png");
									if(fileImage.exists()){
										WritableImage image=new WritableImage(j ,  i+2,1,1,fileImage);
										sheet.addImage(image);
									}
								}
							}
						}else{
							Label label  =   new  Label(j ,  i+2 ,labelValueString,formatContent);
							sheet.addCell(label);
						}
					}
				}
			}
			workbook.write();
			workbook.close();
		}
	}
	
	
	/**
	 * @description  将MapList数据导出成excel
	 * @createTime 2015-7-31 下午3:02:28
	 * @author lys
	 * @param response
	 * @param filename
	 * @param mapList
	 * @param columnFieldArray
	 * @param mergeColumnArray
	 * @param cellFormat
	 */
	public static void exportExcelFromMapList(HttpServletResponse response,String filename,List<Map<String, Object>> mapList,
		String[][] columnFieldArray,Integer[] mergeColumnArray,WritableCellFormat cellFormat,Integer... deleteColumns) throws Exception{
		response.setContentType("application/msexcel;charset=UTF-8");
		response.setHeader("Content-disposition", "attachment; filename=" + java.net.URLEncoder.encode(filename, "UTF-8") + ".xls");
		
		WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());
		int maxSize = mapList.size()%65500!=0?(mapList.size()/65500+1):(mapList.size()/65500);
		for(int f=0;f<maxSize;f++){
			WritableSheet sheet = workbook.createSheet(filename+(f+1), f);
			WritableFont font1 = new WritableFont(WritableFont.createFont("宋体"),
					15, WritableFont.BOLD, false);
			WritableCellFormat cellFormat1 = new WritableCellFormat();
			cellFormat1.setFont(font1);
			cellFormat1.setAlignment(Alignment.CENTRE);
			cellFormat1.setVerticalAlignment(VerticalAlignment.CENTRE);
			sheet.addCell(new Label(0, 0, filename, cellFormat1));
			sheet.mergeCells(0, 0, columnFieldArray.length-1, 0);
			//表头
			//列字段
			for (int i = 0; i < columnFieldArray.length; i++) {
				sheet.addCell(new Label(i, 1, columnFieldArray[i][0], cellFormat1));
				if(columnFieldArray[i].length>2){
					sheet.setColumnView(i, Integer.parseInt(columnFieldArray[i][2]));
				}
			}
			String[][] mergeArray  = new String[columnFieldArray.length][3];
			//0--判断合并的列  1--前值 2 -- 合并开始行
			for (int i = 0; i < mergeColumnArray.length; i++) {
				String mergeColumns = null;
				
				for (int j = 0; j <=i; j++) {
					if(StringUtils.isEmpty(mergeColumns)){
						mergeColumns = mergeColumnArray[j]+"";
					}else{
						mergeColumns =mergeColumns+ "^"+mergeColumnArray[j];
					}
				}
				mergeArray[mergeColumnArray[i]][0]=mergeColumns;
			}
			//导入数据
			if(mapList==null||mapList.size()!=0){
				int nowMaxSize = mapList.size()>(f+1)*65500?(f+1)*65500:mapList.size();
				for(int i =(f*65500);i<nowMaxSize;i++){
					Map<String,Object> map = mapList.get(i);
					for (int j = 0; j < columnFieldArray.length; j++) {
						String value = map.get(columnFieldArray[j][1])==null?null:map.get(columnFieldArray[j][1]).toString();
						sheet.addCell(new Label(j, (2+(i%65500)), value, cellFormat));
						
						String mergeColumns = mergeArray[j][0];
						if(StringUtils.isNotEmpty(mergeColumns)){//是指定的合并列
							String[] mergeColumnsArray =StringUtil.split(mergeColumns);
							String newValue = "";
							for (int k = 0; k < mergeColumnsArray.length; k++) {
								newValue=newValue+"_"+(map.get(columnFieldArray[k][1])==null?null:map.get(columnFieldArray[k][1]).toString());
							}
							
							if(StringUtils.isEmpty(mergeArray[j][1])){
								mergeArray[j][1] = newValue;
								mergeArray[j][2] = (i+2)+"";
							}else{
								if(StringUtils.isNotEmpty(mergeArray[j][1])&&!newValue.equals(mergeArray[j][1])){
									sheet.mergeCells(j, Integer.parseInt(mergeArray[j][2]), j, i+1);
									mergeArray[j][1] = newValue;
									mergeArray[j][2] = (i+2)+"";
								}else if(StringUtils.isNotEmpty(mergeArray[j][1])&&
										(i+1==mapList.size())){
									sheet.mergeCells(j, Integer.parseInt(mergeArray[j][2]), j, i+2);
									mergeArray[j][1] = newValue;
									mergeArray[j][2] = (i+2)+"";
								}
							}
						}
					}
				}
			}
			int hadDeleteColumnSize = 0;
			for (int i = 0; i < deleteColumns.length; i++) {
				sheet.removeColumn(deleteColumns[i]-hadDeleteColumnSize);
				hadDeleteColumnSize++;
			}
		}
		workbook.write();
		workbook.close();
	}
	
	public static void exportExcelFromMapListToFile(String filePath,String filename,List<Map<String, Object>> mapList,
			String[][] columnFieldArray,Integer[] mergeColumnArray,WritableCellFormat cellFormat,Integer... deleteColumns) throws Exception{
		WritableWorkbook workbook = Workbook.createWorkbook(new File(filePath));
		WritableSheet sheet = workbook.createSheet(filename, 0);
		WritableFont font1 = new WritableFont(WritableFont.createFont("宋体"),
				15, WritableFont.BOLD, false);
		WritableCellFormat cellFormat1 = new WritableCellFormat();
		cellFormat1.setFont(font1);
		cellFormat1.setAlignment(Alignment.CENTRE);
		cellFormat1.setVerticalAlignment(VerticalAlignment.CENTRE);
		sheet.addCell(new Label(0, 0, filename, cellFormat1));
		sheet.mergeCells(0, 0, columnFieldArray.length-1, 0);
		//表头
		//列字段
		for (int i = 0; i < columnFieldArray.length; i++) {
			sheet.addCell(new Label(i, 1, columnFieldArray[i][0], cellFormat));
			if(columnFieldArray[i].length>2){
				sheet.setColumnView(i, Integer.parseInt(columnFieldArray[i][2]));
			}
		}
		String[][] mergeArray  = new String[columnFieldArray.length][3];
		//0--判断合并的列  1--前值 2 -- 合并开始行
		for (int i = 0; i < mergeColumnArray.length; i++) {
			String mergeColumns = null;
			
			for (int j = 0; j <=i; j++) {
				if(StringUtils.isEmpty(mergeColumns)){
					mergeColumns = mergeColumnArray[j]+"";
				}else{
					mergeColumns =mergeColumns+ "^"+mergeColumnArray[j];
				}
			}
			mergeArray[mergeColumnArray[i]][0]=mergeColumns;
		}
		//导入数据
		if(mapList==null||mapList.size()!=0){
			for(int i =0;i<mapList.size();i++){
				Map<String,Object> map = mapList.get(i);
				for (int j = 0; j < columnFieldArray.length; j++) {
					String value = map.get(columnFieldArray[j][1])==null?null:map.get(columnFieldArray[j][1]).toString();
					sheet.addCell(new Label(j, 2+i, value, cellFormat));
					
					String mergeColumns = mergeArray[j][0];
					if(StringUtils.isNotEmpty(mergeColumns)){//是指定的合并列
						String[] mergeColumnsArray =StringUtil.split(mergeColumns);
						String newValue = "";
						for (int k = 0; k < mergeColumnsArray.length; k++) {
							newValue=newValue+"_"+(map.get(columnFieldArray[k][1])==null?null:map.get(columnFieldArray[k][1]).toString());
						}
						
						if(StringUtils.isEmpty(mergeArray[j][1])){
							mergeArray[j][1] = newValue;
							mergeArray[j][2] = (i+2)+"";
						}else{
							if(StringUtils.isNotEmpty(mergeArray[j][1])&&
									!newValue.equals(mergeArray[j][1])){
								sheet.mergeCells(j, Integer.parseInt(mergeArray[j][2]), j, i+1);
								mergeArray[j][1] = newValue;
								mergeArray[j][2] = (i+2)+"";
							}else if(StringUtils.isNotEmpty(mergeArray[j][1])&&
									((i+1==mapList.size()))){
								sheet.mergeCells(j, Integer.parseInt(mergeArray[j][2]), j, i+2);
								mergeArray[j][1] = newValue;
								mergeArray[j][2] = (i+2)+"";
							}
						}
					}
				}
			}
		}
		int hadDeleteColumnSize = 0;
		for (int i = 0; i < deleteColumns.length; i++) {
			sheet.removeColumn(deleteColumns[i]-hadDeleteColumnSize);
			hadDeleteColumnSize++;
		}
		workbook.write();
		workbook.close();
	}

}
