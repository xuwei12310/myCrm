package com.xuwei.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.AssessmentCompany;
import com.xuwei.bean.Attach;
import com.xuwei.bean.Bank;
import com.xuwei.bean.BankSubbranch;
import com.xuwei.bean.Customer;
import com.xuwei.bean.Dict;
import com.xuwei.bean.OtherPartners;
import com.xuwei.bean.User;
import com.xuwei.service.IAssessmentCompanyService;
import com.xuwei.service.IAttachService;
import com.xuwei.service.IBankService;
import com.xuwei.service.IBankSubbranchService;
import com.xuwei.service.ICustomerService;
import com.xuwei.service.IDictService;
import com.xuwei.service.IOtherPartnersService;
import com.xuwei.service.IUserService;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.OperateUtils;

/**
 * @description:
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 9-5 10:13
 * @author：hhd
 * @version：1.0
 */
@Controller
@RequestMapping("/common/util/")
public class CommonController extends BaseController<String>{

    @Resource
    private IUserService userService;
    @Resource
    private ICustomerService customerService;
    @Resource
    private IDictService dictService;
    @Resource
    private IBankService bankService;
    @Resource
    private IBankSubbranchService bankSubbranchService;
    @Resource
    private IAssessmentCompanyService assessmentCompanyService;
    @Resource
    private IOtherPartnersService otherPartnersService;
    @Resource
    private IAttachService attachService;
    /**
     * @description: 获取用户信息
     * @createTime: 2017年9月5日 9:23:58
     * @author: hhd
     * @return
     */
    @RequestMapping(value = "getUserByList")
    @ResponseBody
    public Object getUserByList(int rows, int page,User user){
        Page<User> userPage=new Page<User>(page,rows);
        userService.findListByPageMap(user,null,userPage);
        String[] properties = { "id", "username","name"};
        return JSONUtil.toJson(userPage.getRecords(), properties, (long)userPage.getTotal());
    }

