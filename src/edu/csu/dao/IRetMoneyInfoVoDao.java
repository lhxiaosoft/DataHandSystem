package edu.csu.dao;

import java.util.List;

import authority.edu.csu.dao.IBaseDao;

public interface IRetMoneyInfoVoDao<T> extends IBaseDao<T> {
	public List<T> queryListById(Object id);   //传入招生点id，返款信息表中查询对应的返款信息
	
	

}
