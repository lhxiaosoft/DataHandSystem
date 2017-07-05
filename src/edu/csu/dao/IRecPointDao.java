package edu.csu.dao;

import java.util.List;

import authority.edu.csu.dao.IBaseDao;

public interface IRecPointDao<T> extends IBaseDao<T> {
	public List<T> queryRecPointList(T model);         //查询招生点年份列表数据
	public List<T> queryRecPointListByRecYear(T model);         //查询招生点名称列表数据   返回名称 和 名称id
	public List<T> queryRecNameList();     //查询招生点名称列表数据  返回名称  
	public List<T> queryRecYearList();     //查询入学时间列表数据  返回入学时间
}
