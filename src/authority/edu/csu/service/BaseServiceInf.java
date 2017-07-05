package authority.edu.csu.service;

import java.util.List;

import javax.jws.WebService;

import edu.csu.model.BaseModel;

@WebService
public interface BaseServiceInf<T> {

	public int add(T model);
	public int update(T model);
	public int del(String IDs) throws Exception;
	public int queryByCount(BaseModel model);
	public List<T> queryByList(BaseModel model);
	public T queryById(Object id);
}
