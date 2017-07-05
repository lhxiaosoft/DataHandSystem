package edu.csu.dao;

import authority.edu.csu.dao.IBaseDao;

public interface IPayInfoCalVoDao<T> extends IBaseDao<T> {
	public Long sumFirPayBack(String id);   //统计招生点对应的第一年缴费（或返款）
	public Long sumSecPayBack(String id);   //统计招生点对应的第一年缴费（或返款）
	public int updatePayBack(String id);   //更新招生点对应的第一年（或第二年）缴费（或返款）

}
