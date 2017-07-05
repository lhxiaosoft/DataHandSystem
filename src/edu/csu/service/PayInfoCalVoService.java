package edu.csu.service;

import authority.edu.csu.service.BaseService;
import edu.csu.dao.IPayInfoCalVoDao;

public class PayInfoCalVoService<T> extends BaseService<T>{
	private IPayInfoCalVoDao<T> payInfoCalVoDao;

	public IPayInfoCalVoDao<T> getPayInfoCalVoDao() {
		return payInfoCalVoDao;
	}

	public void setPayInfoCalVoDao(IPayInfoCalVoDao<T> payInfoCalVoDao) {
		this.payInfoCalVoDao = payInfoCalVoDao;
	}
	
	public Long sumFirPayBack(String id){   //统计招生点对应的第一年缴费（或返款）
		return payInfoCalVoDao.sumFirPayBack(id);
	}
	public Long sumSecPayBack(String id){   //统计招生点对应的第二年缴费（或返款）
		return payInfoCalVoDao.sumSecPayBack(id);
	} 
	
	public long updatePayBack(String id){   //更新招生点对应的第一年（或第二年）缴费（或返款）
		return payInfoCalVoDao.updatePayBack(id);
	}   

	
}
