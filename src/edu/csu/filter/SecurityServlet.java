package edu.csu.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.csu.utils.CommonTools;

public class SecurityServlet extends HttpServlet implements Filter {
    private static final long serialVersionUID = 1L;
    public static String preUrl;
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
             HttpServletRequest request=(HttpServletRequest)arg0;  
           HttpServletResponse response  =(HttpServletResponse) arg1;    
           HttpSession session = request.getSession(true);    
           Object user= session.getAttribute("adminuser");//登录人角色
           String url=request.getRequestURI(); 
           if(url.indexOf("system/adminuserinfo/page_getmainframepage")!=-1)
           {
               arg2.doFilter(arg0, arg1);  
               return;  
           }
           if(user==null) 
            {      
            //跳转到登陆页面
        	/*   RequestDispatcher requestDispatcher = request.getRequestDispatcher(request.getContextPath()+"/pub/login/page_getadminlogin");  
               requestDispatcher.forward(request, response); */ 
             response.sendRedirect(request.getContextPath()+"/pub/login/page_getadminlogin");  
        	  return;
            }  
           else
           {
        	   Object baseUrl=session.getAttribute("baseurl");
        	   Object childrensUrl=session.getAttribute("childrensurl");
        	//   List adminMenuChil=(ArrayList)session.getAttribute("adminmenuchil");
        	   Map adminMenuChil=(Map)session.getAttribute("adminmenuchil");
        	  if(adminMenuChil==null)
        	  {
        		  System.out.println("NULLLLLLLLLLLLLLLLLLLLLLLLL");
        	  }
        	  else
        	  {
//        		  System.out.println(adminMenuChil.size()+"ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
//          		
//        	        for(int i=0;i<adminMenuChil.size();i++)
//        	        {
//        	        	System.out.println("qu->LINK------------"+adminMenuChil.get(i));
//        	        }
//        		  System.out.println(adminMenuChil);
//        		  System.out.println("yesyesyesyessyeskdfjdklas");
        		  
        		  for (Object obj : adminMenuChil.keySet()) {
					
        			  System.out.println("key value"+obj+"--"+adminMenuChil.get(obj));
				}
        	  }
        	   if(url.indexOf(baseUrl.toString())!=-1 )
        	   {
                   arg2.doFilter(arg0, arg1);  
                   return; 
        	   }
        	   if(url.indexOf(childrensUrl.toString())!=-1 )
        	   {
                   arg2.doFilter(arg0, arg1);  
                   return; 
        	   }
     		  
     		  for (Object obj : adminMenuChil.keySet()) {
					if(obj!=null&&!obj.equals(""))
					{
						 if(url.indexOf(obj.toString().substring(0,obj.toString().lastIndexOf("/")))!=-1)
			        		   {
			        			  preUrl=adminMenuChil.get(obj).toString() ;
			                       arg2.doFilter(arg0, arg1);  
			                       return; 
			        		   }
					}
     			  System.out.println("key value"+obj+"--"+adminMenuChil.get(obj));
				}
//        	   for(int i=0;i<adminMenuChil.size();i++)
//        	   {
//        		   if(adminMenuChil!=null)
//        		   {
//        			   if(adminMenuChil.get(i)!=null)
//        			   {
//		        		   String temp=adminMenuChil.get(i).toString();
//		        		   if(url.indexOf(temp.substring(0,temp.lastIndexOf("/")))!=-1)
//		        		   {
//		        			  preUrl= url;
//		                       arg2.doFilter(arg0, arg1);  
//		                       return; 
//		        		   }
//        			   }
//        		   }
//        	   }
     		
           }
           //跳转到登陆页面
       	//response.sendRedirect(request.getContextPath()+"/pub/login/page_getadminlogin");  
       	 //return;
           arg2.doFilter(arg0, arg1);  
           return; 
    }
    public void init(FilterConfig arg0) throws ServletException {
    }

}