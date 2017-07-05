package authority.edu.csu.dao;

import java.util.List;

import edu.csu.model.BaseModel;

public interface IBaseDao<T> {
	public int add(T model);
	
	public int del(Object ids);
	
	public int deleteById(Object id);
	
	public int update(T model);
	
	public int updateById(Object id);
	
	public List<T> selectAll();
	
	public T queryById(Object id);
	
	//为了分页查询使用
	public int queryByCount(BaseModel model);
	
	public List<T> queryByList(BaseModel model);

}
