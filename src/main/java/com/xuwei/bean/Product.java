package com.xuwei.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * @description: 
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年12月13日 10:12:59
 * @author: xw
 * @version: 1.0
 */
@TableName("PRODUCT")
@KeySequence("SEQ_PRODUCT_ID")
public class Product extends Model<Product> {

    private static final long serialVersionUID = 1L;
    
    @TableId(value="ID",type=IdType.INPUT)
	private Long id;
    @TableField("PRODUCTNAME")
	private String productName;
    @TableField("PRICE")
	private BigDecimal price;
    @TableField("DETAILS")
    private String details;
    @TableField("CREATETIME")
	private String createTime;


	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
