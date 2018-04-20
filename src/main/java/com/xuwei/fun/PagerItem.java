package com.xuwei.fun;

public class PagerItem {

	public PagerItem() {

	}

	public PagerItem(Integer pPageSize) {
		this.setPageSize(pPageSize);
		reset();
	}

	public PagerItem(Integer pPageSize, Integer pPageIndex) {
		this.setPageSize(pPageSize);
		this.setPageIndex(pPageIndex);
		reset();
	}

	public PagerItem(Integer pPageSize, Integer pPageIndex, Integer pRowCount) {
		this.setPageSize(pPageSize);
		this.setPageIndex(pPageIndex);
		this.setRowCount(pRowCount);
		reset();
	}

	public PagerItem changePageSize(Integer pPageSize) {
		setPageSize(pPageSize);
		return reset();
	}

	public PagerItem changePageIndex(Integer pPageIndex) {
		setPageIndex(pPageIndex);
		return reset();
	}

	public PagerItem changeRowCount(Integer pRowCount) {
		setRowCount(pRowCount);

		return reset();
	}

	public PagerItem reset() {
		return resetPager().resetPageNum();
	}

	public PagerItem resetPager() {
		if (pageSize < 1) {
			pageSize = 10;
		}

		pageCount = (rowCount + pageSize - 1) / pageSize;
		if (pageIndex < 0) {
			pageIndex = 1;
		}
		if (pageCount > 1 && pageIndex > pageCount) {
			pageIndex = pageCount;
		}

		return this;

	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if(pageSize==null){
			pageSize=10;
		}
		this.pageSize = pageSize;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		if(pageIndex==null){
			pageIndex=1;
		}
		this.pageIndex = pageIndex;
	}

	public Integer getRowCount() {
		return rowCount;
	}

	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	private Integer pageSize = 10;

	private Integer pageIndex = 1;

	private Integer rowCount = 0;

	private Integer pageCount = 1;

	private Integer startIndex = 0;

	public Integer getStartIndex() {

		if (pageIndex > 1) {
			startIndex = (pageIndex - 1) * pageSize;
		}
		if (startIndex < 0) {
			startIndex = 0;
		}
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public void parsePageSize(String pPageSize) {
		if (!SysFun.isNullOrEmpty(pPageSize)) {
			pageSize = SysFun.parseInt(pPageSize, 10);
		}
	}

	public void parsePageIndex(String pPageIndex) {
		if (!SysFun.isNullOrEmpty(pPageIndex)) {
			pageIndex = SysFun.parseInt(pPageIndex, 1);
		}
	}

	public static void main(String[] args) {

		System.out.println("Test");
		PagerItem pager = new PagerItem(5, 3).changeRowCount(100);

		System.out.println("limit " + pager.getStartIndex() + ", " + pager.getPageSize());

	}

	private String url = "";

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private String paramPageSize = "pageSize";
	private String paramPageIndex = "pageIndex";

	public String getParamPageSize() {
		return paramPageSize;
	}

	public void setParamPageSize(String paramPageSize) {
		this.paramPageSize = paramPageSize;
	}

	public String getParamPageIndex() {
		return paramPageIndex;
	}

	public void setParamPageIndex(String paramPageIndex) {
		this.paramPageIndex = paramPageIndex;
	}

	public String generalUrl_ByPageNum(int pPageNum) {

		String str = this.url;
		if (pPageNum < 0) {
			pPageNum = 1;
		}
		str = SysFun.resetUrlQSValue(str, getParamPageSize(), "" + pageSize);
		str = SysFun.resetUrlQSValue(str, getParamPageIndex(), "" + pPageNum);
		return str;
	}

	public PagerItem changeUrl(String pUrl) {
		this.url = pUrl;
		resetPageUrl();
		return this;
	}

	public PagerItem resetPageNum() {
		firstPageNum = 1;
		lastPageNum = pageCount;

		prevPageNum = pageIndex - 1;
		if (prevPageNum < firstPageNum) {
			prevPageNum = 1;
		}

		nextPageNum = pageIndex + 1;
		if (nextPageNum > lastPageNum) {
			nextPageNum = lastPageNum;
		}

		return this;
	}

	public PagerItem resetPageUrl() {
		resetPageNum();
		firstPageUrl = generalUrl_ByPageNum(firstPageNum);
		lastPageUrl = generalUrl_ByPageNum(lastPageNum);
		prevPageUrl = generalUrl_ByPageNum(prevPageNum);
		nextPageUrl = generalUrl_ByPageNum(nextPageNum);
		return this;
	}

	private Integer firstPageNum = 1;
	private Integer lastPageNum = 1;
	private Integer prevPageNum = 1;
	private Integer nextPageNum = 1;

	private String firstPageUrl;
	private String lastPageUrl;
	private String prevPageUrl;
	private String nextPageUrl;

	public Integer getFirstPageNum() {
		return firstPageNum;
	}

	public void setFirstPageNum(Integer firstPageNum) {
		this.firstPageNum = firstPageNum;
	}

	public Integer getLastPageNum() {
		return lastPageNum;
	}

	public void setLastPageNum(Integer lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	public Integer getPrevPageNum() {
		return prevPageNum;
	}

	public void setPrevPageNum(Integer prevPageNum) {
		this.prevPageNum = prevPageNum;
	}

	public Integer getNextPageNum() {
		return nextPageNum;
	}

	public void setNextPageNum(Integer nextPageNum) {
		this.nextPageNum = nextPageNum;
	}

	public String getFirstPageUrl() {
		return firstPageUrl;
	}

	public void setFirstPageUrl(String firstPageUrl) {
		this.firstPageUrl = firstPageUrl;
	}

	public String getLastPageUrl() {
		return lastPageUrl;
	}

	public void setLastPageUrl(String lastPageUrl) {
		this.lastPageUrl = lastPageUrl;
	}

	public String getPrevPageUrl() {
		return prevPageUrl;
	}

	public void setPrevPageUrl(String prevPageUrl) {
		this.prevPageUrl = prevPageUrl;
	}

	public String getNextPageUrl() {
		return nextPageUrl;
	}

	public void setNextPageUrl(String nextPageUrl) {
		this.nextPageUrl = nextPageUrl;
	}

}
