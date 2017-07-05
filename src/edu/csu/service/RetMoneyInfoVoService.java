package edu.csu.service;

import java.util.List;

import authority.edu.csu.service.BaseService;
import edu.csu.dao.IRetMoneyInfoVoDao;

public class RetMoneyInfoVoService<T> extends BaseService<T> {
	private IRetMoneyInfoVoDao<T> retMoneyInfoVoDao;

	public IRetMoneyInfoVoDao<T> getRetMoneyInfoVoDao() {
		return retMoneyInfoVoDao;
	}

	public void setRetMoneyInfoVoDao(IRetMoneyInfoVoDao<T> retMoneyInfoVoDao) {
		this.retMoneyInfoVoDao = retMoneyInfoVoDao;
	}
	public List<T> queryListById(Object id){     //传入招生点id，返款信息表中查询对应的返款信息
		return retMoneyInfoVoDao.queryListById(id);
	}
	

}
