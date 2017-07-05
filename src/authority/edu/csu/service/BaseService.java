package authority.edu.csu.service;

import java.util.List;

import javax.jws.WebService;

import authority.edu.csu.dao.IBaseDao;
import edu.csu.model.BaseModel;

@WebService
public class BaseService<T> implements BaseServiceInf<T>{
	   protected IBaseDao<T> dao;
	  
		public IBaseDao<T> getDao() {
			return dao;
		}

		public void setDao(IBaseDao<T> dao) {
			this.dao = dao;
		}

		public int add(T model)
		{
			return dao.add(model);
		}
		public int update(T model)
		{
			return dao.update(model);
		}
		public int del(String IDs) throws Exception {
				String[] id_item = IDs.split(",");//批量删除
				for (int i = 0; i < id_item.length; i++) {
					dao.del(id_item[i]);
					}
				return 1;
		}
		public int deleteById(Object id){
			return dao.deleteById(id);
		}
		
		public int queryByCount(BaseModel model){
			return dao.queryByCount(model);
		}
		
		public List<T> queryByList(BaseModel model){
			return dao.queryByList(model);
		}
		
		public T queryById(Object id)
		{
			return dao.queryById(id);
		}
}
