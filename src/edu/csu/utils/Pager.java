package edu.csu.utils;




public class Pager {

	private int pageId = 1; 
	private int pageSize = 10; 
	private String orderField;
	private boolean orderDirection;


	public Pager() {
		this.orderDirection = true;
	}


	public String getOrderCondition() {
		String condition = "";
		if (this.orderField != null && this.orderField.length() != 0) {
			condition = " order by " + orderField
					+ (orderDirection ? " " : " desc ");
		}
		return condition;
	}
	public String getThenOrder() {
		String condition = "";
		if (this.orderField != null && this.orderField.length() != 0) {
			condition = "," + orderField
					+ (orderDirection ? " " : " desc ");
		}
		return condition;
	}

	public void setOrderDirection(boolean orderDirection) {
		this.orderDirection = orderDirection;
	}

	public boolean isOrderDirection() {
		return orderDirection;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setPageId(int pageId) {
		this.pageId = pageId;
	}

	public int getPageId() {
		return pageId;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}


}