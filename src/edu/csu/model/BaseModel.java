package edu.csu.model;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import edu.csu.utils.Pager;

/**
 * 所有Model的基类，用以分页
 * @author yz 2016-1-8 21:44:50
 *
 */
public class BaseModel {

	private  Integer page = 1;//当前页面

	private Integer rows = Integer.MAX_VALUE;//一页多少条

	private String sort;//拿什么字段排序

	private String order;//DESC OR ASC

	private int Count;//总条数
	
	public int getCount() {
		return Count;
	}

	public void setCount(int count) {
		Count = count;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	/**
	 * 分页导航
	 */
	@JsonIgnore
	private Pager pager = new Pager();
	
	public Pager getPager() {
		pager.setPageId(getPage());
		pager.setPageSize(getRows());
		String orderField="";
		if(StringUtils.isNotBlank(sort)){
			orderField = sort;
		}
		if(StringUtils.isNotBlank(orderField) && StringUtils.isNotBlank(order)){
			orderField +=" "+ order;
			}
			pager.setOrderField(orderField);
			return pager;
		}

		public void setPager(Pager pager) {
			this.pager = pager;
		}


/*	public String getOrderPager() {
		
		String orderField="";
		if(StringUtils.isNotBlank(sort)){
			orderField = sort;
		}
		if(StringUtils.isNotBlank(orderField) && StringUtils.isNotBlank(order)){
			orderField +=" "+ order;
			if (orderField != null && orderField.length() != 0) {
				OrderPager = " order by " + orderField;
			}
		}
	System.out.println(OrderPager+"====OrderPager");
		return OrderPager;
	}*/
    
}
