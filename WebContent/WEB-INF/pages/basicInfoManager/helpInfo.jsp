<%@page import="edu.csu.filter.SecurityServlet"%>
<%@page import="edu.csu.utils.OpertionEnum"%>
<%@page import="edu.csu.utils.OperationAuthority"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
boolean isAdd = OperationAuthority.getOper(OpertionEnum.ADD);
boolean isUpdate = OperationAuthority.getOper(OpertionEnum.UPDATE);
boolean isDelete = OperationAuthority.getOper(OpertionEnum.DELETE);
boolean isEdit = OperationAuthority.getOper(OpertionEnum.EDIT);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <base href="<%=basePath%>">
    <title>缴费信息汇总</title>
	<link rel="stylesheet" type="text/css"
		href="<%=request.getContextPath()%>/js/easyui/themes/	metro/easyui.css">
	<link rel="stylesheet" type="text/css"
		href="<%=request.getContextPath()%>/js/easyui/themes/icon.css">
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/easyui/jquery.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/easyui/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/Common.js"></script>
	<title>帮助信息</title>
</head>
<body>
<ul>
	<li style="list-style-type:none; margin-top: 20px;"><a href="<%=request.getContextPath() %>/system/stuinfo/data_downloadhelpinfo">下载导入学生信息数据模板</a></li>
	<li style="list-style-type:none; margin-top: 20px;"><a href="<%=request.getContextPath() %>/system/stuinfo/data_downloadDoc">系统使用说明文档</a></li>
</ul>

</body>
</html>