    /**
     * @description: 获取客户信息
     * @createTime: 2017年9月5日 9:23:58
     * @author: hhd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getCustomerByList")
    public Object getCustomerByList(Customer m, int rows, int page){
        setResourceIdentity("myWorkbench:totalCustomer");
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
        List<Customer> list = customerService.listCustomerByPage(pageM, m, null, viewType);
        String[] properties = {"customerName","id"};
        String jsonString = JSONUtil.toJson(list, properties,(long) pageM.getTotal());
        return jsonString;
    }

    /**
     * @description: 根据类型获取dict字典
     * @createTime: 2017年9月5日 14:23:58
     * @author: hhd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getDictByType")
    public Object getDictByType(String type){
        EntityWrapper<Dict> ewCustomer = new EntityWrapper<Dict>();
        ewCustomer.eq("dict_type",type);
        ewCustomer.eq("status",1);
        List<Dict> list = dictService.selectList(ewCustomer);
        String[] properties = {"name","id"};
        String jsonString = JSONUtil.toJson(list,properties);
        return jsonString;
    }
    
    /**
     * 
     * @description: 获取转交用户列表（出去当前拥有人）
     * @createTime: 2017年9月6日 上午10:12:42
     * @author: caw
     * @param rows
     * @param page
     * @param userName
     * @param transferUserId
     * @return
     */
    @RequestMapping(value = "getTransferUserByList")
    @ResponseBody
    public Object getTransferUserByList(int rows, int page, String userName, Long transferUserId){
    	Page<User> pageM= new Page<>(page,rows);
        String[] properties = { "id", "username","name"};
        List<User> list = userService.getTransferUserByList(pageM, userName, transferUserId);
    	return JSONUtil.toJson(list, properties,(long) pageM.getTotal());
    }
    /**
     * @description: 获取银行
     * @createTime: 2017年9月5日 14:23:58
     * @author: hhd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getBankByList")
    public Object getBankByList(){
        EntityWrapper<Bank> ewBank = new EntityWrapper<Bank>();
        ewBank.eq("status",1);
        List<Bank> list = bankService.selectList(ewBank);
        String[] properties = {"bankName","id"};
        String jsonString = JSONUtil.toJson(list, properties);
        return jsonString;
    }

    /**
     * @description: 根据银行id获取支行
     * @createTime: 2017年9月5日 14:23:58
     * @author: hhd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getBankSubbranchByList")
    public Object getBankSubbranchByList(int rows, int page, Long bankid,String subbranchName){
        EntityWrapper<BankSubbranch> ewBank = new EntityWrapper<BankSubbranch>();
        ewBank.eq("status",1);
        if(bankid!=null){
            ewBank.eq("bank_id",bankid);
        }
        if(StringUtils.isNotEmpty(subbranchName)){
            ewBank.like("subbranch_name",subbranchName);
        }
        Page<BankSubbranch> pageM= new Page<>(page,rows);
        bankSubbranchService.selectPage(pageM,ewBank);
        String[] properties = { "array", "id","bank.id:bankId", "subbranchName","bankCode","phone","contacts","address"};
        String jsonString = JSONUtil.toJson(pageM.getRecords(), properties,(long) pageM.getTotal());
        return jsonString;
    }

    /**
     *
     * @description: 获取当前用户信息
     * @createTime: 2017年9月5日 下午5:40:42
     * @author: hhd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getUserInfo")
    public Object getUserInfo(Long data){
        User user = userService.userInfo();
        String[] properties = { "id", "username","name"};
        String jsonString = JSONUtil.toJson(user, properties);
        return jsonString;
    }
    /**
	 * 
	 * @description:获取评估公司列表
	 * @createTime 2017年8月30日 上午10:37:58
	 * @author xw
	 * @param model
	 * @param m
	 * @param rows
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAssessmentList")
	public Object getAssessmentList(Model model, AssessmentCompany m, int rows, int page) {
		Page<AssessmentCompany> pageM = new Page<>(page, rows);
		EntityWrapper<AssessmentCompany> ew = new EntityWrapper<>(m);
		ew.orderBy("array", true);
		if (!StringUtils.isEmpty(m.getName())) {
			ew.like("name", m.getName());
		}
		if(m.getStatus()!=null){
			ew.eq("status", m.getStatus());
		}
		m.setName(null);
		pageM = assessmentCompanyService.selectPage(pageM, ew);
		String[] properties = { "name", "address","contacts","telephone","status", "note", "isSys", "array", "id" };
		String jsonString = JSONUtil.toJson(pageM.getRecords(), properties, (long) pageM.getTotal());
		return jsonString;
	}
	/**
	 * 
	 * @description:分页查询支行
	 * @createTime 2017年8月30日 下午4:39:36
	 * @author xw
	 * @param model
	 * @param m
	 * @param rows
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "listBranchByPage")
	public Object listBranchByPage(Model model, BankSubbranch m,Long bankId, int rows, int page) {
		Page<BankSubbranch> pageM = new Page<>(page, rows);
		EntityWrapper<BankSubbranch> ew = new EntityWrapper<>(m);
		ew.orderBy("array", true);
		if(bankId!=null){
			ew.eq("bank_id",bankId);
		}
		if(m.getSubbranchName()!=null){
			ew.eq("subbranch_name",m.getSubbranchName());
		}
		if(m.getStatus()!=null){
			ew.eq("status",m.getStatus());
		}
		m.setSubbranchName(null);
		pageM = bankSubbranchService.selectPage(pageM, ew);
		String[] properties = { "array", "id","bank.id:bankId", "subbranchName","bankCode","phone","contacts","address","note","status"};
		String jsonString = JSONUtil.toJson(pageM.getRecords(), properties, (long) pageM.getTotal());
		return jsonString;
	}

    /**
     *
     * @description: 根据用户id获取用户信息
     * @createTime: 2017年9月5日 下午5:40:42
     * @author: hhd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getUserNameById")
    public Object getUserNameById(Long id){
        if(id==null){
            return JSONUtil.EMPTY_COMBOBOX_JSON;
        }
        User user = userService.selectById(id);
        return user;
    }

    /**
     *
     * @description: 根据客户id获取客户信息
     * @createTime: 2017年9月5日 下午5:40:42
     * @author: hhd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getCustomerNameById")
    public Object getCustomerNameById(Long id){
        if(id==null){
            return JSONUtil.EMPTY_COMBOBOX_JSON;
        }
        Customer customer = customerService.selectById(id);
        return customer;
    }

    /**
     *
     * @description: 根据id获取支行
     * @createTime: 2017年9月5日 下午5:40:42
     * @author: hhd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getBranchById")
    public Object getBranchById(String id){
        if(id==null){
            return JSONUtil.EMPTY_COMBOBOX_JSON;
        }
        BankSubbranch bankSubbranch = bankSubbranchService.selectById(Long.valueOf(id));
        return bankSubbranch;
    }

    /**
     *
     * @description: 根据id获取合作伙伴
     * @createTime: 2017年9月5日 下午5:40:42
     * @author: hhd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getMatchmakerById")
    public Object getMatchmakerById(Long id){
        if(id==null){
            return JSONUtil.EMPTY_COMBOBOX_JSON;
        }
        OtherPartners otherPartners = otherPartnersService.selectById(id);
        return otherPartners;
    }

    /**
     *
     * @description: 获取合作伙伴
     * @createTime: 2017年9月14日 下午5:40:42
     * @author: hhd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getOtherPartners")
    public Object getOtherPartners(int rows, int page,String name){
        EntityWrapper<OtherPartners> ew = new EntityWrapper<>();
        ew.eq("status",1);
        if(StringUtils.isNotEmpty(name)){
            ew.eq("name",name);
        }
        Page<OtherPartners> pageM= new Page<>(page,rows);
        otherPartnersService.selectPage(pageM,ew);
        String[] properties = { "name", "address","telephone", "note", "id" };
        String jsonString = JSONUtil.toJson(pageM.getRecords(), properties,(long) pageM.getTotal());
        return jsonString;
    }

    /**
     *
     * @description: 获取评估公司
     * @createTime: 2017年9月14日 下午5:40:42
     * @author: hhd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getCompany")
    public Object getCompany(){
        List<AssessmentCompany> list = assessmentCompanyService.selectList(null);
        String[] properties = { "name","id" };
        String jsonString = JSONUtil.toJson(list,properties);
        return jsonString;
    }

    /**
     * 
     * @description: 获取附件信息
     * @createTime: 2017年10月16日 上午10:51:39
     * @author: caw
     * @param attachId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getAttach")
    public Object getAttach(Long attachId){
    	Attach attach = attachService.selectById(attachId);
    	String[] properties = { "name","id" };
    	String jsonString = JSONUtil.toJson(attach,properties);
        return jsonString;
    }

    /**
     *
     * @description: 获取当前用户信息
     * @createTime: 2017年10月18日 下午5:40:42
     * @author: hhd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getUserByOrganization")
    public Object getUserByOrganization(Long organizationId){
        EntityWrapper<User> ew=new EntityWrapper<User>();
        if (organizationId!=null){
            ew.eq("organization_id",organizationId);
        }
        List<User> list = userService.selectList(ew);
        String[] properties = { "id", "username","name"};
        String jsonString = JSONUtil.toJson(list, properties);
        return jsonString;
    }
}
