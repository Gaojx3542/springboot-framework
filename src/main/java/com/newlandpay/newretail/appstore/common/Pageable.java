package com.newlandpay.newretail.appstore.common;

import java.io.Serializable;

public class Pageable implements Serializable {

	private static final long serialVersionUID = -3930180379790344299L;

	private static final int DEFAULT_PAGE_NUMBER = 1;

	private static final int DEFAULT_PAGE_SIZE = 15;

	private static final int MAX_PAGE_SIZE = 1000;

	private int pageNo = DEFAULT_PAGE_NUMBER;

	private int pageSize = DEFAULT_PAGE_SIZE;


	public Pageable() {
	}

	public Pageable(Integer pageNumber, Integer pageSize) {
		if (pageNumber != null && pageNumber >= 1) {
			this.pageNo = pageNumber;
		}else{
			this.pageNo = DEFAULT_PAGE_NUMBER;
		}
		if (pageSize != null && pageSize >= 1 && pageSize <= MAX_PAGE_SIZE) {
			this.pageSize = pageSize;
		}else{
			this.pageSize = DEFAULT_PAGE_SIZE;
		}
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		if (pageNo < 1) {
			pageNo = DEFAULT_PAGE_NUMBER;
		}
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize < 1 || pageSize > MAX_PAGE_SIZE) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}
	
	public int getStartIndex(){
		return (getPageNo()-1)*getPageSize();
	}

}