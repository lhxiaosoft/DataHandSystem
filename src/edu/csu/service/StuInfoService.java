package edu.csu.service;

import authority.edu.csu.service.BaseService;
import edu.csu.dao.IStuInfoDao;

public class StuInfoService<T> extends BaseService<T> {
	private IStuInfoDao<T> stuInfoDao;

	public IStuInfoDao<T> getStuInfoDao() {
		return stuInfoDao;
	}

	public void setStuInfoDao(IStuInfoDao<T> stuInfoDao) {
		this.stuInfoDao = stuInfoDao;
	}
	

	
}
