package edu.csu.utils;

import java.util.List;

import authority.edu.csu.controller.adminuserinfomanager.AdminUserInfoController;
import authority.edu.csu.vo.AdminRoleMenuInfoVo;
import edu.csu.filter.SecurityServlet;

public class OperationAuthority {
	
	public static boolean getOper(OpertionEnum opr)
	{
		boolean isFlag=false;
		List<AdminRoleMenuInfoVo> list=AdminUserInfoController.getUserAutMenu;
	    if (list!=null) {
	    	for(int i=0;i<list.size();i++)
		    {
		    	System.out.println(list.get(i).getMENU_NAME()+"-----------"+opr.toString());
		    	String name=list.get(i).getMENU_CODE();
		    	String oprEX=SecurityServlet.preUrl+"_"+opr.toString();
		    	if(name.equals(oprEX))
		    	{
		    		return true;
		    	}
		    }
			System.out.println(list.size());
		}
		return true;
	}
}
