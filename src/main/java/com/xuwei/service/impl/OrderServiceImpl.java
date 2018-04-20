package com.xuwei.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.BankSubbranch;
import com.xuwei.bean.CustomerProperty;
import com.xuwei.bean.Dict;
import com.xuwei.bean.Order;
import com.xuwei.bean.OrderBank;
import com.xuwei.bean.OrderCompany;
import com.xuwei.bean.OrderIncome;
import com.xuwei.bean.OrderMaterial;
import com.xuwei.bean.OrderProperty;
import com.xuwei.bean.OrderSchedule;
import com.xuwei.bean.Pay;
import com.xuwei.bean.RelatedMaterials;
import com.xuwei.bean.Schedule;
import com.xuwei.mapper.CustomerMapper;
import com.xuwei.mapper.DictMapper;
import com.xuwei.mapper.OrderBankMapper;
import com.xuwei.mapper.OrderCompanyMapper;
import com.xuwei.mapper.OrderIncomeMapper;
import com.xuwei.mapper.OrderMapper;
import com.xuwei.mapper.OrderScheduleMapper;
import com.xuwei.mapper.PayMapper;
import com.xuwei.mapper.RelatedMaterialsMapper;
import com.xuwei.mapper.ScheduleMapper;
import com.xuwei.mapper.UserMapper;
import com.xuwei.service.ICustomerPropertyService;
import com.xuwei.service.IMessageService;
import com.xuwei.service.IOrderMaterialService;
import com.xuwei.service.IOrderPropertyService;
import com.xuwei.service.IOrderScheduleService;
import com.xuwei.service.IOrderService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;

