package authority.edu.csu.controller;

import java.util.HashMap;
import java.util.Map;

import edu.csu.utils.RepStatus;

public class BaseController {
	/**
	 * 所有的ActionMap统一从这里获取
	 * @return
	 */
	public Map<String, Object> getRootMap()
	{
		return new HashMap<String, Object>();
	}
	/**
	 * 返回成功的消息
	 * @return
	 */
	public Map<String,Object> getSuccessRep(){
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("Status", true);
		return result;
	}
	/**
	 * 返回带参失败的消息
	 * @param failMsg 失败消息
	 * @return 结果集
	 */
	public Map<String,Object> getFailureRep(String failMsg){
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("Status", false);
		result.put("Msg", failMsg);
		return result;
	}
	/**
	 * 返回无参失败的消息
	 * @return 结果集
	 */
	public Map<String,Object> getFailureRep(){
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("Status", false);
		result.put("Msg", null);
		return result;
	}
	public Map<String,Object> getRepResult(boolean Status,Object Msg){
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("Status", Status);
		result.put("Msg", Msg);
		return result;
	}
}