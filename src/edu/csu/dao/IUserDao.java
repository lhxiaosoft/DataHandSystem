package edu.csu.dao;

import java.util.List;

import authority.edu.csu.dao.IBaseDao;

public interface IUserDao<T> extends IBaseDao<T>{

	public List<T> getAllPerson(T person);
	public List<T> getAllPersonOrg(T pOrg);
	
}