/**
 * @description: 订单服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月05日 09:22:53
 * @author: hhd
 * @version: 1.0
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
	@Resource
	private OrderMapper orderMapper;
	@Resource
	private CustomerMapper customerMapper;
	@Resource
	private UserMapper userMapper;
	@Resource
	private DictMapper dictMapper;
	@Resource
	private OrderCompanyMapper orderCompanyMapper;
	@Resource
	private OrderIncomeMapper orderIncomeMapper;
	@Resource
	private PayMapper payMapper;
	@Resource
	private OrderBankMapper orderBankMapper;
	@Resource
	private IOrderMaterialService orderMaterialService;
	@Resource
	private OrderScheduleMapper orderScheduleMapper;
	@Resource
	private RelatedMaterialsMapper relatedMaterialsMapper;
	@Resource
	private ScheduleMapper scheduleMapper;
	@Resource
	private IOrderScheduleService orderScheduleService;
	@Resource
	private IMessageService messageService;
	@Resource
	private ICustomerPropertyService customerPropertyService;
	@Resource
	private IOrderPropertyService orderPropertyService;

	@Override
	public List<Order> orderCombobox(Long userId) {
		return orderMapper.orderCombobox(userId);
	}
	@Override
	public Page<Order> listOrderBalanceByPage(Page<Order> pageM, Order m,EntityWrapper<Order> ew) {
		List<Order> list=orderMapper.listOrderBalanceByPage(pageM,m,ew);
		pageM.setRecords(list);
		return pageM;
	}
	@Override
	@Transactional
	public ServiceResult saveOrderBalance(Order m) {
		ServiceResult result = new ServiceResult(false);
		// 添加收入项手续费
		Order order = orderMapper.selectById(m.getId());
		if(order.getSettlementAuditStatus()==null){
		OrderIncome oi = new OrderIncome();
		Dict incomeCostType = new Dict();
		incomeCostType.setId(166L);
		oi.setCostType(incomeCostType);
		oi.setEstimateAmount(order.getServiceCharge());
		oi.setReceiveAmount(order.getServiceCharge());
		oi.setReceivedAmount(new BigDecimal(0));
		oi.setReceivingAmount(order.getServiceCharge());
		oi.setOrder(order);
		oi.setCreatorId(OperateUtils.getCurrentUserId());
		oi.setCreateTime(DateUtil.getNowTimestampStr());
		oi.setLastModifierId(OperateUtils.getCurrentUserId());
		oi.setLastModifyTime(DateUtil.getNowTimestampStr());
		orderIncomeMapper.insert(oi);
		// 添加收入项其他
		OrderIncome oi2 = new OrderIncome();
		Dict incomeCostType2 = new Dict();
		incomeCostType2.setId(165L);
		oi2.setCostType(incomeCostType2);
		oi2.setOrder(order);
		oi2.setCreatorId(OperateUtils.getCurrentUserId());
		oi2.setCreateTime(DateUtil.getNowTimestampStr());
		oi2.setLastModifierId(OperateUtils.getCurrentUserId());
		oi2.setLastModifyTime(DateUtil.getNowTimestampStr());
		orderIncomeMapper.insert(oi2);
		// 添加支出项 评估费
		List<OrderCompany> list = orderCompanyMapper.selectAssessmentCompany(m.getId(), 1);
		for (OrderCompany oc : list) {
			Pay pay = new Pay();
			if (oc != null) {
				pay.setAssessmentCompany(oc.getAssessmentCompany());
				pay.setEstimatePayAmount(oc.getAssessmenFee());
				pay.setPayAmount(oc.getAssessmenFee());
			}
			Dict payCostType = new Dict();
			payCostType.setId(161L);
			pay.setCostType(payCostType);
			pay.setPayObjectType(1);
			pay.setIsCost(1);
			pay.setOrder(oc.getOrder());
			pay.setSource(1);
			//pay.setAuditStatus(1);
			pay.setSubmitDate(DateUtil.getNowTimestampStr());
			pay.setSubmitUser(OperateUtils.getCurrentUser());
			pay.setCreatorId(OperateUtils.getCurrentUserId());
			pay.setCreateTime(DateUtil.getNowTimestampStr());
			pay.setLastModifierId(OperateUtils.getCurrentUserId());
			pay.setLastModifyTime(DateUtil.getNowTimestampStr());
			payMapper.insert(pay);
		}
		// 添加支出项 工本费
		List<OrderCompany> list2 = orderCompanyMapper.selectAssessmentCompany(m.getId(), 0);
		for (OrderCompany oc : list2) {
			Pay pay = new Pay();
			if (oc != null) {
				pay.setAssessmentCompany(oc.getAssessmentCompany());
				pay.setEstimatePayAmount(oc.getFee());
				pay.setPayAmount(oc.getFee());
			}
			Dict payCostType = new Dict();
			payCostType.setId(162L);
			pay.setCostType(payCostType);
			pay.setPayObjectType(1);
			pay.setIsCost(1);
			pay.setOrder(oc.getOrder());
			pay.setSource(1);
			//pay.setAuditStatus(1);
			pay.setSubmitDate(DateUtil.getNowTimestampStr());
			pay.setSubmitUser(OperateUtils.getCurrentUser());
			pay.setCreatorId(OperateUtils.getCurrentUserId());
			pay.setCreateTime(DateUtil.getNowTimestampStr());
			pay.setLastModifierId(OperateUtils.getCurrentUserId());
			pay.setLastModifyTime(DateUtil.getNowTimestampStr());
			payMapper.insert(pay);
		}
		// 添加支出项 业务费
		List<OrderBank> list3 = orderBankMapper.selectByOrderId(m.getId());
		for (OrderBank orderBank : list3) {
			Pay pay = new Pay();
			BankSubbranch bs = new BankSubbranch();
			bs.setId(orderBank.getSubbranch().getId());
			pay.setBankSubbranch(bs);
			Dict payCostType = new Dict();
			payCostType.setId(163L);
			pay.setCostType(payCostType);
			pay.setPayObjectType(2);
			pay.setIsCost(1);
			pay.setOrder(orderBank.getOrder());
			pay.setSource(1);
			//pay.setAuditStatus(1);
			pay.setSubmitDate(DateUtil.getNowTimestampStr());
			pay.setSubmitUser(OperateUtils.getCurrentUser());
			pay.setCreatorId(OperateUtils.getCurrentUserId());
			pay.setCreateTime(DateUtil.getNowTimestampStr());
			pay.setLastModifierId(OperateUtils.getCurrentUserId());
			pay.setLastModifyTime(DateUtil.getNowTimestampStr());
			payMapper.insert(pay);
		}
		// 添加支出项 返佣
		if(oi.getReceiveAmount()!=null&&order.getCommissionRate()!=null){
			Pay pay = new Pay();
			Dict payCostType = new Dict();
			payCostType.setId(164L);
			pay.setCostType(payCostType);
			pay.setPayObjectType(3);
			if(order.getMatchmaker()!=null){
				pay.setOtherPartners(order.getMatchmaker());
			}
			pay.setIsCost(1);
			pay.setOrder(order);
			pay.setEstimatePayAmount(oi.getReceiveAmount().multiply(order.getCommissionRate()));
			pay.setPayAmount(oi.getReceiveAmount().multiply(order.getCommissionRate()));
			pay.setSource(1);
			//pay.setAuditStatus(1);
			pay.setSubmitDate(DateUtil.getNowTimestampStr());
			pay.setSubmitUser(OperateUtils.getCurrentUser());
			pay.setCreatorId(OperateUtils.getCurrentUserId());
			pay.setCreateTime(DateUtil.getNowTimestampStr());
			pay.setLastModifierId(OperateUtils.getCurrentUserId());
			pay.setLastModifyTime(DateUtil.getNowTimestampStr());
			payMapper.insert(pay);
		}
		// 添加支出项 其他
		Pay pay2 = new Pay();
		Dict payCostType2 = new Dict();
		payCostType2.setId(165L);
		pay2.setCostType(payCostType2);
	    pay2.setPayObjectType(3);
		pay2.setIsCost(1);
		pay2.setOrder(order);
		pay2.setSource(1);
		//pay2.setAuditStatus(1);
		pay2.setSubmitDate(DateUtil.getNowTimestampStr());
		pay2.setSubmitUser(OperateUtils.getCurrentUser());
		pay2.setCreatorId(OperateUtils.getCurrentUserId());
		pay2.setCreateTime(DateUtil.getNowTimestampStr());
		pay2.setLastModifierId(OperateUtils.getCurrentUserId());
		pay2.setLastModifyTime(DateUtil.getNowTimestampStr());
		payMapper.insert(pay2);
		// 修改订单审核状态为草稿
		order.setSettlementAuditStatus(1);
		order.setSettlementSubmitDate(DateUtil.getNowTimesminutStr());
		order.setSettlementSubmitUser(OperateUtils.getCurrentUser());
		order.setLastModifierId(OperateUtils.getCurrentUserId());
		order.setLastModifyTime(DateUtil.getNowTimestampStr());
		orderMapper.updateAllColumnById(order);
		result.setIsSuccess(true);
		return result;
		}else{
			result.setMessage("该订单结算已存在，无法添加");
			return result;
		}
	}

	@Override
	public Page<Order> selectPage(Page<Order> page, Wrapper<Order> wrapper, int type) {
		Long userid =OperateUtils.getCurrentUserId();
		List<Order> list = orderMapper.selectPageByType(page,wrapper,userid,type);
		page.setRecords(list);
		return page;
	}

	@Transactional
	@Override
	public ServiceResult createOrder(Order m) {
		ServiceResult result = new ServiceResult(false);
		String currentTime = DateUtil.getNowTimestampStr("yyyy-MM-dd");
		EntityWrapper<Order> ew = new EntityWrapper<>();
		ew.between("create_time",currentTime+" 00:00:00",currentTime+" 23:59:59");
		ew.orderBy("create_time",false);
		List<Order> list = orderMapper.selectList(ew);
		String orderCode = null;
		if(list.size()>0){
			orderCode =  String.valueOf(Long.valueOf(list.get(0).getOrderCode())+1);
		}else {
			String[] time=currentTime.split("-");
			orderCode = time[0]+time[1]+time[2]+"01";
		}
		m.setAuditStatus(1);
		m.setBrokerageGrantState(0);
		m.setOrderCode(orderCode);
		m.setCreatorId(OperateUtils.getCurrentUserId());
		m.setCreateTime(DateUtil.getNowTimestampStr());
		m.setLastModifierId(OperateUtils.getCurrentUserId());
		m.setLastModifyTime(DateUtil.getNowTimestampStr());
		int affect = orderMapper.insert(m);
		if(affect>0){
			EntityWrapper<RelatedMaterials> ewRelated = new EntityWrapper<>();
			ewRelated.eq("status",1);
			List<RelatedMaterials> listMaterials = relatedMaterialsMapper.selectList(ewRelated);
			List<OrderMaterial> listOrderMaterial = new ArrayList<>();
			for (RelatedMaterials item : listMaterials){
				OrderMaterial om=new OrderMaterial();
				om.setOrder(m);
				om.setMaterialId(item.getId());
				om.setName(item.getName());
				om.setNumber(item.getNumber());
				om.setNotice(item.getNote());
				om.setIsFinish(0);
				om.setCreatorId(OperateUtils.getCurrentUserId());
				om.setCreateTime(DateUtil.getNowTimestampStr());
				om.setLastModifierId(OperateUtils.getCurrentUserId());
				om.setLastModifyTime(DateUtil.getNowTimestampStr());
				listOrderMaterial.add(om);
			}
			if(listOrderMaterial.size()>0){
				orderMaterialService.insertBatch(listOrderMaterial);
			}
			EntityWrapper<Schedule> ewSchedule = new EntityWrapper<>();
			ewSchedule.eq("product_id",m.getProduct().getId());
			List<Schedule> listSchedule = scheduleMapper.selectList(ewSchedule);
			List<OrderSchedule> listOrderSchedule = new ArrayList<>();
			for (Schedule item:listSchedule){
				OrderSchedule os = new OrderSchedule();
				Order order=new Order();
				order.setId(m.getId());
				os.setOrder(order);
				Schedule schedule=new Schedule();
				schedule.setId(item.getId());
				os.setSchedule(schedule);
				try {
					os.setEstimateDate(DateUtil.dateToString(DateUtil.addDay(DateUtil.toDate(m.getCreateTime()),item.getDuration())));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				os.setIsComplete(0);
				os.setActualDate(os.getEstimateDate());
				os.setCreatorId(OperateUtils.getCurrentUserId());
				os.setCreateTime(DateUtil.getNowTimestampStr());
				os.setLastModifierId(OperateUtils.getCurrentUserId());
				os.setLastModifyTime(DateUtil.getNowTimestampStr());
				listOrderSchedule.add(os);
			}
			if(listOrderSchedule.size()>0){
				orderScheduleService.insertBatch(listOrderSchedule);
			}
			messageService.addOrderScheduleMessage(m.getId());

			EntityWrapper<CustomerProperty> ewcp = new EntityWrapper<>();
			ewcp.eq("customer_id",m.getCustomer().getId());
			List<CustomerProperty> listProperty = customerPropertyService.selectList(ewcp);
			List<OrderProperty> listOrderProperty = new ArrayList<>();
			for (CustomerProperty cp:listProperty){
				OrderProperty op=new OrderProperty();
				op.setOrder(m);
				op.setCustomerPropertyId(cp.getId());
				op.setCreatorId(OperateUtils.getCurrentUserId());
				op.setCreateTime(DateUtil.getNowTimestampStr());
				op.setLastModifierId(OperateUtils.getCurrentUserId().toString());
				op.setLastModifyTime(DateUtil.getNowTimestampStr());
				listOrderProperty.add(op);
			}
			if(listOrderProperty.size()>0){
				orderPropertyService.insertBatch(listOrderProperty);
			}
			result.addData("oid",m.getId());
			result.setIsSuccess(true);
		}
		result.setIsSuccess(true);
		return result;
	}

	/**
	 * 获取订单信息（微信端）
	 */
	@Override
	public ServiceResult getOrderList(String searchValue, Page<Order> page, Long userId, Long totalNum,
			Long searchType) {
		ServiceResult result = new ServiceResult(false);
		List<Order> list = null;
		Date date = DateUtil.getNowTimestamp();
		String currentTime = DateUtil.dateToString(date);
		if(searchType==1){            //查询全部
			list = orderMapper.getOrderList(searchValue, page,userId);
		}else if(searchType==2){      //查询今日更新
			list = orderMapper.getOrderTodayUpdateList(searchValue, page, currentTime,userId);
		}else if(searchType==3){      //查询近一周
			list = orderMapper.getOrderNearlyAWeekList(searchValue, page, currentTime,userId);
		}else if(searchType==4){      //查询近一个月
			list = orderMapper.getOrderNearlyAMonthList(searchValue, page, currentTime,userId);
		}
		if(page.getTotal()==totalNum && totalNum!=0){
			List<Order> lists = new ArrayList<Order>();
			result.addData("orderlist", lists);
			result.setIsSuccess(true);
			return result;
		}
		result.addData("orderlist", list);
		result.setIsSuccess(true);
		return result;
	}
	
	/**
	 * 添加订单（微信端）
	 */
	@Transactional
	@Override
	public ServiceResult addOrder(Order m, Long userId) {
		ServiceResult result = new ServiceResult(false);
		if(m.getCustomer()==null){
			result.setMessage("客户不能为空");
			return result;
		}
		if(m.getProduct()==null){
			result.setMessage("产品不能为空");
			return result;
		}
		if(m.getEstimateFinishTime()==null){
			result.setMessage("预计完成时间不能为空");
			return result;
		}
		if(m.getOwner()==null){
			result.setMessage("拥有人不能为空");
			return result;
		}
		if(m.getFollowUser()==null){
			result.setMessage("跟单人不能为空");
			return result;
		}
		if(m.getCsAssistant()==null){
			result.setMessage("客服负责人不能为空");
			return result;
		}
		if(m.getCsPrincipal()==null){
			result.setMessage("客服助理不能为空");
			return result;
		}
		String currentTime = DateUtil.getNowTimestampStr("yyyy-MM-dd");
		EntityWrapper<Order> ew = new EntityWrapper<>();
		ew.between("create_time",currentTime+" 00:00:00",currentTime+" 23:59:59");
		ew.orderBy("create_time",false);
		List<Order> list = orderMapper.selectList(ew);
		String orderCode = null;
		if(list.size()>0){
			orderCode =  String.valueOf(Long.valueOf(list.get(0).getOrderCode())+1);
		}else {
			String[] time=currentTime.split("-");
			orderCode = time[0]+time[1]+time[2]+"01";
		}
		m.setAuditStatus(1);
		m.setOrderCode(orderCode);
		m.setCreatorId(userId);
		m.setCreateTime(DateUtil.getNowTimestampStr());
		m.setLastModifierId(userId);
		m.setLastModifyTime(DateUtil.getNowTimestampStr());
		int affect = orderMapper.insert(m);
		if(affect>0){
			EntityWrapper<RelatedMaterials> ewRelated = new EntityWrapper<>();
			ewRelated.eq("status",1);
			List<RelatedMaterials> listMaterials = relatedMaterialsMapper.selectList(ewRelated);
			List<OrderMaterial> listOrderMaterial = new ArrayList<>();
			for (RelatedMaterials item : listMaterials){
				OrderMaterial om=new OrderMaterial();
				om.setOrder(m);
				om.setMaterialId(item.getId());
				om.setName(item.getName());
				om.setNumber(item.getNumber());
				om.setNotice(item.getNote());
				om.setIsFinish(0);
				om.setCreatorId(OperateUtils.getCurrentUserId());
				om.setCreateTime(DateUtil.getNowTimestampStr());
				om.setLastModifierId(OperateUtils.getCurrentUserId());
				om.setLastModifyTime(DateUtil.getNowTimestampStr());
				listOrderMaterial.add(om);
			}
			if(listOrderMaterial.size()>0){
				orderMaterialService.insertBatch(listOrderMaterial);
			}
			EntityWrapper<Schedule> ewSchedule = new EntityWrapper<>();
			ewSchedule.eq("product_id",m.getProduct().getId());
			List<Schedule> listSchedule = scheduleMapper.selectList(ewSchedule);
			List<OrderSchedule> listOrderSchedule = new ArrayList<>();
			for (Schedule item:listSchedule){
				OrderSchedule os = new OrderSchedule();
				Order order=new Order();
				order.setId(m.getId());
				os.setOrder(order);
				Schedule schedule=new Schedule();
				schedule.setId(item.getId());
				os.setSchedule(schedule);
				try {
					os.setEstimateDate(DateUtil.dateToString(DateUtil.addDay(DateUtil.toDate(m.getCreateTime()),item.getDuration())));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				os.setIsComplete(0);
				os.setCreatorId(userId);
				os.setCreateTime(DateUtil.getNowTimestampStr());
				os.setLastModifierId(userId);
				os.setLastModifyTime(DateUtil.getNowTimestampStr());
				listOrderSchedule.add(os);
			}
			if(listOrderSchedule.size()>0){
				orderScheduleService.insertBatch(listOrderSchedule);
			}
			messageService.addOrderScheduleMessage(m.getId());

			EntityWrapper<CustomerProperty> ewcp = new EntityWrapper<>();
			ewcp.eq("customer_id",m.getCustomer().getId());
			List<CustomerProperty> listProperty = customerPropertyService.selectList(ewcp);
			List<OrderProperty> listOrderProperty = new ArrayList<>();
			for (CustomerProperty cp:listProperty){
				OrderProperty op=new OrderProperty();
				op.setOrder(m);
				op.setCustomerPropertyId(cp.getId());
				op.setCreatorId(OperateUtils.getCurrentUserId());
				op.setCreateTime(DateUtil.getNowTimestampStr());
				op.setLastModifierId(OperateUtils.getCurrentUserId().toString());
				op.setLastModifyTime(DateUtil.getNowTimestampStr());
				listOrderProperty.add(op);
			}
			if(listOrderProperty.size()>0){
				orderPropertyService.insertBatch(listOrderProperty);
			}
			result.addData("oid",m.getId());
			result.setIsSuccess(true);
		}
		result.setIsSuccess(true);
		return result;
	}

	/**
	 * 根据id获取订单详细信息（微信端）
	 */
	@Override
	public Order getOrderInfo(Long orderid) {
		return orderMapper.getOrderInfo(orderid);
	}

	/**
	 * 修改订单信息（微信端）
	 */
	@Override
	public ServiceResult modOrder(Order m, Long userId) {
		ServiceResult result = new ServiceResult(false);
		if(m.getEstimateFinishTime()==null){
			result.setMessage("预计完成时间不能为空");
			return result;
		}
		if(m.getOwner()==null){
			result.setMessage("拥有人不能为空");
			return result;
		}
		if(m.getFollowUser()==null){
			result.setMessage("跟单人不能为空");
			return result;
		}
		if(m.getCsAssistant()==null){
			result.setMessage("客服负责人不能为空");
			return result;
		}
		if(m.getCsPrincipal()==null){
			result.setMessage("客服助理不能为空");
			return result;
		}
		Order order = orderMapper.selectById(m.getId());
		if(order.getAuditStatus()!=1){
			result.setMessage("非草稿状态不能修改");
			return result;
		}
    	order.setEstimateFinishTime(m.getEstimateFinishTime());
		order.setOwner(m.getOwner());
		order.setOwner(m.getFollowUser());
		order.setCsPrincipal(m.getCsPrincipal());
		order.setCsAssistant(m.getCsAssistant());
    	order.setLastModifierId(userId);
    	order.setLastModifyTime(DateUtil.getNowTimestampStr());
    	int data = orderMapper.updateAllColumnById(order);
    	if(data>0){
			result.addData("orderid", m.getId());
			result.setIsSuccess(true);
		}else{
			result.setMessage("没修改记录");	
		}
    	return result;
	}
	@Override
	@Transactional
	public boolean deleteBalanceByIds(String[] idArray) {
		int count=orderMapper.deleteBalanceByIds(idArray);
		for (String id : idArray) {
			EntityWrapper<OrderIncome> ew=new EntityWrapper<OrderIncome>();
			ew.eq("order_id", id);
			orderIncomeMapper.delete(ew);
			EntityWrapper<Pay> ew2=new EntityWrapper<Pay>();
			ew2.eq("order_id", id);
			ew2.eq("source", 1);
			payMapper.delete(ew2);
		}
		return count > 0;
	}

	@Override
	public ServiceResult updateLoan(Order m, Long userId) {
		ServiceResult result = new ServiceResult(false);
		Order order = orderMapper.selectById(m.getId());
		if(m.getLoanAmount()==null){
			result.setMessage("贷款金额不能为空");
			return result;
		}
		if(m.getLendingRate()==null){
			result.setMessage("月利率不能为空");
			return result;
		}
		if(m.getLoanTerm()==null){
			result.setMessage("贷款年限不能为空");
			return result;
		}
		if(m.getRepayWay()==null){
			result.setMessage("还款方式不能为空");
			return result;
		}
		if(m.getServiceChargePercent()==null){
			result.setMessage("手续费%不能为空");
			return result;
		}
		if(m.getServiceCharge()==null){
			result.setMessage("手续费不能为空");
			return result;
		}
		if(order.getAuditStatus()!=1){
			result.setMessage("非草稿状态不能为空");
			return result;
		}
		order.setBank(m.getBank());
		order.setBranch(m.getBranch());
		order.setLoanAmount(m.getLoanAmount());
		order.setLendingRate(m.getLendingRate());
		order.setLoanTerm(m.getLoanTerm());
		order.setRepayWay(m.getRepayWay());
		order.setServiceCharge(m.getServiceCharge());
		order.setServiceChargePercent(m.getServiceChargePercent());
		order.setMatchmaker(m.getMatchmaker());
		order.setCommissionRate(m.getCommissionRate());
		order.setCommissionAmount(m.getCommissionAmount());
		order.setCommissionReason(m.getCommissionReason());
		order.setLastModifierId(userId);
		order.setLastModifyTime(DateUtil.getNowTimestampStr());
		int data = orderMapper.updateAllColumnById(order);
		if(data>0){
			result.addData("orderid", m.getId());
			result.setIsSuccess(true);
		}else{
			result.setMessage("没修改记录");
		}
		return result;
	}

	@Override
	public ServiceResult updateLending(Order m, Long userId) {
		ServiceResult result = new ServiceResult(false);
		Order order = orderMapper.selectById(m.getId());
		if(m.getLoanMoney()==null){
			result.setMessage("放款金额不能为空");
			return result;
		}
		if(m.getLoanDate()==null){
			result.setMessage("放款日期不能为空");
			return result;
		}
		if(m.getServiceChargePercent()==null){
			result.setMessage("手续费%不能为空");
			return result;
		}
		if(m.getServiceCharge()==null){
			result.setMessage("手续费不能为空");
			return result;
		}
		order.setLoanMoney(m.getLoanMoney());
		order.setLoanDate(m.getLoanDate());
		order.setServiceChargePercent(m.getServiceChargePercent());
		order.setServiceCharge(m.getServiceCharge());
		order.setLastModifierId(userId);
		order.setLastModifyTime(DateUtil.getNowTimestampStr());
		int data = orderMapper.updateAllColumnById(order);
		if(data>0){
			result.addData("orderid", m.getId());
			result.setIsSuccess(true);
		}else{
			result.setMessage("没修改记录");
		}
		return result;
	}
	/**
	 * 获取订单佣金信息
	 */
	@Override
	public List<Order> getOrderBrokerageList(Page<Order> pageM, Order m, int viewType, Long userid) {
		return orderMapper.getOrderBrokerageList(pageM, m, viewType, userid);
	}
	/**
	 * 客户获取订单信息（微信端）
	 */
	@Override
	public ServiceResult getOrderList(Page<Order> page, Long userId, Long totalNum) {
		ServiceResult result = new ServiceResult(false);
		List<Order> list = orderMapper.getCustomerOrderList(page, userId);
		if(page.getTotal()==totalNum && totalNum!=0){
			List<Order> lists = new ArrayList<Order>();
			result.addData("orderlist", lists);
			result.setIsSuccess(true);
			return result;
		}
		result.addData("orderlist", list);
		result.setIsSuccess(true);
		return result;
	}

}
