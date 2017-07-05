<%@page import="edu.csu.utils.QEncodeUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!-- saved from url=(0030)http://cszn.cainiaoxianfei.cn/ -->
<html><head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>在职研究生缴费信息管理系统</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" /> 
    <link href="<%=request.getContextPath()%>/loginfile/bootstrap.css" rel="stylesheet" type="text/css">
	<link href="<%=request.getContextPath()%>/loginfile/ataw_theme.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/loginfile/bootstrapValidator.min.css"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/loginfile/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/loginfile/backstretch.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/loginfile/bootstrapValidator.min.js"></script>
	<%
		String username = "";
		String password = "";
		Cookie[] cookies = request.getCookies();
		if (cookies!=null&&cookies.length>0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("username")) {
					username = cookie.getValue();
				}
				if (cookie.getName().equals("password")) {
					cookie.setMaxAge(0);//设置无效
					if(cookie.getValue()!=null)
				try{
					password =QEncodeUtil.aesDecrypt(cookie.getValue());//解密
				}catch(Exception e){
					password = "";
				}
				}
			}
		}
	
	%>
<script type="text/javascript">
$("document").ready(function(){ 
	//防止在frame里面出现登录页面 
	if(top.location!==self.location){ 
		top.location.href=self.location.href; 
		} 
	});

$(document).ready(function() {
    $('#loginform').bootstrapValidator({
        message: '不能为空',
        fields: {
        	USER_LOGIN_NAME: {
                message: '请输入用户名',
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    },
                    stringLength: {
                        min: 5,
                        max: 15,
                        message: '用户名长度必须超过5个字符且不能小于15个字符'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_]+$/,
                        message: '用户名只能包含字母、数字以及下划线'
                    }
                }
            },
            USER_LOGIN_PASSWORD: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    }
                }
            }
        }
    })
    .on('success.form.bv', function(e) {
              // Prevent form submission
              e.preventDefault();
              $.ajax({
                  type: "POST",
                  url: "<%=path %>/system/adminuserinfo/page_getmainframepage",
                  data: $('#loginform').serialize(),
                  success: function(msg){
                	  console.log(msg);
                	  if(msg=="OK"){
                		  window.location.href="<%=path %>/system/left_adminmenu/page_getadminmenulist";
               		  }else{
               		 	 var alertdiv =$('#alertInfo');
               		     alertdiv.html("<strong>错误！</strong>您的账号或者密码不正确。"); 
               		     alertdiv.css("display","block");
               	     	/* alert("账号或密码错误！"); */
               		  }
                	  
                  }
              });
                                   
    });
});	
	
</script>
</head>
<body class="login" >
  <div class="wrapper">
 
    <h3 style="text-align: center;color:white; font-size:18px;font-weight:bold;font-family: "Microsoft Yahei","微软雅黑";">软件学院在职研究生缴费信息管理系统</h3> 
   <div class="login-body">
      <h2>登录</h2>
      <form method="post" class="form-horizontal" id="loginform" action="<%=path %>/system/adminuserinfo/page_getmainframepage">
        <div class="form-group">
            <input type="text" class="form-control" name="USER_LOGIN_NAME" value="<%=username%>" id="UserName" placeholder="用户名" data-rule-required="true">
        </div>
        <div class="form-group">
            <input type="password" class="form-control" name="USER_LOGIN_PASSWORD" value="<%=password%>" id="Password" placeholder="密码" data-rule-required="true">
           <!--  <span style="display:none;" for="password" class="help-block error">不能为空</span> -->
        </div>
        <div class="submit" style="">
          <div class="checkbox">
            <label for="remember">记住我&nbsp;</label> 
            <input type="checkbox" name="remember" id="remember" checked="checked"> 
          </div>
          <div class="alert alert-danger" style="margin-bottom: 8px;display: none" id="alertInfo">
          </div>
          <div class="loginBtnGroup"><input class="btn btn-info loginBtn" value="登录" type="submit"></div>
        </div>
      <div class="forget">
      </div>
      </form>
    </div>
  </div>


<div class="backstretch" style="left: 0px; top: 0px; overflow: hidden; margin: 0px; padding: 0px; height: 100%; width: 100%; z-index: -999999; position: fixed;"><img src="<%=request.getContextPath()%>/loginfile/bgImg-office.jpg" style="position: absolute; margin: 0px; padding: 0px; border: none; width: 100%; height: 100%; max-height: none; max-width: none; z-index: -999999; left: 0px;"><iframe id="tmp_downloadhelper_iframe" style="display: none;"></iframe></div></body></html>