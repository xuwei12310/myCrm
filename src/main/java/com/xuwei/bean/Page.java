package com.xuwei.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

/**
 * 分页对应的实体类
 */
public class Page<T> extends RowBounds{
	/**
	 * 总条数
	 */
	private Long totalNumber;
	/**
	 * 当前第几页
	 */
	private Long currentPage= 1l;
	/**
	 * 总页数
	 */
	private Long totalPage;
	/**
	 * 每页显示条数
	 */
	private int pageNumber = 20;
	/**
	 * 数据库中limit的参数，从第几条开始取
	 */
	private int dbIndex;

	/**
	 * 数据库中limit的参数，一共取多少条
	 */
	private int dbNumber;
	
	private List<T> content = new ArrayList<T>();
	
	public Page() {
	}
	
	public Page(List<T> content,Long totalNumber, Long currentPage, Long totalPage) {
		this.content.addAll(content);
		this.totalNumber = totalNumber;
		this.currentPage = currentPage;
		this.totalPage = totalPage;
	}
	/**
	 * 根据当前对象中属性值计算并设置相关属性值
	 */
	public void count() {
		// 计算总页数
		Long totalPageTemp = this.totalNumber / this.pageNumber;
		Long plus = (this.totalNumber % this.pageNumber) == 0 ? 0l : 1;
		totalPageTemp = totalPageTemp + plus;
		if(totalPageTemp <= 0) {
			totalPageTemp = 1l;
		}
		this.totalPage = totalPageTemp;
		
		// 设置当前页数
		// 总页数小于当前页数，应将当前页数设置为总页数
		if(this.totalPage < this.currentPage) {
			this.currentPage = this.totalPage;
		}
		// 当前页数小于1设置为1
		if(this.currentPage < 1) {
			this.currentPage = 1l;
		}
		
		// 设置limit的参数
		this.dbIndex = (int) ((this.currentPage - 1) * this.pageNumber);
		this.dbNumber = this.pageNumber;
	}

	public Long getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Long totalNumber) {
		this.totalNumber = totalNumber;
		this.count();
	}

	public Long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Long currentPage) {
		this.currentPage = currentPage;
	}

	public Long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Long totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
		this.count();
	}

	public int getDbIndex() {
		return dbIndex;
	}

	public void setDbIndex(int dbIndex) {
		this.dbIndex = dbIndex;
	}

	public int getDbNumber() {
		return dbNumber;
	}

	public void setDbNumber(int dbNumber) {
		this.dbNumber = dbNumber;
	}

	public List<T> getContent() {
		return content;
	}
	
	public void setPage(Long page) {
		this.currentPage = page;
	}
	public void setRows(int rows) {
		this.pageNumber = rows;
	}
	public void setContent(List<T> content) {
		this.content = content;
	}
}
