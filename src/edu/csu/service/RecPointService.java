package edu.csu.service;

import java.util.List;

import authority.edu.csu.service.BaseService;
import edu.csu.dao.IRecPointDao;

public class RecPointService<T> extends BaseService<T> {
	private IRecPointDao<T> recPointDao;

	public IRecPointDao<T> getRecPointDao() {
		return recPointDao;
	}

	public void setRecPointDao(IRecPointDao<T> recPointDao) {
		this.recPointDao = recPointDao;
	}
	
	public List<T> queryRecPointList(T model){
		return recPointDao.queryRecPointList(model);    //查询招生点年份列表数据
	}
	public List<T> queryRecPointListByRecYear(T model){
		return recPointDao.queryRecPointListByRecYear(model);    //查询招生点名称列表数据   返回名称 和 名称id
	}
	
	public List<T> queryRecNameList(){
		return recPointDao.queryRecNameList();    //查询招生点名称列表数据  返回名称
	}
	public List<T> queryRecYearList(){
		return recPointDao.queryRecYearList();    //查询入学时间列表数据  返回入学时间
	}
}
