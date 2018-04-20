package com.xuwei.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.Area;
import com.xuwei.bean.Attach;
import com.xuwei.bean.Customer;
import com.xuwei.bean.Plot;
import com.xuwei.bean.User;
import com.xuwei.service.IAreaService;
import com.xuwei.service.IAttachService;
import com.xuwei.service.ICustomerService;
import com.xuwei.service.IDictService;
import com.xuwei.service.IPlotService;
import com.xuwei.service.IUserService;
import com.xuwei.util.AssistUtil;
import com.xuwei.util.DateUtil;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;


/**
 * @description: 客户控制器
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年08月31日 10:49:27
 * @author: caw
 * @version: 1.0
 */
@Controller
@RequestMapping("/myWorkbench/totalCustomer")
public class CustomerController extends BaseController<Customer> {
    @Resource
    private ICustomerService customerService;
    @Resource
	private IDictService dictService;
    @Resource
    private IUserService userService;
    @Resource
    private IAreaService areaService;
    @Resource
    private IPlotService plotService;
    @Resource
    private IAttachService attachService;

    public CustomerController(){
        setResourceIdentity("myWorkbench:totalCustomer");
    }

    /**
    * @description: 转向模块主界面
    * @createTime: 2017年08月31日 10:49:27
    * @author: caw
    * @param model
    * @return
    */
    @RequestMapping(value = "main",method = RequestMethod.GET)
    public String main(Model model,HttpServletRequest request){
        return defaultViewPrefix();
    }
    /**
    * @description: 添加
    * @createTime: 2017年08月31日 10:49:27
    * @author: caw
    * @param model
    * @param m
    * @return
    */
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Object create(@RequestParam(value="attachPhoto", required=false) MultipartFile file,HttpServletRequest request,Model model,Customer m,String sexOne){
        ServiceResult result = new ServiceResult(false);
        if(!hasCreatePermission()){
            result.setMessage("没有添加权限");
        }else{
        	m.setSex(Integer.parseInt(sexOne));
        	result = customerService.createCustomer(file, request, model, m);
        }
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
     * @description: 修改
     * @createTime: 2017年08月31日 10:49:27
     * @author: caw
     * @param model
     * @param m
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Object update(@RequestParam(value="attachPhotoMod", required=false) MultipartFile file,HttpServletRequest request,Model model,Customer m,Long attachId){
        ServiceResult result = new ServiceResult(false);
        if(!hasUpdatePermission()){
            result.setMessage("没有修改权限");
        }else{
        	result = customerService.updateCustomer(file, request, model, m, attachId);
        }
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 删除
    * @createTime: 2017年08月31日 10:49:27
    * @author: caw
    * @param model
    * @param ids
    * @return
    */
    @RequestMapping(value = "mulDelete",method = RequestMethod.POST)
    @ResponseBody
    public Object mulDelete(Model model,String ids){
        ServiceResult result = new ServiceResult(false);
        if(!hasUpdatePermission()){
            result.setMessage("没有删除权限");
        }else{
            try {
                String[] idArray = StringUtil.split(ids);
                if(idArray==null||idArray.length==0){
                    result.setMessage("请选择要删除的数据行");
                    return result;
                }
                boolean isSuccess = customerService.deleteBatchIds(Arrays.asList(idArray));
                result.setIsSuccess(isSuccess);
            } catch (Exception e) {
                if(e instanceof org.springframework.dao.DataIntegrityViolationException){
                    result.setMessage("数据已被引用不能删除");
                }else{
                    result.setMessage("删除出错："+e.getMessage());
                }
            }
        }
        String jsonString = result.toJSON();
        return jsonString;
    }
    
    /**
     * 根据id查询客户信息
     * @createTime: 2017年08月31日 5:49:58
     * @author: caw
     * @param customerid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "selectById")
    public Object selectById(Long customerid){
    	Customer customer = customerService.selectCustomerById(customerid);
    	String[] properties = {"customerType","customerName","sex","mobilePhone","telephone",
    			"email","idNumber","idAddress","customerSource.id:customerSourceid","customerSource.name:customerSourceName","customerStage.id:customerStageid",
    			"customerStage.name:customerStageName","customerStatus.id:customerStatusid","customerStatus.name:customerStatusName","owner.id:ownerid","owner.username:ownerUsername",
    			"owner.name:ownerName","gradeId","place.id:placeid","place.areaName:placeAreaName","place.showName:placeShowName","maritalId","liveArea.id:liveAreaid","liveArea.areaName:liveAreaAreaName",
    			"liveArea.showName:liveAreaShowName","livePlot.id:livePlotid","livePlot.plotName:livePlotPlotName","birthdate","company","occupation","spouseName",
    			"spousePlace.id:spousePlaceid","spousePlace.areaName:spousePlaceAreaName","spousePlace.showName:spousePlaceShowName","spouseMobilePhone","spouseIdNumber",
    			"spouseCompany","spouseOccupation","creditRatingId","creditRatingAttachId","lastTrackTime","photoId","id","note","lastModifyTime","createTime"};
    	String jsonString = JSONUtil.toJson(customer, properties);
		return jsonString;
    }

    /**
    * @description: 分页查询
    * @createTime: 2017年08月31日 10:49:27
    * @author: caw
    * @param model
    * @param m
    * @param rows
    * @param page
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "listByPage")
    public Object listByPage(Model model, Customer m, int rows, int page, String todayTime){
    	int viewType = 0;
        if(!hasPermissionByOpt("totalCustomer")){
        	if(hasPermissionByOpt("branchCustomer")){
        		viewType = 1;
        		User user = userService.getUserInfo(OperateUtils.getCurrentUserId());
        		m.setOwner(user);
        	}else if(hasViewPermission()){
        		viewType = 2;
        		m.setOwner(OperateUtils.getCurrentUser());
        	}else{
        		return  JSONUtil.EMPTYJSON;
        	}
        }
        Page<Customer> pageM= new Page<>(page,rows);
        List<Customer> list = customerService.listCustomerByPage(pageM, m, todayTime, viewType);
        String[] properties = {"customerType","customerName","sex","mobilePhone","telephone",
    			"email","idNumber","idAddress","customerSource.id:customerSourceid","customerSource.name:customerSourceName","customerStage.id:customerStageid",
    			"customerStage.name:customerStageName","customerStatus.id:customerStatusid","customerStatus.name:customerStatusName","owner.id:ownerid","owner.username:ownerUsername",
    			"owner.name:ownerName","gradeId","place.id:placeid","place.areaName:placeAreaName","place.showName:placeShowName","maritalId","liveArea.id:liveAreaid","liveArea.areaName:liveAreaAreaName",
    			"liveArea.showName:liveAreaShowName","livePlot.id:livePlotid","livePlot.plotName:livePlotPlotName","birthdate","company","occupation","spouseName",
    			"spousePlace.id:spousePlaceid","spousePlace.areaName:spousePlaceAreaName","spousePlace.showName:spousePlaceShowName","spouseMobilePhone","spouseIdNumber",
    			"spouseCompany","spouseOccupation","creditRatingId","creditRatingAttachId","lastTrackTime","photoId","id","note","lastModifyTime","createTime"};
        String jsonString = JSONUtil.toJson(list, properties,(long) pageM.getTotal());
        return jsonString;
    }
    
    /**
     * 
     * @description: 导出客户
     * @createTime: 2017年9月6日 上午11:38:54
     * @author: caw
     * @param m
     * @param titleExport
     * @param fieldNamesExport
     * @param fieldsExport
     * @param response
     */
    @RequestMapping(value = "exportCustomer")
	public void exportCustomer(Customer m, String titleExport, String fieldNamesExport, String fieldsExport,
			HttpServletResponse response){
    	ServiceResult result = new ServiceResult(false);
		if (!hasPermissionByOpt("export")) {
			result.setMessage("没有导出权限");
		}
		if (titleExport == null || titleExport.equals("")) {
			titleExport = "客户";
		}
		customerService.export(m, titleExport, fieldNamesExport, fieldsExport, response);
    }
    
    /**
     * 
     * @description: 统计
     * @createTime: 2017年9月7日 上午11:51:11
     * @author: caw
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "countCustomer")
    public Object countCustomer(Model model,Customer m){
    	int viewType = 0;
    	if(!hasPermissionByOpt("totalCustomer")){
        	if(hasPermissionByOpt("branchCustomer")){
        		viewType = 1;
        		User user = userService.getUserInfo(OperateUtils.getCurrentUserId());
        		m.setOwner(user);
        	}else if(hasViewPermission()){
        		viewType = 2;
        		m.setOwner(OperateUtils.getCurrentUser());
        	}else{
        		return  JSONUtil.EMPTYJSON;
        	}
        }
    	ServiceResult result = new ServiceResult(false);
    	result = customerService.countCustomer(viewType,m);
    	String jsonString = result.toJSON();
        return jsonString;
    }
    
    /**
     * 
     * @description: 修改信用等级
     * @createTime: 2017年9月5日 下午3:48:15
     * @author: caw
     * @param file
     * @param request
     * @param model
     * @param m
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "modCustomerCredit")
    public Object modCustomerCredit(@RequestParam(value="attachCreditName", required=false) MultipartFile file,HttpServletRequest request,Model model, Customer m){
    	ServiceResult result = new ServiceResult(false);
        if(!hasUpdatePermission()){
            result.setMessage("没有修改权限");
        }else{
        	result = customerService.modCustomerCredit(file, request, model, m);
        }
        String jsonString = result.toJSON();
        return jsonString;
    }
    
    /**
     * 
     * @description: 获取当前用户信息
     * @createTime: 2017年9月1日 下午3:40:42
     * @author: caw
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "userInfo")
    public Object userInfo(Model model,Long data){
    	User user = userService.userInfo();
    	String[] properties = { "id", "username","name"};
    	String jsonString = JSONUtil.toJson(user, properties);
        return jsonString;
    }

    /**
     * @description: 获取用户信息
     * @createTime: 2017年08月31日 2:23:58
     * @author: caw
     * @return
     */
    @RequestMapping(value = "getOwnerByList",method = RequestMethod.POST)
    @ResponseBody
    public Object getOwnerByList(int rows, int page, String userName){
    	Page<User> pageM= new Page<>(page,rows);
    	String[] properties = { "id", "username","name"};
        List<User> list = customerService.getOwnerByList(pageM,userName);
    	return JSONUtil.toJson(list, properties,(long) pageM.getTotal());
    }
    
    /**
     * @description: 获取户籍地信息
     * @createTime: 2017年08月31日 2:23:58
     * @author: caw
     * @return
     */
    @RequestMapping(value = "getPlaceByList",method = RequestMethod.POST)
    @ResponseBody
    public Object getPlaceByList(int rows, int page, String areaName){
    	Page<Area> pageM= new Page<>(page,rows);
    	String[] properties = {"province.id:provinceId","province.provinceName:provinceName","city.id:cityId","city.cityName:cityName","areaCode","areaName","showName","status","id"};
        List<Area> list = areaService.getPlaceByList(pageM,areaName);
    	return JSONUtil.toJson(list, properties,(long) pageM.getTotal());
    }
    
    /**
     * @description: 获取居住地区信息
     * @createTime: 2017年08月31日 2:23:58
     * @author: caw 
     * @param placeid
     * @return
     */
    @RequestMapping(value = "getLiveAreaByList",method = RequestMethod.POST)
    @ResponseBody
    public Object getLiveAreaByList(int rows, int page, String areaName){
    	Page<Area> pageM= new Page<>(page,rows);
    	String[] properties = {"province.id:provinceId","province.provinceName:provinceName","city.id:cityId","city.cityName:cityName","areaCode","areaName","showName","status","id"};
        List<Area> list = areaService.getPlaceByList(pageM,areaName);
    	return JSONUtil.toJson(list, properties,(long) pageM.getTotal());
    }
    
    /**
     * @description: 获取小区信息
     * @createTime: 2017年08月31日 2:23:58
     * @author: caw 
     * @param liveAreaid
     * @return
     */
    @RequestMapping(value = "getLivePlotByList",method = RequestMethod.POST)
    @ResponseBody
    public Object getLivePlotByList(Long liveAreaid,int rows, int page, String plotName){
    	Page<Plot> pageM= new Page<>(page,rows);
    	String[] properties = {"area.id:areaId","area.areaName:areaName","plotName","status","id","note"};
        List<Plot> list = plotService.getLivePlotByList(liveAreaid,pageM,plotName);
    	return JSONUtil.toJson(list, properties,(long) pageM.getTotal());
    }
    
    /**
     * 
     * @description: 根据客户id获取logo的src
     * @createTime: 2017年8月31日 下午4:32:48
     * @author: caw
     * @param model
     * @param request
     * @param customerid
     * @return
     */
    @RequestMapping(value = "imgSrc", method = RequestMethod.POST)
	@ResponseBody
    public Object imgSrc(Model model, HttpServletRequest request,Long customerid) {
    	ServiceResult result = new ServiceResult(false);
    	result = customerService.imgSrc(model, request, customerid);
    	return result;
    }
    
    /**
     * 
     * @description: 根据附件id获取logo的src
     * @createTime: 2017年9月4日 下午5:07:23
     * @author: caw
     * @param model
     * @param request
     * @param attachid
     * @return
     */
    @RequestMapping(value = "imgSrcAttach", method = RequestMethod.POST)
	@ResponseBody
    public Object imgSrcAttach(Model model, HttpServletRequest request,Long attachid) {
    	ServiceResult result = new ServiceResult(false);
    	result = customerService.imgSrcAttach(model, request, attachid);
    	return result;
    }
    
    /**
	 * @description: 获取图片
	 * @createTime: 2017年8月31日 下午4:27:26
	 * @author: caw
	 * @param attachId
	 * @param redirectAttributes
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/getImg" })
	public void getImg(Long attachId, RedirectAttributes redirectAttributes, HttpServletRequest request,
		HttpServletResponse response) {
		customerService.getImg(attachId, redirectAttributes, request, response);
	}
	
	/**
	 * 
	 * @description: 提取身份证中的出生日期
	 * @createTime: 2017年9月1日 下午4:55:57
	 * @author: caw
	 * @param idCard
	 * @return
	 */
	@RequestMapping(value = "getIdCardToBirthday", method = RequestMethod.POST)
	@ResponseBody
	public Object getIdCardToBirthday(String idCard){
		ServiceResult result = new ServiceResult(false);
		String birthday = AssistUtil.getBirthdayByIdCard(idCard);
		result.addData("birthday", birthday);
		String jsonString = result.toJSON();
		return jsonString;
	}
	
	/**
	 * 
	 * @description: 获取附件名称
	 * @createTime: 2017年10月9日 上午9:40:34
	 * @author: caw
	 * @param creditRatingAttachId
	 * @return
	 */
	@RequestMapping(value = "getAttachCName", method = RequestMethod.POST)
	@ResponseBody
	public Object getAttachCName(String creditRatingAttachId){
		ServiceResult result = new ServiceResult(false);
		Attach attach = attachService.selectById(creditRatingAttachId);
		result.addData("attachCName", attach.getName());
		String jsonString = result.toJSON();
		return jsonString;
	}
	
	/**
	 * @description: 分页查询个人客户
	 * @createTime: 2017年08月31日 10:49:27
	 * @author: caw
	 * @param model
	 * @param m
	 * @param rows
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "listPersonalByPage")
	public Object listPersonalByPage(Model model, Customer m, int rows, int page, String todayTime){
	    int viewType = 0;
	    if(hasPermissionByOpt("viewPersonal")){
	    	if(!hasPermissionByOpt("totalCustomer")){
	    		if(hasPermissionByOpt("branchCustomer")){
	        		viewType = 1;
	        		User user = userService.getUserInfo(OperateUtils.getCurrentUserId());
	        		m.setOwner(user);
	        	}else if(hasViewPermission()){
	        		viewType = 2;
	        		m.setOwner(OperateUtils.getCurrentUser());
	        	}else{
	        		return  JSONUtil.EMPTYJSON;
	        	}
	    	}
        }else{
        	return  JSONUtil.EMPTYJSON;
        }
        Page<Customer> pageM= new Page<>(page,rows);
        List<Customer> list = customerService.listCustomerByPage(pageM, m, todayTime, viewType);
		String[] properties = {"customerType","customerName","sex","mobilePhone","telephone",
			"email","idNumber","idAddress","customerSource.id:customerSourceid","customerSource.name:customerSourceName","customerStage.id:customerStageid",
			"customerStage.name:customerStageName","customerStatus.id:customerStatusid","customerStatus.name:customerStatusName","owner.id:ownerid","owner.username:ownerUsername",
			"owner.name:ownerName","gradeId","place.id:placeid","place.areaName:placeAreaName","place.showName:placeShowName","maritalId","liveArea.id:liveAreaid","liveArea.areaName:liveAreaAreaName",
			"liveArea.showName:liveAreaShowName","livePlot.id:livePlotid","livePlot.plotName:livePlotPlotName","birthdate","company","occupation","spouseName",
			"spousePlace.id:spousePlaceid","spousePlace.areaName:spousePlaceAreaName","spousePlace.showName","spouseMobilePhone","spouseIdNumber",
			"spouseCompany","spouseOccupation","creditRatingId","creditRatingAttachId","lastTrackTime","photoId","id","note","lastModifyTime","createTime"};
	    String jsonString = JSONUtil.toJson(list, properties,(long) pageM.getTotal());
	    return jsonString;
	}
	 
	/**
	 * @description: 分页查询企业客户
	 * @createTime: 2017年08月31日 10:49:27
	 * @author: caw
	 * @param model
     * @param m
	 * @param rows
	 * @param page
	 * @return
	 */
	 @ResponseBody
	 @RequestMapping(value = "listEnterpriseByPage")
	 public Object listEnterpriseByPage(Model model, Customer m, int rows, int page, String todayTime){
	     int viewType = 0;
	     if(hasPermissionByOpt("viewEnterprise")){
	    	 if(!hasPermissionByOpt("totalCustomer")){
	    		if(hasPermissionByOpt("branchCustomer")){
	        		viewType = 1;
	        		User user = userService.getUserInfo(OperateUtils.getCurrentUserId());
	        		m.setOwner(user);
	        	}else if(hasViewPermission()){
	        		viewType = 2;
	        		m.setOwner(OperateUtils.getCurrentUser());
	        	}else{
	        		return  JSONUtil.EMPTYJSON;
	        	}
	    	}
         }else{
        	return  JSONUtil.EMPTYJSON;
         }
         Page<Customer> pageM= new Page<>(page,rows);
         List<Customer> list = customerService.listCustomerByPage(pageM, m, todayTime, viewType);
	     String[] properties = {"customerType","customerName","sex","mobilePhone","telephone",
	    	"email","idNumber","idAddress","customerSource.id:customerSourceid","customerSource.name:customerSourceName","customerStage.id:customerStageid",
	    	"customerStage.name:customerStageName","customerStatus.id:customerStatusid","customerStatus.name:customerStatusName","owner.id:ownerid","owner.username:ownerUsername",
	    	"owner.name:ownerName","gradeId","place.id:placeid","place.areaName:placeAreaName","place.showName:placeShowName","maritalId","liveArea.id:liveAreaid","liveArea.areaName:liveAreaAreaName",
	    	"liveArea.showName:liveAreaShowName","livePlot.id:livePlotid","livePlot.plotName:livePlotPlotName","birthdate","company","occupation","spouseName",
	    	"spousePlace.id:spousePlaceid","spousePlace.areaName:spousePlaceAreaName","spousePlace.showName","spouseMobilePhone","spouseIdNumber",
	    	"spouseCompany","spouseOccupation","creditRatingId","creditRatingAttachId","lastTrackTime","photoId","id","note","lastModifyTime","createTime"};
	     String jsonString = JSONUtil.toJson(list, properties,(long) pageM.getTotal());
	     return jsonString;
	 }
	 
	/**
	 * @description: 分页查询今日更新
	 * @createTime: 2017年08月31日 10:49:27
	 * @author: caw
	 * @param model
     * @param m
	 * @param rows
	 * @param page
	 * @return
	 */
	 @ResponseBody
	 @RequestMapping(value = "listTodayUpdateByPage")
	 public Object listTodayUpdateByPage(Model model, Customer m, int rows, int page, String todayTime){
	     int viewType = 0;
	     if(hasPermissionByOpt("viewTodayUpdate")){
	    	 if(!hasPermissionByOpt("totalCustomer")){
	    		if(hasPermissionByOpt("branchCustomer")){
	        		viewType = 1;
	        		User user = userService.getUserInfo(OperateUtils.getCurrentUserId());
	        		m.setOwner(user);
	        	}else if(hasViewPermission()){
	        		viewType = 2;
	        		m.setOwner(OperateUtils.getCurrentUser());
	        	}else{
	        		return  JSONUtil.EMPTYJSON;
	        	}
	    	}
         }else{
        	return  JSONUtil.EMPTYJSON;
         }
         Page<Customer> pageM= new Page<>(page,rows);
         Date date = DateUtil.getNowTimestamp();
         todayTime = DateUtil.dateToString(date);
         List<Customer> list = customerService.listCustomerByPage(pageM, m, todayTime, viewType);
	     String[] properties = {"customerType","customerName","sex","mobilePhone","telephone",
	    	"email","idNumber","idAddress","customerSource.id:customerSourceid","customerSource.name:customerSourceName","customerStage.id:customerStageid",
	    	"customerStage.name:customerStageName","customerStatus.id:customerStatusid","customerStatus.name:customerStatusName","owner.id:ownerid","owner.username:ownerUsername",
	    	"owner.name:ownerName","gradeId","place.id:placeid","place.areaName:placeAreaName","place.showName:placeShowName","maritalId","liveArea.id:liveAreaid","liveArea.areaName:liveAreaAreaName",
	    	"liveArea.showName:liveAreaShowName","livePlot.id:livePlotid","livePlot.plotName:livePlotPlotName","birthdate","company","occupation","spouseName",
	    	"spousePlace.id:spousePlaceid","spousePlace.areaName:spousePlaceAreaName","spousePlace.showName","spouseMobilePhone","spouseIdNumber",
	    	"spouseCompany","spouseOccupation","creditRatingId","creditRatingAttachId","lastTrackTime","photoId","id","note","lastModifyTime","createTime"};
	     String jsonString = JSONUtil.toJson(list, properties,(long) pageM.getTotal());
	     return jsonString;
	 }
	 
	 /**
	  * @description:跟进订单Id获取客户
	  * @createTime 2017年9月14日 上午10:51:46
	  * @author xw
	  * @description: 
	  * @createTime: 2017年9月22日 上午8:19:28
	  * @author: caw
	  * @param orderId
	  * @param page
	  * @param rows
	  * @return
	  */
	 @ResponseBody
	 @RequestMapping(value = "listCustomerByOrder")
	 public Object listCustomerByOrder(Long orderId,int page,int rows){
		 Page<Customer> pageM=new Page<Customer>(page, rows);
		 pageM=customerService.listCustomerByOrder(pageM,orderId);
		 String[] properties = {"customerType","customerName","sex","mobilePhone","telephone",
	    			"email","idNumber","idAddress","customerSource.id:customerSourceid","customerSource.name:customerSourceName","customerStage.id:customerStageid",
	    			"customerStage.name:customerStageName","customerStatus.id:customerStatusid","customerStatus.name:customerStatusName","owner.id:ownerid","owner.username:ownerUsername",
	    			"owner.name:ownerName","gradeId","place.id:placeid","place.areaName:placeAreaName","place.showName:placeShowName","maritalId","liveArea.id:liveAreaid","liveArea.areaName:liveAreaAreaName",
	    			"liveArea.showName:liveAreaShowName","livePlot.id:livePlotid","livePlot.plotName:livePlotPlotName","birthdate","company","occupation","spouseName",
	    			"spousePlace.id:spousePlaceid","spousePlace.areaName:spousePlaceAreaName","spousePlace.showName","spouseMobilePhone","spouseIdNumber",
	    			"spouseCompany","spouseOccupation","creditRatingId","creditRatingAttachId","lastTrackTime","photoId","id","note","lastModifyTime","createTime"};
        String jsonString = JSONUtil.toJson(pageM.getRecords(), properties,(long) pageM.getTotal());
        return jsonString;
	 }
}
