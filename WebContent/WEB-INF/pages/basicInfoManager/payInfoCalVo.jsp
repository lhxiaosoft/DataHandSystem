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
    <script type="text/javascript">
    
    
    //更新汇总数据
    function update_payinfocalvo(){
    	 $.ajax({
				type:"POST",
				url:'<%=request.getContextPath() %>/system/payinfocalvo/data_updatepayinfocalvo',
				async:false,
				success:function(result){
					if(result=='true'){
						$.messager.alert("操作提示", "汇总数据更新成功，当前数据为最新数据");
						$('#tt_payinfocalvo').datagrid('reload');
					}else{
						$.messager.alert("操作提示", "汇总数据更新失败");
					}
					
				},
				error:function(result){
					$.messager.alert("操作提示", "汇总数据更新失败");
				}
	   		});
      }
    
   //搜索
	function doSearch_payinfocalvo(){
		$('#tt_payinfocalvo').datagrid('load',{
			REC_YEAR: $('#FIND_REC_YEAR').combobox('getValue'),
			REC_NAME: $('#FIND_REC_NAME').combobox('getValue'),
			RetTimes: $('#FIND_RET_TIMES').combobox('getValue') 
		});
   }
	//导出学生文件
   function output_payinfocalvo(){
	   	var REC_YEAR=$('#FIND_REC_YEAR').combobox('getValue');   
		var REC_NAME=$('#FIND_REC_NAME').combobox('getValue');
		var RetTimes=$('#FIND_RET_TIMES').combobox('getValue');
		var elemIF = document.createElement("iframe");   
        elemIF.src = encodeURI('<%=request.getContextPath() %>/system/payinfocalvo/data_outputpayinfocalvo?REC_YEAR='+REC_YEAR+'&REC_NAME='+REC_NAME+'&RetTimes='+RetTimes);   
        elemIF.style.display = "none";   
        document.body.appendChild(elemIF); 
	  
	}
	   
	  </script>
   
  </head>
  
  <body >
  
   <!-- 列表开始 -->
   <div id="tb_payinfocalvo" style="padding:3px"> 
	  	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="update_payinfocalvo()" >更新汇总数据</a>
	  	<a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-undo"  plain="true" onclick="output_payinfocalvo()">导出</a>
	  	<span>招生点:</span>
	  	<input  class="easyui-combobox" name="FIND_REC_NAME" id="FIND_REC_NAME" style="line-height:22px;border:1px solid #ccc;width:100px;"
	  	data-options="
                url:'<%=request.getContextPath() %>/system/recpoint/data_getRecNameComboxList',
                method:'post',
                editable:false,
                valueField:'rec_NAME',
                textField:'rec_NAME',
                multiple:false,
                panelHeight:'auto'"/>
	  	<span>入学时间:</span>
	  	<input  class="easyui-combobox" name="FIND_REC_YEAR" id="FIND_REC_YEAR" style="line-height:22px;border:1px solid #ccc;width:100px;"
	  	data-options="
                url:'<%=request.getContextPath() %>/system/recpoint/data_getAdmTimeFindComboxList',
                method:'post',
                editable:false,
                valueField:'rec_YEAR',
                textField:'rec_YEAR',
                multiple:false,
                panelHeight:'auto'"/>
	  	
         <span>返款批次:</span>
         <input id="FIND_RET_TIMES" class="easyui-combobox" name="FIND_RET_TIMES"  style="line-height:22px;border:1px solid #ccc;width:100px;" 
         data-options="
				valueField:'label',
				textField:'value',
				data:[
				{
					label:'全部',
					value:'全部',
				},{
					label:'第1次返款',
					value:'第1次返款',
				},{
					label:'第2次返款',
					value:'第2次返款',
				}],
				editable:false,
				panelHeight:'auto'" /> 
         
	  	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch_payinfocalvo()">查询</a>
    </div>
  <table id="tt_payinfocalvo"  class="easyui-datagrid" title="缴费汇总信息" rownumbers="true" fit="true"  toolbar = "#tb_payinfocalvo"  fitColumns="true"
			singleSelect="true" url="system/payinfocalvo/data_getPayInfoCalVoList" pagination="true" data-options="showFooter: true,pageSize: 20,pageList: [20,30,40]">
		<thead>
			<tr>
			<th field="rec_ID" width="100" align="center" checkbox="true"></th> <!-- field 不能重复。不能设置单选，否则只能选中一列 -->
			<th field="rec_NAME" width="80" sortable="false" align="center" >招生点</th>
			<th field="rec_YEAR" width="100" align="center" sortable="false">入学时间</th>
			<th field="retTimes" width="60" sortable="false" align="center" >返款批次</th>
			<th field="rec_NUMBER" width="100" align="center" sortable="false">实际入学人数（人）</th>
			<th field="retUnitPrice" width="100" align="center" sortable="false">应返金额/人（元）</th>
			<th field="retID" width="100" hidden="true" >学年id</th>   <!-- 隐藏域 -->
			<th field="havRet" width="100" align="center" sortable="false">应返金额（元）</th>
			<th field="had_RET" width="80" sortable="true" align="center" >已返金额（元）</th>
			<th field="tal_BANLANCE" width="80" sortable="true" align="center" >合计差额（元）</th>
			<th field="remark" width="100" sortable="false" align="center">备注</th>
			</tr>
		</thead>
	</table>
	<!-- 列表结束 -->
	
	
	

  </body>
</html>
